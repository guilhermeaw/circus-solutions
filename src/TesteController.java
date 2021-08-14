import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import db.Database;
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

    Database db = Database.getInstance();
    Session session = db.getSession();

    session.beginTransaction();
    session.save(artist);
    session.getTransaction().commit();
    session.close();
  }
}