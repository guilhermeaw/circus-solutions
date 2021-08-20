package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
      System.out.println("teste");
    }

    @FXML
    void handleSignup(ActionEvent event) {
        
    }
}
