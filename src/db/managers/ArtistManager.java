package db.managers;

import java.util.List;

import entities.Artist;

public class ArtistManager extends DefaultManager<Artist> {
  private static ArtistManager instance;

  public static ArtistManager getInstance() {
    if (instance == null){
      instance = new ArtistManager();
    }

    return instance;
  }
  
  private void ArtistManager() {
  }
  
  public List<Artist> getAll() {
    // TODO Auto-generated method stub
    return null;
  }
}
