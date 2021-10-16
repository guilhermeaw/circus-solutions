package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import db.managers.AuditManager;
import entities.Audit;
import formatters.DateFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    refreshContent();
  }
  
  public void refreshContent() {
    try {
      List<Audit> audits = AuditManager.getInstance().getAll();
      ObservableList<Audit> auditsObservableList = FXCollections.observableArrayList(audits);

      nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getUser().getName()));
      dateColumn.setCellValueFactory(column -> new SimpleStringProperty(DateFormatter.format(column.getValue().getDate())));
      actionColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getType()));

      auditTable.setItems(auditsObservableList);
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
  }
}