package filters.editors;

import java.util.List;

import common.DefaultEditor;
import common.EditorCallback;
import db.managers.ArtistManager;
import db.managers.OccupationsManager;
import entities.Artist;
import entities.Occupation;
import filters.data.ArtistFilter;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ArtistFilterEditor extends DefaultEditor<ArtistFilter> {
  public ArtistFilterEditor(EditorCallback<ArtistFilter> callback) {
    super(callback);
    initComponents();
  }

  @Override
  protected void validateInput(List<String> errors) throws Exception {
    
  }

  @Override
  protected void obtainInput() {
    source.setArtist(cbArtist.getValue());
    source.setOccupation(cbOccupation.getValue());
  }

  @Override
  protected void setSource(ArtistFilter source) {
    cbArtist.setValue(source.getArtist());
    cbOccupation.setValue(source.getOccupation());
  }

  @Override
  protected void onClear() {
    setSource(new ArtistFilter());
  }

  private void loadComboItems() {
    List<Artist> artists = ArtistManager.getInstance().getAll();
    List<Occupation> occupations = OccupationsManager.getInstance().getAll();
    
    cbOccupation.setItems(FXCollections.observableArrayList(occupations));
    cbArtist.setItems(FXCollections.observableArrayList(artists));
  }
  
  private void initComponents() {
    setTitle("Filtro de Artistas");
    setHeaderText("Filtro de Artistas");

    cbArtist.setPrefWidth(200);
    cbOccupation.setPrefWidth(200);

    setSource(source);
    activeClearButton();
    loadComboItems();

    grid.setPadding(new Insets(520));
    grid.setStyle("-fx-padding: 30;");
    grid.setPrefWidth(520);
    grid.setVgap(15);
    grid.setHgap(15);

    grid.add(lbArtist, 0, 0, 1, 1);
    grid.add(cbArtist, 1, 0, 1, 1);

    grid.add(lbOccupation, 0, 1, 1, 1);
    grid.add(cbOccupation, 1, 1, 1, 1);

    ColumnConstraints cc = new ColumnConstraints();
    cc.setHgrow(Priority.ALWAYS);

    grid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
    getDialogPane().setContent(grid);
  }

  private GridPane grid = new GridPane();

  private Label lbArtist = new Label("Artista:");
  private ComboBox<Artist> cbArtist = new ComboBox<Artist>();

  private Label lbOccupation = new Label("Cargo:");
  private ComboBox<Occupation> cbOccupation = new ComboBox<Occupation>();
}
