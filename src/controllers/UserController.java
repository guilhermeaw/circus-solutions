package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import common.EditorCallback;
import db.managers.UserManager;
import editors.UserEditor;
import entities.Operation;
import entities.Pane;
import entities.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import services.AlertService;
import services.PermissionService;
import utils.ApplicationUtilities;

public class UserController implements Initializable {
  @FXML
  private Button buttonEdit;

  @FXML
  private Button buttonDelete;

  @FXML
  private TableView<User> usersTable;

  @FXML
  private TableColumn<User, String> nameColumn;

  @FXML
  private TableColumn<User, String> loginColumn;

  @FXML
  private TableColumn<User, String> roleColumn;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    refreshContent();

    buttonEdit.setDisable(true);
    buttonDelete.setDisable(true);

    usersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        buttonEdit.setDisable(false);
        buttonDelete.setDisable(false);
      } else {
        buttonEdit.setDisable(true);
        buttonDelete.setDisable(true);
      }
    });
  }

  public void refreshContent() {
    try {
      List<User> users = UserManager.getInstance().getAllExcludingCurrentUser();
      ObservableList<User> usersObservableList = FXCollections.observableArrayList(users);

      nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getName()));
      loginColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getLogin()));
      roleColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getRole().getName()));

      usersTable.setItems(usersObservableList);
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
  }

  @FXML
  void handleDeleteUser(ActionEvent event) {
    if (PermissionService.hasAccess(Operation.DELETE, Pane.USERS)) {
      User selectedUser = usersTable.getSelectionModel().getSelectedItem();
      User activeUser = ApplicationUtilities.getInstance().getActiveUser();
  
      if (selectedUser != null) {
        if (activeUser.getRole().getLevel() < selectedUser.getRole().getLevel()) {
          AlertService.showWarning("Não é possível excluir um usuário com um nível de permissão maior que o seu");
          return;
        }
        
        if (AlertService.showConfirmation("Tem certeza que deseja excluir o usuário " + selectedUser.getName() + "?")) {
          try {
            UserManager.getInstance().delete(selectedUser);
  
            refreshContent();
          } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
          }
        }
      } else {
        AlertService.showWarning("É necessário selecionar um usuário");
      }
    }
  }

  @FXML
  void handleEditUser(ActionEvent event) {
    if (PermissionService.hasAccess(Operation.UPDATE, Pane.USERS)) {
      User selectedUser = usersTable.getSelectionModel().getSelectedItem();
  
      if (selectedUser != null) {
        new UserEditor(new EditorCallback<User>(selectedUser) {
          @Override
          public void onEvent() {
            try {
              UserManager.getInstance().update((User) getSource());
  
              refreshContent();
            } catch ( Exception e ) {
              ApplicationUtilities.getInstance().handleException(e);
            }
          }
        }).open();
      } else {
        AlertService.showWarning("É necessário selecionar um usuário");
      }
    }
  }
}
