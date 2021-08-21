package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import services.SceneChangeService;

public class DashboardController {
    @FXML
    private Button buttonLogout;

    @FXML
    void handleLogout(ActionEvent event) {
      new SceneChangeService().changeSceneTo("/views/login.fxml");
    }
}
