package db.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import db.Database;
import entities.Artist;

public class ArtistManager extends DefaultManager<Artist> {
  private static ArtistManager instance;

  public static ArtistManager getInstance() {
    if (instance == null){
      instance = new ArtistManager();
    }

    return instance;
  }
  
  private ArtistManager() {
  }
  
  public List<Artist> getAll() {
    List artistList = new ArrayList();

    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();
      artistList = session.createQuery("FROM Artist").list();
      session.getTransaction().commit();
      session.close();
    } catch (Exception e) {
      handleException(e);
    }
      
    return artistList;
  }
}
