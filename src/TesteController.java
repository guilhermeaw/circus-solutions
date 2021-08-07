import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TesteController {
  private SessionFactory sessionFactory;

  @FXML
  private Button buttonTest;

  @FXML
  void handleAddArtist(ActionEvent event) {
    try {
      setUp();
    } catch (Exception e) {

    }

    Artist artist = new Artist();
    artist.setId(1);
    artist.setName("Guilherme");

    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(artist);
    session.getTransaction().commit();
    session.close();
  }

  protected void setUp() throws Exception {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
        .configure() // configures settings from hibernate.cfg.xml
        .build();
    try {
      sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }
    catch (Exception e) {
      // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
      // so destroy it manually.
      StandardServiceRegistryBuilder.destroy( registry );
    }
  }
}