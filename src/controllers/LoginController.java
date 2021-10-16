package controllers;

import java.sql.Date;

import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import common.Credentials;
import db.managers.LoginManager;
import entities.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.AlertService;
import services.LoginService;
import services.SceneChangeService;
import utils.ApplicationUtilities;

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

        Login registerLogin = new Login();

        registerLogin.setDate(new java.sql.Date(new java.util.Date().getTime()));
        registerLogin.setUserLogged(ApplicationUtilities.getInstance().getActiveUser());

        LoginManager.getInstance().create(registerLogin);
        
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
