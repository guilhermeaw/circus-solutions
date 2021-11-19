package controllers;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;

import db.managers.TicketConfigManager;
import entities.Operation;
import entities.Pane;
import entities.Show;
import entities.TicketConfig;
import formatters.CurrencyFormatter;
import formatters.DateFormatter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.AlertService;
import services.PermissionService;
import services.ShowService;
import utils.ApplicationUtilities;

public class TicketController implements Initializable {
    @FXML
    private TextField fieldValue;

    @FXML
    private TextField fieldLastUpdate;

    @FXML
    private TextField fieldAuthor;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonConfirm;

    @FXML
    private Button buttonEdit;

    private TicketConfig source = null;
    private SimpleDoubleProperty currencyAmount = new SimpleDoubleProperty(this, "amount", 0.00);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fieldAuthor.setDisable(true);
        fieldLastUpdate.setDisable(true);
        
        refreshContent();

        fieldValue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    currencyAmount.set(CurrencyFormatter.getAmount(newValue));
                    fieldValue.setText(CurrencyFormatter.format(newValue));
                }
            }
        });
    }

    @FXML
    void handleEditTicketConfig(ActionEvent event) {
        if (PermissionService.hasAccess(Operation.UPDATE, Pane.TICKETS)) {
            Show currentActiveShow = ShowService.getCurrentActiveShow();
    
            if (currentActiveShow != null) {
                AlertService.showWarning("Não é possível alterar o valor do ingresso quando há espetáculos em atividade. Para continuar, primeiro encerre as vendas na bilheteria");
                return;
            }
    
            buttonConfirm.setDisable(false);
            buttonCancel.setDisable(false);
    
            fieldValue.setDisable(false);
            fieldValue.setText(CurrencyFormatter.format(String.valueOf(currencyAmount.get())));
        }
    }

    @FXML
    void handleUpdateTicketConfig(ActionEvent event) {
        if (PermissionService.hasAccess(Operation.UPDATE, Pane.TICKETS)) {
            TicketConfig ticketConfig = new TicketConfig();
    
            ticketConfig.setAuthor(ApplicationUtilities.getInstance().getActiveUser());
            ticketConfig.setCreatedDate(new Timestamp(new Date().getTime()));
            ticketConfig.setValue(currencyAmount.get());
    
            TicketConfigManager.getInstance().create(ticketConfig);
            
            refreshContent();
        }
    }

    @FXML
    void handleCancel(ActionEvent event) {
        refreshContent();
    }

    public void refreshContent() {
        fieldValue.setDisable(true);

        buttonConfirm.setDisable(true);
        buttonCancel.setDisable(true);
        buttonEdit.setDisable(false);

        source = TicketConfigManager.getInstance().getLastTicketConfig();

        if (source != null) {
            currencyAmount.set(source.getValue());

            fieldAuthor.setText(source.getAuthor().getName());
            fieldValue.setText(CurrencyFormatter.format(String.valueOf(currencyAmount.get())));
            fieldLastUpdate.setText(DateFormatter.format(source.getCreatedDate()));
        } else {
            fieldValue.clear();
        }
    }
}
