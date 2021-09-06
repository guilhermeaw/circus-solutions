package controllers;

import common.Credentials;
import entities.Occupation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.SceneChangeService;
import services.UserService;
import utils.ApplicationUtilities;

public class OccupationsController {
    @FXML
    private TextField fieldName;

    @FXML
    private PasswordField fieldDescription;

    @FXML
    private Button buttonOccupations;

    @FXML
    void handleAddOccupation(ActionEvent event) {   
    }

    @FXML
    void handleEditOcuppation(ActionEvent event) {
    }

    @FXML
    void handleDeleteOccupation(ActionEvent event) {
        
    }
}
