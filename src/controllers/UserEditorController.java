package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.AlertService;
import services.HashService;
import services.UserService;
import utils.ApplicationUtilities;
import validators.NameValidator;
import validators.PasswordValidator;

public class UserEditorController implements Initializable {

    @FXML
    private Button buttonEdit;

    @FXML
    private TextField nameField;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonConfirm;

    private boolean editMode = false;
    private User source;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginField.setDisable(true);

        refreshContent();
    }

    public void refreshContent() {
      User activeUser = ApplicationUtilities.getInstance().getActiveUser();

      this.source = activeUser;

      nameField.setText(activeUser.getName());
      loginField.setText(activeUser.getLogin());

      updateFields();
    }

    @FXML
    public void handleCancel(ActionEvent event) {
      setEditMode(false);
      refreshContent();
    }

    @FXML
    public void handleConfirm(ActionEvent event) {
      List<String> errors = new ArrayList<String>();

      validateFields(errors);

      if (!errors.isEmpty()) {
        AlertService.showWarning(ApplicationUtilities.getInstance().formatErrorMessage(errors));
      } else {
        String password = passwordField.getText().trim();

        source.setName(nameField.getText());
        source.setLogin(loginField.getText());
        source.setPassword(password.isEmpty() ? source.getPassword() : HashService.hash(password));

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

    @FXML
    public void handleEditUser(ActionEvent event) {
      setEditMode(true);
    }

    private void validateFields(List<String> errors) {
      String password = passwordField.getText();
      String confirmPassword = confirmPasswordField.getText();

      if (!NameValidator.validate(nameField.getText())) {
          errors.add("É necessário informar um nome completo");
      }

      if (!PasswordValidator.validate(password)) {
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
      nameField.setDisable(!editMode);
      passwordField.setDisable(!editMode);
      confirmPasswordField.setDisable(!editMode);
      buttonEdit.setDisable(editMode);
      buttonConfirm.setDisable(!editMode);
      buttonCancel.setDisable(!editMode);
    }
}
