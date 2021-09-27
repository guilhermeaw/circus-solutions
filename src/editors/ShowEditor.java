package editors;

import java.util.List;

import common.DefaultEditor;
import common.EditorCallback;
import entities.Show;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import validators.EmptyValidator;

public class ShowEditor extends DefaultEditor<Show> {
    public ShowEditor(EditorCallback<Show> callback) {
        super(callback);

        initComponents();
    }

    @Override
    protected void validateInput(List<String> errors) throws Exception {
        if (!EmptyValidator.validate(textCity.getText())) {
            errors.add("É necessário informar uma cidade");
        }
    }

    @Override
    protected void obtainInput() { 
        // source.setCityId(textCity.getText().getId());
        // source.setCapacity(textCapacity.getPlainText());
        // source.setDate(textDate.getText());
        // source.setAuthor(textAuthor.getText());
        
    }

    @Override
    protected void setSource(Show source) {
        if (source.getId() != 0) {
            textCity.setText(String.valueOf(source.getCity()));
            textCapacity.setText(String.valueOf(source.getCapacity()));
            //textDate.setValue(source.getDate());
            //textAuthor.setText(String.valueOf(source.getAuthor()));
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
        grid.add(textCity, 1, 0, 1, 1);

        grid.add(labelCapacity, 0, 2, 1, 1);
        grid.add(textCapacity, 1, 2, 1, 1);

        grid.add(labelDate, 0, 4, 1, 1);
        grid.add(textDate, 1, 4, 1, 1);

        grid.add(labelAuthor, 0, 6, 1, 1);
        grid.add(textAuthor, 1, 6, 1, 1);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
        getDialogPane().setContent( grid );
    }

    private GridPane grid = new GridPane();

    private Label labelCity = new Label("Cidade: *");
    private TextField textCity = new TextField();

    private Label labelCapacity = new Label("Capacidade:");
    private TextField textCapacity = new TextField();

    private Label labelDate = new Label("Data: *");
    private DatePicker textDate = new DatePicker();

    private Label labelAuthor = new Label("Autor:");
    private TextField textAuthor = new TextField();
}
