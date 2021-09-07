package controllers;

import common.EditorCallback;
import db.managers.OccupationsManager;
import editors.OccupationEditor;
import entities.Occupation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.ApplicationUtilities;

public class OccupationsController {
    @FXML
    private TextField fieldName;

    @FXML
    private PasswordField fieldDescription;

    @FXML
    private Button buttonOccupations;

    public void refreshContent() {
        
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
    void handleEditOcuppation(ActionEvent event) {
        
    }

    @FXML
    void handleDeleteOccupation(ActionEvent event) {
        
    }   
}
