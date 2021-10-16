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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import services.AlertService;
import utils.ApplicationUtilities;

public class AuditController implements Initializable {
  @FXML
  private Button deleteButton;

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

    deleteButton.setDisable(true);

    auditTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        deleteButton.setDisable(false);
      } else {
        deleteButton.setDisable(true);
      }
    });
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

  @FXML
  public void handleDeleteAuditoria(ActionEvent event) {
    Audit selectedAudit = auditTable.getSelectionModel().getSelectedItem();

    if (selectedAudit != null) {
      if (AlertService.showConfirmation("Tem certeza que deseja excluir a auditoria " + selectedAudit.getType() + "?")) {
        try {
          AuditManager.getInstance().delete(selectedAudit);

          refreshContent();
        } catch (Exception e) {
          ApplicationUtilities.getInstance().handleException(e);
        }
      }
    } else {
      AlertService.showWarning("É necessário selecionar uma auditoria");
    }
  }
}