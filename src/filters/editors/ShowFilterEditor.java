package filters.editors;

import java.sql.Date;


import java.time.LocalDate;
import java.util.List;

import common.DefaultEditor;
import common.EditorCallback;
import db.managers.CityManager;
import db.managers.ShowManager;
import db.managers.UserManager;
import entities.City;
import entities.User;
import filters.data.ShowFilter;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import utils.DateUtils;

public class ShowFilterEditor extends DefaultEditor<ShowFilter> {
  public ShowFilterEditor(EditorCallback<ShowFilter> callback) {
    super(callback);
    initComponents();
  }

  @Override
  protected void validateInput(List<String> errors) throws Exception {
    LocalDate startDateRange = dpStartDate.getValue();
    LocalDate endDateRange = dpEndDate.getValue();

    boolean someDateIsFilled = startDateRange != null || endDateRange != null;
    boolean someDateIsNotFilled = startDateRange == null || endDateRange == null;

    boolean allDatesFilled = startDateRange != null && endDateRange != null;

    if (someDateIsFilled && someDateIsNotFilled) {
      errors.add("É necessário informar a data de início e de fim");
    }

    if (allDatesFilled && endDateRange.isBefore(startDateRange)) {
      errors.add("A data final deve ser posterior à data inicial");
    }
  }

  @Override
  protected void obtainInput() {
    LocalDate startDate = dpStartDate.getValue();
    LocalDate endDate = dpEndDate.getValue();

    source.setAuthor(cbAuthor.getValue());
    source.setCity(cbCity.getValue());

    source.setStartDate(startDate != null ? DateUtils.getDateByLocalDate(dpStartDate.getValue()) : null);
    source.setEndDate(endDate != null ? DateUtils.getDateByLocalDate(dpEndDate.getValue()) : null);
  }

  @Override
  protected void setSource(ShowFilter source) {
    Date startDate = source.getStartDate();
    Date endDate = source.getEndDate();

    cbAuthor.setValue(source.getAuthor());
    cbCity.setValue(source.getCity());
    dpStartDate.setValue(startDate != null ? DateUtils.getLocalDateByDate(startDate) : null);
    dpEndDate.setValue(endDate != null ? DateUtils.getLocalDateByDate(endDate) : null);
  }

  @Override
  protected void onClear() {
    setSource(new ShowFilter());
  }

  private void loadComboItems() {
    List<User> authors = UserManager.getInstance().getAll();
    
    List<Long> idsOfCitiesWithShows = ShowManager.getInstance().getIdsOfCitiesWithShows();
    List<City> cities = CityManager.getInstance().getByIdList(idsOfCitiesWithShows);
    
    cbCity.setItems(FXCollections.observableArrayList(cities));
    cbAuthor.setItems(FXCollections.observableArrayList(authors));
  }
  
  private void initComponents() {
    setTitle("Filtro de Auditoria");
    setHeaderText("Filtro de Auditoria");

    cbAuthor.setPrefWidth(200);
    cbCity.setPrefWidth(200);

    dpStartDate.setPrefWidth(150);
    dpEndDate.setPrefWidth(150);

    setSource(source);
    activeClearButton();
    loadComboItems();

    grid.setPadding(new Insets(520));
    grid.setStyle("-fx-padding: 30;");
    grid.setPrefWidth(520);
    grid.setVgap(15);
    grid.setHgap(15);

    grid.add(lbAuthor, 0, 0, 1, 1);
    grid.add(cbAuthor, 1, 0, 1, 1);

    grid.add(lbCity, 0, 1, 1, 1);
    grid.add(cbCity, 1, 1, 1, 1);

    grid.add(lbDateRange, 0, 2, 1, 1);
    grid.add(dpStartDate, 1, 2, 1, 1);
    grid.add(lbRangeTo, 2, 2, 1, 1);
    grid.add(dpEndDate, 3, 2, 1, 1);

    ColumnConstraints cc = new ColumnConstraints();
    cc.setHgrow(Priority.ALWAYS);

    grid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
    getDialogPane().setContent(grid);
  }

  private GridPane grid = new GridPane();

  private Label lbAuthor = new Label("Autor:");
  private ComboBox<User> cbAuthor = new ComboBox<User>();

  private Label lbCity = new Label("Cidade:");
  private ComboBox<City> cbCity = new ComboBox<City>();

  private Label lbDateRange = new Label("Data:");
  private DatePicker dpStartDate = new DatePicker();
  private Label lbRangeTo = new Label("até");
  private DatePicker dpEndDate = new DatePicker();
}
