package filters.data;

import entities.Artist;
import entities.Occupation;

public class ArtistFilter {
  private Artist artist = null;
  private Occupation occupation;

  public ArtistFilter() {}

  public Artist getArtist() {
      return artist;
  }

  public void setArtist(Artist artist) {
      this.artist = artist;
  }

  public Occupation getOccupation() {
    return occupation;
  }

  public void setOccupation(Occupation occupation) {
    this.occupation = occupation;
  }
}
