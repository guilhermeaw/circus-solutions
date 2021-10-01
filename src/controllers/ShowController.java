package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Duration;
import services.AlertService;
import services.ShowService;
import utils.ApplicationUtilities;
import utils.DateUtils;

public class ShowController implements Initializable {
    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonStartSales;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshContent();

        buttonEdit.setDisable(true);
        buttonDelete.setDisable(true);
        buttonStartSales.setDisable(true);

        showsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
          if (newSelection != null) {
            buttonEdit.setDisable(false);
            buttonDelete.setDisable(false);
            buttonStartSales.setDisable(false);
          } else {
            buttonEdit.setDisable(true);
            buttonDelete.setDisable(true);
            buttonStartSales.setDisable(true);
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

    @FXML
    public void handleStartSales(ActionEvent event) {
        Show selectedShow = showsTable.getSelectionModel().getSelectedItem();
        Show currentActiveShow = ShowService.getCurrentActiveShow();

        if (currentActiveShow != null) {
            if (currentActiveShow.getId() != selectedShow.getId()) {
                AlertService.showWarning("Já existe um show com as vendas ativas. É necessário primeiramente encerrar as vendas do show ativo, através da bilheteria");
                return;
            } 

            AlertService.showWarning("O show já está com as vendas ativas na bilheteria");
        } else {
            if (selectedShow.getDate().before(DateUtils.getDateByLocalDate(LocalDate.now()))) {
                AlertService.showWarning("A data do show já passou, não é possível iniciar as vendas");
                return;
            }
            
            selectedShow.setIsShowActive(true);
            ShowManager.getInstance().update(selectedShow);

            Notifications activeSalesNotification = Notifications.create()
                                    .title("Espetáculo - Circus Solution")
                                    .text("Vendas abertas para o show de " + selectedShow.toString() )
                                    .position( Pos.BOTTOM_RIGHT )
                                    .hideAfter( Duration.seconds( 10 ) );

            activeSalesNotification.show();
        }
    }

    /**
     * @return Button return the buttonAdd
     */
    public Button getButtonAdd() {
        return buttonAdd;
    }

    /**
     * @param buttonAdd the buttonAdd to set
     */
    public void setButtonAdd(Button buttonAdd) {
        this.buttonAdd = buttonAdd;
    }

    /**
     * @return Button return the buttonEdit
     */
    public Button getButtonEdit() {
        return buttonEdit;
    }

    /**
     * @param buttonEdit the buttonEdit to set
     */
    public void setButtonEdit(Button buttonEdit) {
        this.buttonEdit = buttonEdit;
    }

    /**
     * @return Button return the buttonDelete
     */
    public Button getButtonDelete() {
        return buttonDelete;
    }

    /**
     * @param buttonDelete the buttonDelete to set
     */
    public void setButtonDelete(Button buttonDelete) {
        this.buttonDelete = buttonDelete;
    }

    /**
     * @return Button return the buttonStartSales
     */
    public Button getButtonStartSales() {
        return buttonStartSales;
    }

    /**
     * @param buttonStartSales the buttonStartSales to set
     */
    public void setButtonStartSales(Button buttonStartSales) {
        this.buttonStartSales = buttonStartSales;
    }

    /**
     * @return TableView<Show> return the showsTable
     */
    public TableView<Show> getShowsTable() {
        return showsTable;
    }

    /**
     * @param showsTable the showsTable to set
     */
    public void setShowsTable(TableView<Show> showsTable) {
        this.showsTable = showsTable;
    }

    /**
     * @return TableColumn<Show, String> return the dateColumn
     */
    public TableColumn<Show, String> getDateColumn() {
        return dateColumn;
    }

    /**
     * @param dateColumn the dateColumn to set
     */
    public void setDateColumn(TableColumn<Show, String> dateColumn) {
        this.dateColumn = dateColumn;
    }

    /**
     * @return TableColumn<Show, String> return the capacityColumn
     */
    public TableColumn<Show, String> getCapacityColumn() {
        return capacityColumn;
    }

    /**
     * @param capacityColumn the capacityColumn to set
     */
    public void setCapacityColumn(TableColumn<Show, String> capacityColumn) {
        this.capacityColumn = capacityColumn;
    }

    /**
     * @return TableColumn<Show, String> return the cityColumn
     */
    public TableColumn<Show, String> getCityColumn() {
        return cityColumn;
    }

    /**
     * @param cityColumn the cityColumn to set
     */
    public void setCityColumn(TableColumn<Show, String> cityColumn) {
        this.cityColumn = cityColumn;
    }

    /**
     * @return TableColumn<Show, String> return the stateColumn
     */
    public TableColumn<Show, String> getStateColumn() {
        return stateColumn;
    }

    /**
     * @param stateColumn the stateColumn to set
     */
    public void setStateColumn(TableColumn<Show, String> stateColumn) {
        this.stateColumn = stateColumn;
    }

    /**
     * @return TableColumn<Show, String> return the authorColumn
     */
    public TableColumn<Show, String> getAuthorColumn() {
        return authorColumn;
    }

    /**
     * @param authorColumn the authorColumn to set
     */
    public void setAuthorColumn(TableColumn<Show, String> authorColumn) {
        this.authorColumn = authorColumn;
    }

}
