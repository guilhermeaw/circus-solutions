package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.SceneChangeService;

public class LoginController {
    @FXML
    private TextField fieldLogin;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonSignup;

    @FXML
    void handleLogin(ActionEvent event) {
      new SceneChangeService().changeSceneTo("/views/dashboard.fxml");
    }

    @FXML
    void handleSignup(ActionEvent event) {
      new SceneChangeService().changeSceneTo("/views/signup.fxml");
    }
}
