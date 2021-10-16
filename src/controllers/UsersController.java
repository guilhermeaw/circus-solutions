package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.AlertService;
import services.HashService;
import services.UserService;
import utils.ApplicationUtilities;
import validators.NameValidator;
import validators.PasswordValidator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entities.User;

public class UsersController implements Initializable {
    @FXML
    private TextField fieldUserName;

    @FXML
    private TextField fieldUserLogin;

    @FXML
    private TextField fieldUserPassword;

    @FXML
    private TextField fieldConfirmPassword;

    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button editButton;

    private boolean editMode = false;
    private User source;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fieldUserLogin.setDisable(true);

        refreshContent();
    }

    public void refreshContent() {
        User activeUser = ApplicationUtilities.getInstance().getActiveUser();

        this.source = activeUser;

        fieldUserName.setText(activeUser.getName());
        fieldUserLogin.setText(activeUser.getLogin());

        updateFields();
    }

    public void handleEditUser() {
        setEditMode(true);
    }

    public void handleConfirmChanges() {
        List<String> errors = new ArrayList<>();

        validateFields(errors);

        if (!errors.isEmpty()) {
            AlertService.showWarning(ApplicationUtilities.getInstance().formatErrorMessage(errors));
        } else {
            String password = fieldUserPassword.getText().trim();

            source.setName(fieldUserName.getText());
            source.setLogin(fieldUserLogin.getText());
            source.setPassword(password.isEmpty() ? password : HashService.hash(password));

            try {
                UserService.updateUser(source);
            } catch (Exception e) {
                ApplicationUtilities.getInstance().handleException(e);
            }

            ApplicationUtilities.getInstance().setActiveUser(source);

            setEditMode(false);
            refreshContent();
        }
    }

    public void handleCancelChanges() {
        setEditMode(false);
        refreshContent();
    }

    private void validateFields(List<String> errors) {
        String password = fieldUserPassword.getText();
        String confirmPassword = fieldConfirmPassword.getText();

        if (!NameValidator.validate(fieldUserName.getText())) {
            errors.add("É necessário informar um nome completo");
        }

        if (PasswordValidator.validate(password)) {
            errors.add("A senha deve ter mais de 4 caracteres");
        }

        if (!password.equals(confirmPassword)) {
            errors.add("A senha e a confirmação de senha devem ser iguais");
        }
    }

    private void setEditMode(boolean value) {
        this.editMode = value;
        updateFields();
    }

    private void updateFields() {
        fieldUserName.setDisable(!editMode);
        fieldUserPassword.setDisable(!editMode);
        fieldConfirmPassword.setDisable(!editMode);
        editButton.setDisable(editMode);
        submitButton.setDisable(!editMode);
        cancelButton.setDisable(!editMode);
    }
}

