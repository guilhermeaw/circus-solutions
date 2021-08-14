import db.managers.ArtistManager;
import entities.Artist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TesteController {
  @FXML
  private Button buttonTest;

  @FXML
  void handleAddArtist(ActionEvent event) {
    Artist artist = new Artist();
    artist.setId(1);
    artist.setName("Guilherme");

    try {
      new ArtistManager().create(artist);
    } catch(Exception error) {

    }
  }
}