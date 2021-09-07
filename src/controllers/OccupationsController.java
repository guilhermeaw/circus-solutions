package controllers;

import java.util.List;

import common.EditorCallback;
import db.managers.OccupationsManager;
import editors.OccupationEditor;
import entities.Occupation;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import services.AlertService;
import utils.ApplicationUtilities;

public class OccupationsController {
    @FXML
    private TableView<Occupation> occupationsTable;
   
    @FXML
    private TableColumn<Occupation, String> nameColumn;

    @FXML
    private TableColumn<Occupation, String> descriptionColumn;

    @FXML
    private Button buttonOccupations;

    public void refreshContent() {
        try {
            List<Occupation> occupations = OccupationsManager.getInstance().getAll();

            ObservableList<Occupation> occupationObservableList = FXCollections.observableArrayList(occupations);          

            nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getName()));
            descriptionColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getDescription()));

            occupationsTable.setItems(occupationObservableList);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    @FXML
    void handleAddOccupation(ActionEvent event) {   
        new OccupationEditor(new EditorCallback<Occupation>(new Occupation()) {
            @Override
            public void onEvent() {
                try {
                    OccupationsManager.getInstance().create((Occupation) getSource());
                    
                    refreshContent();
                } catch ( Exception e ) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } ).open();
    }

    @FXML
    public void handleEditOccupation(ActionEvent event) {
        Occupation selectedOccupation = occupationsTable.getSelectionModel().getSelectedItem();

        if (selectedOccupation != null) {
            new OccupationEditor(new EditorCallback<Occupation>(selectedOccupation) {
                @Override
                public void onEvent() {
                    try {
                        OccupationsManager.getInstance().update((Occupation) getSource());

                        refreshContent();
                    } catch ( Exception e ) {
                        ApplicationUtilities.getInstance().handleException(e);
                    }
                }
            } ).open();
        } else {
            AlertService.showWarning("É necessário selecionar um cargo");
        }
    }

    @FXML
    public void handleDeleteOccupation(ActionEvent event) {
        Occupation selectedOccupation = occupationsTable.getSelectionModel().getSelectedItem();

        if (selectedOccupation != null) {
            if (AlertService.showConfirmation("Tem certeza que deseja excluir o cargo " + selectedOccupation.getName() + "?")) {
                try {
                    OccupationsManager.getInstance().delete(selectedOccupation);

                    refreshContent();
                } catch (Exception e) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } else {
            AlertService.showWarning("É necessário selecionar um cargo");
        }
    }   
}
