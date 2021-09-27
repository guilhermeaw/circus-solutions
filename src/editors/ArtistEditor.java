package editors;

import java.util.List;

import common.DefaultEditor;
import common.EditorCallback;
import common.MaskedTextField;
import db.managers.OccupationsManager;
import entities.Artist;
import entities.Occupation;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import validators.CpfValidator;
import validators.EmptyValidator;
import validators.PhoneValidator;

public class ArtistEditor extends DefaultEditor<Artist> {
  public ArtistEditor(EditorCallback<Artist> callback) {
    super(callback);
    initComponents();
  }

  @Override
  protected void validateInput(List<String> errors) throws Exception {
    if (!EmptyValidator.validate(tfName.getText())) {
      errors.add("É necessário informar um nome");
    }

    if (!CpfValidator.validate(tfCpf.getPlainText())) {
      errors.add("É necessário informar um CPF válido");
    }

    if (!PhoneValidator.validate(tfPhone.getPlainText())) {
      errors.add("É necessário informar um telefone válido");
    }

    if (cbOccupation.getValue() == null) {
      errors.add("É necessário informar um cargo");
    }
  }

  @Override
  protected void obtainInput() {
    source.setName(tfName.getText());
    source.setCpf(tfCpf.getPlainText());
    source.setPhone(tfPhone.getPlainText());
    source.setOccupation(cbOccupation.getValue());
  }

  @Override
  protected void setSource(Artist source) {
    if (source.getId() != 0) {
      tfName.setText(source.getName());
      tfCpf.setPlainText(source.getCpf());
      tfPhone.setPlainText(source.getPhone());
      cbOccupation.setValue(source.getOccupation());
    }
  }

  private void loadComboItems() {
    try {
      List<Occupation> occupations = OccupationsManager.getInstance().getAll();

      cbOccupation.setItems(FXCollections.observableArrayList(occupations));
    } catch (Exception e) {
      handleException(e);
    }
}
  
  private void initComponents() {
    setTitle("Editor de Artista");
    setHeaderText("Editor de Artista");

    cbOccupation.setPrefWidth(300);

    loadComboItems();
    setSource(source);

    grid.setPadding(new Insets(500));
    grid.setStyle("-fx-padding: 30;");
    grid.setPrefWidth(500);
    grid.setVgap(15);
    grid.setHgap(15);

    grid.add(lbName, 0, 0, 1, 1);
    grid.add(tfName, 1, 0, 1, 1);

    grid.add(lbOccupation, 0, 1, 1, 1);
    grid.add(cbOccupation, 1, 1, 1, 1);

    grid.add(lbPhone, 0, 2, 1, 1);
    grid.add(tfPhone, 1, 2, 1, 1);

    grid.add(lbCpf, 0, 3, 1, 1);
    grid.add(tfCpf, 1, 3, 1, 1);

    ColumnConstraints cc = new ColumnConstraints();
    cc.setHgrow(Priority.ALWAYS);

    grid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
    getDialogPane().setContent(grid);
  }

  private GridPane grid = new GridPane();

  private Label lbName = new Label("Nome: *");
  private TextField tfName = new TextField();

  private Label lbOccupation = new Label("Cargo: *");
  private ComboBox<Occupation> cbOccupation = new ComboBox<Occupation>();

  private Label lbPhone = new Label("Telefone: *");
  private MaskedTextField tfPhone = new MaskedTextField("(##) #####-####");

  private Label lbCpf = new Label("CPF: *");
  private MaskedTextField tfCpf = new MaskedTextField("###.###.###-##");
}
