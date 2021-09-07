package db.managers;

import java.util.List;

import org.hibernate.Session;

import db.Database;
import entities.Artist;

public class ArtistManager implements IDefaultManager<Artist> {
  private static ArtistManager instance;
  private Database db = Database.getInstance();

  private void ArtistManager() {
  }

  public static ArtistManager getInstance() {
    if (instance == null){
      instance = new ArtistManager();
    }

    return instance;
  }
  
  @Override
  public void create(Artist value) throws Exception {
    Database db = Database.getInstance();
    Session session = db.openSession();

    session.beginTransaction();
    session.save(value);
    session.getTransaction().commit();
    session.close();    
  }

  @Override
  public void update(Artist value) throws Exception {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void delete(Artist value) throws Exception {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Artist getById(int id) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Artist> getAll() throws Exception {
    // TODO Auto-generated method stub
    return null;
  }
  
}
