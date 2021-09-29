package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import services.LoginService;
import services.ShowService;
import utils.ApplicationUtilities;

public class DashboardController implements Initializable {
    @FXML
    private Button buttonLogout;

    @FXML
    private StackPane dashboardStackPane;

    @FXML
    private Button artistsButton;

    @FXML
    private Button occupationsButton;

    @FXML
    private Button attractionsButton;

    @FXML
    private Button ticketsButton;
    
    @FXML
    private Button showsButton;

    @FXML
    private Button ticketOfficeButton;

    @FXML
    private SplitPane ticketOfficeWrapper;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      loadDefaultPane();
      loadTicketOfficeButton();
    }

    public void handleChangePane(ActionEvent actionEvent) {
      if (actionEvent.getSource() == artistsButton) {
          loadPane("/views/components/panes/artist.fxml");
      } else if (actionEvent.getSource() == occupationsButton) {
          loadPane("/views/components/panes/occupation.fxml");
      } else if (actionEvent.getSource() == attractionsButton) {
        loadPane("/views/components/panes/attraction.fxml");
      } else if (actionEvent.getSource() == ticketsButton) {
        loadPane("/views/components/panes/tickets.fxml");
      } else if (actionEvent.getSource() == showsButton) {
        loadPane("/views/components/panes/show.fxml");
      } else if (actionEvent.getSource() == ticketOfficeButton) {
        loadPane("/views/components/panes/ticketOffice.fxml");
      }
  }
    
    @FXML
    void handleLogout(ActionEvent event) {
      LoginService.doLogout();
    }

    private void loadDefaultPane() {
      loadPane("/views/components/panes/artist.fxml");
    }

    private void loadPane(String paneSrc) {
      try {
          Parent pane = FXMLLoader.load(getClass().getResource(paneSrc));

          dashboardStackPane.getChildren().setAll(pane);
      } catch (Exception e) {
          ApplicationUtilities.getInstance().handleException(e);
      }
    }

    private void loadTicketOfficeButton() {
      boolean hasActiveShow = ShowService.getCurrentActiveShow() != null;
      
      // ticketOfficeButton.setDisable(!hasActiveShow);
      String tooltipText = hasActiveShow ? "" : "Não há nenhum espetáculo com bilheteria aberta";
      ticketOfficeWrapper.setTooltip(new Tooltip(tooltipText));
    }
}
