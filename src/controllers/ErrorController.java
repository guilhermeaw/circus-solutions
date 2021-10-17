package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import common.EditorCallback;
import db.managers.ErrorManager;
import entities.Error;
import filters.data.ErrorFilter;
import filters.editors.ErrorFilterEditor;
import formatters.DateFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
      List<Error> errors = ErrorManager.getInstance().getByFilter(filter);
      ObservableList<Error> auditsObservableList = FXCollections.observableArrayList(errors);

      nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getUser().getName()));
      dateColumn.setCellValueFactory(column -> new SimpleStringProperty(DateFormatter.format(column.getValue().getDate())));
      errorColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getError()));

      errorTable.setItems(auditsObservableList);
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
  }

  @FXML
  void handleOpenFilter(ActionEvent event) {
    new ErrorFilterEditor(new EditorCallback<ErrorFilter>(filter) {
      @Override
      public void onEvent() {
          try {
              refreshContent();
          } catch ( Exception e ) {
              ApplicationUtilities.getInstance().handleException(e);
          }
      }
    }).open();
  }

  private ErrorFilter filter = new ErrorFilter();
}