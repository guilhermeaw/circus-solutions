package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import db.managers.ErrorManager;
import entities.Error;
import formatters.DateFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import utils.ApplicationUtilities;

public class ErrorController implements Initializable {
  @FXML
  private TableView<Error> errorTable;

  @FXML
  private TableColumn<Error, String> nameColumn;

  @FXML
  private TableColumn<Error, String> dateColumn;

  @FXML
  private TableColumn<Error, String> errorColumn;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    refreshContent();
  }
  
  public void refreshContent() {
    try {
      List<Error> errors = ErrorManager.getInstance().getAll();
      ObservableList<Error> auditsObservableList = FXCollections.observableArrayList(errors);

      nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getUser().getName()));
      dateColumn.setCellValueFactory(column -> new SimpleStringProperty(DateFormatter.format(column.getValue().getDate())));
      errorColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getError()));

      errorTable.setItems(auditsObservableList);
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
  }
}