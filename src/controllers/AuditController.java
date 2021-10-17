package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import common.EditorCallback;
import db.managers.AuditManager;
import entities.Audit;
import filters.data.AuditFilter;
import filters.editors.AuditFilterEditor;
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

public class AuditController implements Initializable {
  @FXML
  private TableView<Audit> auditTable;

  @FXML
  private TableColumn<Audit, String> nameColumn;

  @FXML
  private TableColumn<Audit, String> dateColumn;

  @FXML
  private TableColumn<Audit, String> actionColumn;

  @FXML
  private TableColumn<Audit, String> tableNameColumn;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    refreshContent();
  }
  
  public void refreshContent() {
    try {
      List<Audit> audits = AuditManager.getInstance().getByFilter(filter);
      ObservableList<Audit> auditsObservableList = FXCollections.observableArrayList(audits);

      nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getUser().getName()));
      dateColumn.setCellValueFactory(column -> new SimpleStringProperty(DateFormatter.format(column.getValue().getDate())));
      actionColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getType()));
      tableNameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getTableName()));

      auditTable.setItems(auditsObservableList);
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
  }

  @FXML
  void handleOpenFilter(ActionEvent event) {
    new AuditFilterEditor(new EditorCallback<AuditFilter>(filter) {
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

  private AuditFilter filter = new AuditFilter();
}