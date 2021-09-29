package editors;

import java.util.List;

import common.DefaultEditor;
import common.EditorCallback;
import entities.City;
import entities.Show;
import entities.User;
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
        if (!EmptyValidator.validate(textCapacity.getText())) {
            errors.add("É necessário informar a capacidade");
        }
    }

    @Override
    protected void obtainInput() { 
        //source.setCityId(cbCity.getValue().getId());
        source.setCapacity(Integer.parseInt(textCapacity.getText()));
        source.setDate(DateUtils.getDateByLocalDate(textDate.getValue()));
        source.setAuthor(ApplicationUtilities.getInstance().getActiveUser());
    }

    @Override
    protected void setSource(Show source) {
        if (source.getId() != 0) {
            //cbCity.setValue(source.getCity());
            textCapacity.setText(String.valueOf(source.getCapacity()));
            textDate.setPromptText(String.valueOf(source.getDate()));
            cbAuthor.setValue(source.getAuthor());
        }
    }

    private void initComponents() {
        setTitle("Editor do Show");
        setHeaderText("Editor do Show");

        setSource(source);

        grid.setPadding(new Insets( 500 ));
        grid.setStyle("-fx-padding: 30;");
        grid.setPrefWidth(500);
        grid.setVgap(15);
        grid.setHgap(15);

        grid.add(labelCity, 0, 0, 1, 1);
        grid.add(cbCity, 1, 0, 1, 1);

        grid.add(labelCapacity, 0, 2, 1, 1);
        grid.add(textCapacity, 1, 2, 1, 1);

        grid.add(labelDate, 0, 4, 1, 1);
        grid.add(textDate, 1, 4, 1, 1);

        grid.add(labelAuthor, 0, 6, 1, 1);
        grid.add(cbAuthor, 1, 6, 1, 1);
  
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
        getDialogPane().setContent( grid );
    }

    private GridPane grid = new GridPane();

    private Label labelCity = new Label("Cidade: *");
    private ComboBox<City> cbCity = new ComboBox();

    private Label labelCapacity = new Label("Capacidade: *");
    private TextField textCapacity = new TextField();

    private Label labelDate = new Label("Data: *");
    private DatePicker textDate = new DatePicker();

    private Label labelAuthor = new Label("Autor: *");
    private ComboBox<User> cbAuthor = new ComboBox();
}
