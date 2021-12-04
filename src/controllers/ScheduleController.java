package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.ApplicationUtilities;

public class ScheduleController implements Initializable {
    @FXML
    private Button buttonGoBack;

    @FXML
    private Button buttonAdd;

    @FXML
    private VBox scheduleContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            for (int i = 0; i < 5; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/components/scheduleItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                // ScheduleItemController itemController = fxmlLoader.getController();

                scheduleContent.getChildren().add(anchorPane);
            }
        } catch (IOException e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    @FXML
    public void handleAddSchedule(ActionEvent event) {

    }

    @FXML
    public void handleGoBack(ActionEvent event) {
        try {
            StackPane dashboardPane = ApplicationUtilities.getInstance().getDashboardPane();
            Parent pane = FXMLLoader.load(getClass().getResource("/views/components/panes/show.fxml"));

            dashboardPane.getChildren().setAll(pane);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }
}
