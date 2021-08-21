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
import services.SceneChangeService;
import utils.ApplicationUtilities;

public class DashboardController implements Initializable {
    @FXML
    private Button buttonLogout;

    @FXML
    private StackPane dashboardStackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      loadDefaultPane();
    }
    
    @FXML
    void handleLogout(ActionEvent event) {
      new SceneChangeService().changeSceneTo("/views/login.fxml");
    }

    private void loadDefaultPane() {
      loadPane("/views/components/panes/citiesStatesTabbedPane.fxml");
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
