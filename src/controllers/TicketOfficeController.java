package controllers;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import entities.Show;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.ShowService;

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

      // Bloquear botão de vender se a data for igual ou posterior a atual
      //buttonSell.setDisable(currentShow.getDate().after(new Date()));
      fieldCapacity.setDisable(true);
      fieldShow.setDisable(true);
      fieldTicket.setDisable(true);

      loadFieldValues();
    }

    @FXML
    void handleCloseSales(ActionEvent event) {
      /*
        mostra um dialog de confirmação
        desabilita venda de ingressos no show
        mostra dialog de confirmação ou notifier
        redireciona para o dashboard
      */
    }

    @FXML
    void handleSellTicket(ActionEvent event) {
      /*
        não permitir a venda se a data do show for igual ou posterior a atual
      */
    }

    private void loadFieldValues() {
      if (currentShow != null) {
        // preenche os fields
      }
    }
}
