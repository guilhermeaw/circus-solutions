package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import services.LoginService;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      loadDefaultPane();
    }

    public void handleChangePane(ActionEvent actionEvent) {
      if (actionEvent.getSource() == artistsButton) {
          loadPane("/views/components/panes/artist.fxml");
      } else if (actionEvent.getSource() == occupationsButton) {
          loadPane("/views/components/panes/occupation.fxml");
      } else if (actionEvent.getSource() == attractionsButton) {
        loadPane("/views/components/panes/attraction.fxml");
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
}
