package controllers;

import entities.Artist;
import entities.Attraction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class ScheduleItemController {

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

    @FXML
    public void handleDeleteSchedule(ActionEvent event) {

    }

    public void setData() {
      
    }
}
