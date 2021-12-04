package controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.scene.control.DatePicker;
import utils.DateUtils;

public class ScheduleItemController implements Initializable {

    @FXML
    private ComboBox<Attraction> comboAttraction;

    @FXML
    private ComboBox<Artist> comboArtist;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Button buttonDelete;

    private Schedule schedule;
    private ScheduleRemoveListener scheduleRemoveListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadComboItems();

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
    }

    @FXML
    public void handleDeleteSchedule(ActionEvent event) {
        scheduleRemoveListener.onDeleteSchedule(schedule);
    }

    public void setData(Schedule schedule, ScheduleRemoveListener scheduleRemoveListener) {
        this.schedule = schedule;
        this.scheduleRemoveListener = scheduleRemoveListener;

        Timestamp startTime = schedule.getStartTime();
        Timestamp endTime = schedule.getEndTime();

        comboAttraction.setValue(schedule.getAttraction());
        comboArtist.setValue(schedule.getArtist());
        startDatePicker.setValue(startTime != null ? DateUtils.getLocalDateByDate(new Date(startTime.getTime())): null);
        endDatePicker.setValue(endTime != null ? DateUtils.getLocalDateByDate(new Date(endTime.getTime())) : null);
    }

    private void loadComboItems() {
        List<Attraction> attractions = AttractionsManager.getInstance().getAll();
        List<Artist> artists = ArtistManager.getInstance().getAll();

        comboAttraction.setItems(FXCollections.observableArrayList(attractions));
        comboArtist.setItems(FXCollections.observableArrayList(artists));
    }
}
