package controllers;

import common.Credentials;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.AlertService;
import services.LoginService;
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
      try {
        String login = fieldLogin.getText();
        String password = fieldPassword.getText();

        LoginService.doLogin(new Credentials(login, password));

        new SceneChangeService().changeSceneTo("/views/dashboard.fxml");
      } catch (Exception e) {
        AlertService.showWarning(e.getMessage());
      }
    }

    @FXML
    void handleSignup(ActionEvent event) {
      new SceneChangeService().changeSceneTo("/views/signup.fxml");
    }
}
