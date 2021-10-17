package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import db.managers.ShowManager;
import db.managers.TicketConfigManager;
import db.managers.TicketSellManager;
import entities.Operation;
import entities.Pane;
import entities.Show;
import entities.TicketConfig;
import formatters.CurrencyFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.AlertService;
import services.PermissionService;
import services.SceneChangeService;
import services.ShowService;
import services.TicketOfficeService;
import utils.DateUtils;

public class TicketOfficeController implements Initializable {
    @FXML
    private TextField fieldTicket;

    @FXML
    private TextField fieldShow;

    @FXML
    private TextField fieldCapacity;

    @FXML
    private Button buttonCloseSales;

    @FXML
    private Button buttonSell;

    private Show currentShow = null;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
      this.currentShow = ShowService.getCurrentActiveShow();

      buttonSell.setDisable(currentShow.getDate().before(DateUtils.getDateByLocalDate(LocalDate.now())));
      fieldCapacity.setDisable(true);
      fieldShow.setDisable(true);
      fieldTicket.setDisable(true);

      loadFieldValues();
    }

    @FXML
    void handleCloseSales(ActionEvent event) {
      if (PermissionService.hasAccess(Operation.UPDATE, Pane.TICKET_OFFICE)) {
        if (AlertService.showConfirmation("Tem certeza que deseja encerrar a venda de ingressos?")) {
          currentShow.setIsShowActive(false);
          ShowManager.getInstance().update(currentShow);
  
          new SceneChangeService().changeSceneTo("/views/dashboard.fxml");
        }
      }
    }

    @FXML
    void handleSellTicket(ActionEvent event) {
      if (PermissionService.hasAccess(Operation.UPDATE, Pane.TICKET_OFFICE)) {
        /*
          não permitir a venda se a data do show for igual ou anterior a atual
        */
        TicketConfig ticketConfig = TicketConfigManager.getInstance().getLastTicketConfig();
  
        if (currentShow != null && currentShow.getDate().before(DateUtils.getDateByLocalDate(LocalDate.now()))) {
          AlertService.showWarning("Não é possível vender ingressos para um show que já passou");
          return;
        }
  
        TicketOfficeService.sellTicket(currentShow, ticketConfig);
        loadFieldValues();
      }
    }

    private void loadFieldValues() {
      if (currentShow != null) {
        TicketConfig ticketConfig = TicketConfigManager.getInstance().getLastTicketConfig();
        
        fieldShow.setText(currentShow.toString());
        fieldCapacity.setText(String.valueOf(currentShow.getCapacity()));

        double ticketValue = 0;

        if (ticketConfig != null) {
          ticketValue = ticketConfig.getValue();
        }
        
        /*
          buscar sequencial do ticket para botar aqui
        */
        int nextSequential = TicketSellManager.getInstance().getLastSequential(currentShow) + 1;
        fieldTicket.setText("INGRESSO-" + nextSequential + " | " + CurrencyFormatter.format(String.valueOf(ticketValue)));
      }
    }
}
