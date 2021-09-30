package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import common.EditorCallback;
import db.managers.ShowManager;
import editors.ShowEditor;
import entities.Show;
import formatters.DateFormatter;
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
import utils.ApplicationUtilities;

public class ShowController implements Initializable {
    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonDelete;

    @FXML
    private TableView<Show> showsTable;
   
    @FXML
    private TableColumn<Show, String> dateColumn;

    @FXML
    private TableColumn<Show, String> capacityColumn;

    @FXML
    private TableColumn<Show, String> cityColumn;

    @FXML
    private TableColumn<Show, String> stateColumn;

    @FXML
    private TableColumn<Show, String> authorColumn;

    @FXML
    private Button buttonShows;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshContent();

        buttonEdit.setDisable(true);
        buttonDelete.setDisable(true);
    
        showsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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
            List<Show> shows = ShowManager.getInstance().getAll();

            ObservableList<Show> showObservableList = FXCollections.observableArrayList(shows);          

            dateColumn.setCellValueFactory(column -> new SimpleStringProperty(DateFormatter.format(column.getValue().getDate())));
            capacityColumn.setCellValueFactory(column -> new SimpleStringProperty(String.valueOf(column.getValue().getCapacity())));
            cityColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getCity().getName()));
            authorColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getAuthor().getName()));

            showsTable.setItems(showObservableList);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    @FXML
    void handleAddShow(ActionEvent event) {   
        new ShowEditor(new EditorCallback<Show>(new Show()) {
            @Override
            public void onEvent() {
                try {
                    ShowManager.getInstance().create((Show) getSource());
                    
                    refreshContent();
                } catch ( Exception e ) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } ).open();
    }

    @FXML
    public void handleEditShow(ActionEvent event) {
        Show selectedShow = showsTable.getSelectionModel().getSelectedItem();

        if (selectedShow != null) {
            new ShowEditor(new EditorCallback<Show>(selectedShow) {
                @Override
                public void onEvent() {
                    try {
                        ShowManager.getInstance().update((Show) getSource());

                        refreshContent();
                    } catch ( Exception e ) {
                        ApplicationUtilities.getInstance().handleException(e);
                    }
                }
            } ).open();
        } else {
            AlertService.showWarning("É necessário selecionar um show");
        }
    }

    @FXML
    public void handleDeleteShow(ActionEvent event) {
        Show selectedShow = showsTable.getSelectionModel().getSelectedItem();

        if (selectedShow != null) {
            if (AlertService.showConfirmation("Tem certeza que deseja excluir o show do dia: " + selectedShow.getDate() + "?")) {
                try {
                    ShowManager.getInstance().delete(selectedShow);

                    refreshContent();
                } catch (Exception e) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } else {
            AlertService.showWarning("É necessário selecionar um show");
        }
    } 
}
