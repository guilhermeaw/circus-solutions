package editors;

import entities.Occupation;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import validators.EmptyValidator;

import java.util.List;

import common.DefaultEditor;
import common.EditorCallback;

public class OccupationEditor extends DefaultEditor<Occupation> {
    public OccupationEditor(EditorCallback<Occupation> callback) {
        super(callback);

        initComponents();
    }

    @Override
    protected void validateInput(List<String> errors) throws Exception {
        if (!EmptyValidator.validate(textName.getText())) {
            errors.add("É necessário informar um nome");
        }
    }

    @Override
    protected void obtainInput() {
        source.setName(textName.getText());
        source.setDescription(textDescription.getText());
        
    }

    @Override
    protected void setSource(Occupation source) {
        if (source.getId() != 0) {
            textName.setText(source.getName());
            textDescription.setText(source.getDescription());
        }
    }

    private void initComponents() {
        setTitle("Editor de Cargo");
        setHeaderText("Editor de Cargo");

        setSource(source);

        grid.setPadding(new Insets( 500 ));
        grid.setStyle("-fx-padding: 30;");
        grid.setPrefWidth(350);
        grid.setVgap(15);
        grid.setHgap(15);

        grid.add(labelName, 0, 0, 1, 1);
        grid.add(textName, 1, 0, 1, 1);

        grid.add(labelDescription, 0, 2, 1, 1);
        grid.add(textDescription, 1, 2, 1, 1);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
        getDialogPane().setContent( grid );
    }

    private GridPane grid = new GridPane();

    private Label labelName = new Label("Nome: *");
    private TextField textName = new TextField();

    private Label labelDescription = new Label("Descrição:");
    private TextField textDescription = new TextField();
}
