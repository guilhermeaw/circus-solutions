package editors;

import java.util.List;

import common.DefaultEditor;
import common.EditorCallback;
import entities.Role;
import entities.User;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import utils.ApplicationUtilities;

public class UserEditor extends DefaultEditor<User> {
  public UserEditor(EditorCallback<User> callback) {
    super(callback);
    initComponents();
  }

  @Override
  protected void validateInput(List<String> errors) throws Exception {
    Role selectedRole = cbRole.getValue();
    Role activeUserRole = ApplicationUtilities.getInstance().getActiveUser().getRole();
    
    if (selectedRole == null) {
      errors.add("É necessário informar uma função");
    } else if (activeUserRole.getLevel() < selectedRole.getLevel()) {
      errors.add("Não é possível designar uma função mais elevada que a sua");
    }
  }

  @Override
  protected void obtainInput() {
    // Apenas o cargo do usuário pode ser alterado
    source.setRole(cbRole.getValue());
  }

  @Override
  protected void setSource(User source) {
    if (source.getId() != 0) {
      tfName.setText(source.getName());
      tfLogin.setText(source.getLogin());
      cbRole.setValue(source.getRole());
    }
  }

  private void loadComboItems() {
    cbRole.setItems(FXCollections.observableArrayList(Role.values()));
  }
  
  private void initComponents() {
    setTitle("Editor de Usuário");
    setHeaderText("Editor de Usuário");

    cbRole.setPrefWidth(300);

    loadComboItems();
    setSource(source);

    tfName.setDisable(true);
    tfLogin.setDisable(true);

    grid.setPadding(new Insets(500));
    grid.setStyle("-fx-padding: 30;");
    grid.setPrefWidth(500);
    grid.setVgap(15);
    grid.setHgap(15);

    grid.add(lbName, 0, 0, 1, 1);
    grid.add(tfName, 1, 0, 1, 1);

    grid.add(lbRole, 0, 1, 1, 1);
    grid.add(cbRole, 1, 1, 1, 1);

    grid.add(lbLogin, 0, 2, 1, 1);
    grid.add(tfLogin, 1, 2, 1, 1);

    ColumnConstraints cc = new ColumnConstraints();
    cc.setHgrow(Priority.ALWAYS);

    grid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
    getDialogPane().setContent(grid);
  }

  private GridPane grid = new GridPane();

  private Label lbName = new Label("Nome: *");
  private TextField tfName = new TextField();

  private Label lbRole = new Label("Função: *");
  private ComboBox<Role> cbRole = new ComboBox<Role>();

  private Label lbLogin = new Label("Login: *");
  private TextField tfLogin = new TextField();
}
