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
    void handleSignup(ActionEvent event) {
        String name = fieldName.getText();
        String description = fieldDescription.getText();

        Occupation occupation = new Occupation();
        occupation.setName(name);
        occupation.setDescription(description);

        try {
            UserService.createUser(new Credentials(name, description), name);
            new SceneChangeService().changeSceneTo("/views/occupation.fxml");
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }
}
