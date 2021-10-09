package controllers;

import java.util.ArrayList;
import java.util.List;

import common.Credentials;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.AlertService;
import services.SceneChangeService;
import services.UserService;
import utils.ApplicationUtilities;
import validators.LoginValidator;
import validators.NameValidator;
import validators.PasswordValidator;

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
        try {
            String login = fieldLogin.getText();
            String name = fieldName.getText();
            String password = fieldPassword.getText();

            List<String> errors = new ArrayList<String>();

            validateFields(errors);

            if (!errors.isEmpty()) {
                AlertService.showWarning(ApplicationUtilities.getInstance().formatErrorMessage(errors));
            } else {
                UserService.createUser(new Credentials(login, password), name);
                new SceneChangeService().changeSceneTo("/views/login.fxml");
            }

        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    private void validateFields(List<String> errors) {
        String login = fieldLogin.getText();
        String name = fieldName.getText();
        String password = fieldPassword.getText();

        if (!LoginValidator.validate(login)) {
            errors.add("É necessário informar um login com pelo menos 4 caracteres");
        }

        if (!NameValidator.validate(name)) {
            errors.add("É necessário informar o nome completo");
        }

        if (!PasswordValidator.validate(password)) {
            errors.add("A senha deve ter mais de 4 caracteres");
        }
    }
}
