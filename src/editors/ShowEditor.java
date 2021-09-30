package editors;

import java.time.LocalDate;
import java.util.List;

import common.DefaultEditor;
import common.EditorCallback;
import db.managers.CityManager;
import db.managers.StateManager;
import entities.City;
import entities.Show;
import entities.State;
import entities.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import utils.ApplicationUtilities;
import utils.DateUtils;
import validators.EmptyValidator;

public class ShowEditor extends DefaultEditor<Show> {
    public ShowEditor(EditorCallback<Show> callback) {
        super(callback);

        initComponents();
    }

    @Override
    protected void validateInput(List<String> errors) throws Exception {
        LocalDate showDate = textDate.getValue();
        
        if (!EmptyValidator.validate(textCapacity.getText())) {
            errors.add("É necessário informar a capacidade");
        }

        if (cbState.getValue() == null) {
            errors.add("É necessário informar o estado");
        }

        if (cbCity.getValue() == null) {
            errors.add("É necessário informar a cidade");
        }

        if (showDate == null) {
            errors.add("É necessário informar a data");
        } else if (showDate.isBefore(LocalDate.now())) {
            errors.add("A data deve ser posterior à data atual");
        }
    }

    @Override
    protected void obtainInput() { 
        source.setCityId(cbCity.getValue().getId());
        source.setCapacity(Integer.parseInt(textCapacity.getText()));
        source.setDate(DateUtils.getDateByLocalDate(textDate.getValue()));
        source.setAuthor(ApplicationUtilities.getInstance().getActiveUser());
    }

    @Override
    protected void setSource(Show source) {
        if (source.getId() != 0) {
            City city = source.getCity();

            cbState.setValue(StateManager.getInstance().getById(city.getStateId()));
            cbCity.setValue(city);
            textCapacity.setText(String.valueOf(source.getCapacity()));
            textDate.setValue(DateUtils.getLocalDateByDate(source.getDate()));
            cbAuthor.setValue(source.getAuthor());
        }
    }

    private void loadComboItems() {
        List<State> states = StateManager.getInstance().getAll();

        State selectedState = cbState.getValue();

        if (selectedState != null) {
            cbCity.setDisable(false);
            loadCitiesFrom(selectedState);
        }

        cbState.setItems(FXCollections.observableArrayList(states));
        cbAuthor.setValue(ApplicationUtilities.getInstance().getActiveUser());
    }

    private void loadCitiesFrom(State state) {
        List<City> cities = CityManager.getInstance().getAllByState(state);
        City selectedCity = cbCity.getValue();

        if (selectedCity != null && !selectedCity.getStateId().equals(state.getId())) {
            cbCity.setValue(null);
        }

        cbCity.setItems(FXCollections.observableArrayList(cities));
    }

    private void initComponents() {
        setTitle("Editor de Show");
        setHeaderText("Editor de Show");

        cbAuthor.setDisable(true);
        cbCity.setDisable(true);

        setSource(source);
        loadComboItems();

        cbState.setPrefWidth(400);
        cbCity.setPrefWidth(400);
        cbAuthor.setPrefWidth(400);

        grid.setPadding(new Insets( 500 ));
        grid.setStyle("-fx-padding: 30;");
        grid.setPrefWidth(600);
        grid.setVgap(15);
        grid.setHgap(15);

        grid.add(labelState, 0, 0, 1, 1);
        grid.add(cbState, 1, 0, 1, 1);
        
        grid.add(labelCity, 0, 1, 1, 1);
        grid.add(cbCity, 1, 1, 1, 1);

        grid.add(labelCapacity, 0, 2, 1, 1);
        grid.add(textCapacity, 1, 2, 1, 1);

        grid.add(labelDate, 0, 3, 1, 1);
        grid.add(textDate, 1, 3, 1, 1);

        grid.add(labelAuthor, 0, 4, 1, 1);
        grid.add(cbAuthor, 1, 4, 1, 1);
  
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
        getDialogPane().setContent( grid );

        cbState.valueProperty().addListener(new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
                if (newValue != null) {
                    loadCitiesFrom(newValue);
                    cbCity.setDisable(false);
                } else {
                    cbCity.setDisable(true);
                }
            }
        });
    }

    private GridPane grid = new GridPane();

    private Label labelCity = new Label("Cidade: *");
    private ComboBox<City> cbCity = new ComboBox();

    private Label labelState = new Label("Estado: *");
    private ComboBox<State> cbState = new ComboBox();

    private Label labelCapacity = new Label("Capacidade: *");
    private TextField textCapacity = new TextField();

    private Label labelDate = new Label("Data: *");
    private DatePicker textDate = new DatePicker();

    private Label labelAuthor = new Label("Autor: *");
    private ComboBox<User> cbAuthor = new ComboBox();
}
