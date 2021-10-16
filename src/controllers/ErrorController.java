package controllers;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

import common.EditorCallback;
import db.managers.ErrorManager;
import entities.Error;
import formatters.DateFormatter;
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
import utils.ApplicationUtilities;

public class ErrorController implements Initializable {
  @FXML
  private Button deleteButton;

  @FXML
  private TableView<Error> errorTable;

  @FXML
  private TableColumn<Error, String> nameColumn;

  @FXML
  private TableColumn<Error, Timestamp> dateColumn;

  @FXML
  private TableColumn<Error, String> errorColumn;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    refreshContent();

    deleteButton.setDisable(true);

    errorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        deleteButton.setDisable(false);
      } else {
        deleteButton.setDisable(true);
      }
    });
  }
  
  public void refreshContent() {
    try {
      List<Error> errors = ErrorManager.getInstance().getAll();
      ObservableList<Error> auditsObservableList = FXCollections.observableArrayList(errors);

      nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getUser().getName()));
      //dateColumn.setCellValueFactory(column -> new SimpleStringProperty(DateFormatter.format(column.getValue().getDate())));
      errorColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getError()));

      errorTable.setItems(auditsObservableList);
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
  }

  @FXML
  public void handleDeleteError(ActionEvent event) {
    Error selectedError = errorTable.getSelectionModel().getSelectedItem();

    if (selectedError != null) {
      if (AlertService.showConfirmation("Tem certeza que deseja excluir o erro " + selectedError.getError() + " da listagem?")) {
        try {
          ErrorManager.getInstance().delete(selectedError);

          refreshContent();
        } catch (Exception e) {
          ApplicationUtilities.getInstance().handleException(e);
        }
      }
    } else {
      AlertService.showWarning("É necessário selecionar um erro");
    }
  }
}