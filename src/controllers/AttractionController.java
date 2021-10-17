package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import common.EditorCallback;
import db.managers.AttractionsManager;
import editors.AttractionEditor;
import entities.Attraction;
import entities.Operation;
import entities.Pane;
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

public class AttractionController implements Initializable {
    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonDelete;

    @FXML
    private TableView<Attraction> attractionsTable;
   
    @FXML
    private TableColumn<Attraction, String> nameColumn;

    @FXML
    private TableColumn<Attraction, String> descriptionColumn;

    @FXML
    private Button buttonAttractions;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshContent();

        buttonEdit.setDisable(true);
        buttonDelete.setDisable(true);
    
        attractionsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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
            List<Attraction> attractions = AttractionsManager.getInstance().getAll();

            ObservableList<Attraction> attractionObservableList = FXCollections.observableArrayList(attractions);          

            nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getName()));
            descriptionColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getDescription()));

            attractionsTable.setItems(attractionObservableList);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    @FXML
    void handleAddAttraction(ActionEvent event) {   
        if (PermissionService.hasAccess(Operation.INSERT, Pane.ATTRACTIONS)) {
            new AttractionEditor(new EditorCallback<Attraction>(new Attraction()) {
                @Override
                public void onEvent() {
                    try {
                        AttractionsManager.getInstance().create((Attraction) getSource());
                        
                        refreshContent();
                    } catch ( Exception e ) {
                        ApplicationUtilities.getInstance().handleException(e);
                    }
                }
            } ).open();
        }
    }

    @FXML
    public void handleEditAttraction(ActionEvent event) {
        if (PermissionService.hasAccess(Operation.UPDATE, Pane.ATTRACTIONS)) {
            Attraction selectedAttraction = attractionsTable.getSelectionModel().getSelectedItem();
    
            if (selectedAttraction != null) {
                new AttractionEditor(new EditorCallback<Attraction>(selectedAttraction) {
                    @Override
                    public void onEvent() {
                        try {
                            AttractionsManager.getInstance().update((Attraction) getSource());
    
                            refreshContent();
                        } catch ( Exception e ) {
                            ApplicationUtilities.getInstance().handleException(e);
                        }
                    }
                } ).open();
            } else {
                AlertService.showWarning("É necessário selecionar uma atração");
            }
        }
    }

    @FXML
    public void handleDeleteAttraction(ActionEvent event) {
        if (PermissionService.hasAccess(Operation.DELETE, Pane.ATTRACTIONS)) {
            Attraction selectedAttraction = attractionsTable.getSelectionModel().getSelectedItem();
    
            if (selectedAttraction != null) {
                if (AlertService.showConfirmation("Tem certeza que deseja excluir a atração " + selectedAttraction.getName() + "?")) {
                    try {
                        AttractionsManager.getInstance().delete(selectedAttraction);
    
                        refreshContent();
                    } catch (Exception e) {
                        ApplicationUtilities.getInstance().handleException(e);
                    }
                }
            } else {
                AlertService.showWarning("É necessário selecionar uma atração");
            }
        }
    } 
}
