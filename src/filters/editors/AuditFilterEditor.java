package filters.editors;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import common.DefaultEditor;
import common.EditorCallback;
import db.managers.UserManager;
import entities.Operation;
import entities.User;
import filters.data.AuditFilter;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import utils.DateUtils;

public class AuditFilterEditor extends DefaultEditor<AuditFilter> {
  public AuditFilterEditor(EditorCallback<AuditFilter> callback) {
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

    source.setUser(cbUser.getValue());
    source.setType(cbType.getValue());

    source.setStartDate(startDate != null ? DateUtils.getDateByLocalDate(dpStartDate.getValue()) : null);
    source.setEndDate(endDate != null ? DateUtils.getDateByLocalDate(dpEndDate.getValue()) : null);
  }

  @Override
  protected void setSource(AuditFilter source) {
    Date startDate = source.getStartDate();
    Date endDate = source.getEndDate();

    cbUser.setValue(source.getUser());
    cbType.setValue(source.getType());
    dpStartDate.setValue(startDate != null ? DateUtils.getLocalDateByDate(startDate) : null);
    dpEndDate.setValue(endDate != null ? DateUtils.getLocalDateByDate(endDate) : null);
  }

  @Override
  protected void onClear() {
    setSource(new AuditFilter());
  }

  private void loadComboItems() {
    List<User> users = UserManager.getInstance().getAll();
    List<String> types = Arrays.stream(Operation.values())
      .map(Operation::getName)
      .filter(value -> !value.equals(Operation.VIEW.getName()))
      .collect(Collectors.toList());
    
    cbType.setItems(FXCollections.observableArrayList(types));
    cbUser.setItems(FXCollections.observableArrayList(users));
  }
  
  private void initComponents() {
    setTitle("Filtro de Auditoria");
    setHeaderText("Filtro de Auditoria");

    cbUser.setPrefWidth(200);
    cbType.setPrefWidth(200);

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

    grid.add(lbUser, 0, 0, 1, 1);
    grid.add(cbUser, 1, 0, 1, 1);

    grid.add(lbType, 0, 1, 1, 1);
    grid.add(cbType, 1, 1, 1, 1);

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

  private Label lbUser = new Label("Usuário:");
  private ComboBox<User> cbUser = new ComboBox<User>();

  private Label lbType = new Label("Tipo:");
  private ComboBox<String> cbType = new ComboBox<String>();

  private Label lbDateRange = new Label("Data:");
  private DatePicker dpStartDate = new DatePicker();
  private Label lbRangeTo = new Label("até");
  private DatePicker dpEndDate = new DatePicker();
}
