package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.SceneChangeService;

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

    }

    @FXML
    void handleSignup(ActionEvent event) {
        new SceneChangeService().changeSceneTo("/views/login.fxml");
    }
}
