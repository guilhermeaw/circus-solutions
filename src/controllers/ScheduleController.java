package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import db.managers.ScheduleManager;
import entities.Schedule;
import entities.Show;
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
    public interface ScheduleRemoveListener {
        public void onDeleteSchedule(Schedule schedule);
    }
    
    @FXML
    private Button buttonGoBack;

    @FXML
    private Button buttonAdd;

    @FXML
    private VBox scheduleContent;

    private Show show;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void handleAddSchedule(ActionEvent event) {
        Schedule schedule = new Schedule();
        schedule.setShow(show);
        ScheduleManager.getInstance().create(schedule);
        
        renderItem(schedule);
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

    public void setData(Show show) {
        this.show = show;
        refreshContent();
    }

    public void refreshContent() {
        List<Schedule> schedules = ScheduleManager.getInstance().getSchedulesByShow(show);

        for (Schedule schedule : schedules) {
            renderItem(schedule);
        }
    }

    public void deleteSchedule(Schedule schedule, AnchorPane itemPane) {
        ScheduleManager.getInstance().delete(schedule);
        scheduleContent.getChildren().remove(itemPane);
    }

    private void renderItem(Schedule schedule) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/components/scheduleItem.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            
            ScheduleItemController itemController = fxmlLoader.getController();
            
            ScheduleRemoveListener scheduleRemoveListener = new ScheduleRemoveListener() {
                @Override
                public void onDeleteSchedule(Schedule schedule) {
                    deleteSchedule(schedule, anchorPane);
                }
            };
            
            itemController.setData(schedule, scheduleRemoveListener);

            scheduleContent.getChildren().add(anchorPane);
        } catch (IOException e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }
}
