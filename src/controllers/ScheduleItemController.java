package controllers;

import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

import common.TimeSpinner;
import controllers.ScheduleController.ScheduleRemoveListener;
import db.managers.ArtistManager;
import db.managers.AttractionsManager;
import db.managers.ScheduleManager;
import entities.Artist;
import entities.Attraction;
import entities.Schedule;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class ScheduleItemController implements Initializable {

    @FXML
    private ComboBox<Attraction> comboAttraction;

    @FXML
    private ComboBox<Artist> comboArtist;

    @FXML
    private HBox startTimeWrapper;

    @FXML
    private HBox endTimeWrapper;

    @FXML
    private Button buttonDelete;

    private Schedule schedule;
    private ScheduleRemoveListener scheduleRemoveListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadComboItems();
        loadTimeSpinners();

        comboArtist.valueProperty().addListener(new ChangeListener<Artist>() {
            @Override
            public void changed(ObservableValue<? extends Artist> observable, Artist oldValue, Artist newValue) {
                schedule.setArtist(newValue);
                ScheduleManager.getInstance().update(schedule);
            }
        });

        comboAttraction.valueProperty().addListener(new ChangeListener<Attraction>() {
            @Override
            public void changed(ObservableValue<? extends Attraction> observable, Attraction oldValue, Attraction newValue) {
                schedule.setAttraction(newValue);
                ScheduleManager.getInstance().update(schedule);
            }
        });

        startTimer.valueProperty().addListener((obs, oldTime, newTime) -> {
            schedule.setStartTime(newTime);
            ScheduleManager.getInstance().update(schedule);
        });

        endTimer.valueProperty().addListener((obs, oldTime, newTime) -> {
            schedule.setEndTime(newTime);
            ScheduleManager.getInstance().update(schedule);
        });
    }

    @FXML
    public void handleDeleteSchedule(ActionEvent event) {
        scheduleRemoveListener.onDeleteSchedule(schedule);
    }

    public void setData(Schedule schedule, ScheduleRemoveListener scheduleRemoveListener) {
        this.schedule = schedule;
        this.scheduleRemoveListener = scheduleRemoveListener;

        LocalTime startTime = schedule.getStartTime();
        LocalTime endTime = schedule.getEndTime();

        comboAttraction.setValue(schedule.getAttraction());
        comboArtist.setValue(schedule.getArtist());
        startTimer.getValueFactory().setValue(startTime != null ? startTime : LocalTime.MIN);
        endTimer.getValueFactory().setValue(endTime != null ? endTime : LocalTime.MIN);
    }

    private void loadComboItems() {
        List<Attraction> attractions = AttractionsManager.getInstance().getAll();
        List<Artist> artists = ArtistManager.getInstance().getAll();

        comboAttraction.setItems(FXCollections.observableArrayList(attractions));
        comboArtist.setItems(FXCollections.observableArrayList(artists));
    }

    private void loadTimeSpinners() {
        startTimer.setMinWidth(220);
        endTimer.setMinWidth(220);
        
        startTimeWrapper.getChildren().add(startTimer);
        endTimeWrapper.getChildren().add(endTimer);
    }

    private TimeSpinner startTimer = new TimeSpinner();
    private TimeSpinner endTimer = new TimeSpinner();
}
