package controllers;

import db.managers.UserManager;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.SceneChangeService;
import utils.ApplicationUtilities;

public class SignupController {
    @FXML
    private TextField fieldLogin;

    @FXML
    private TextField fieldName;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private Button buttonSignup;

    @FXML
    void handleLogin(ActionEvent event) {
        new SceneChangeService().changeSceneTo("/views/login.fxml");
    }

    @FXML
    void handleSignup(ActionEvent event) {
        String login = fieldLogin.getText();
        String name = fieldName.getText();
        String password = fieldPassword.getText();

        User user = new User();
        user.setLogin(login);
        user.setName(name);
        user.setPassword(password);
        
        try {
            new UserManager().create(user);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        } 
    }
}
