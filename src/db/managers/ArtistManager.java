package db.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.query.Query;

import db.Database;
import entities.Artist;
import entities.Occupation;
import filters.data.ArtistFilter;
import utils.ApplicationUtilities;

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

  public List<Artist> getByFilter(ArtistFilter filter) {
    List<Artist> artistList = new ArrayList<Artist>();

    try {
      Database db = Database.getInstance();
      Session session = db.openSession();
      
      session.beginTransaction();

      StringBuilder sql = new StringBuilder("FROM Artist");
      Map<String,Object> parameters = composeFilterConditions(filter, sql);

      Query<Artist> query = session.createQuery(sql.toString(), Artist.class);

      Set<String> parameterSet = parameters.keySet();
      for (Iterator<String> it = parameterSet.iterator(); it.hasNext();) {
        String parameter = it.next();
        query.setParameter(parameter, parameters.get(parameter));
      }

      artistList = query.list();
      session.getTransaction().commit();
      session.close();
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
      
    return artistList;
  }

  private Map<String,Object> composeFilterConditions(ArtistFilter filter, StringBuilder sql) {
    List<String> conditions = new ArrayList<String>();
    Map<String,Object> parameters = new HashMap<String,Object>();

    Artist artist = filter.getArtist();
    Occupation occupation = filter.getOccupation();

    if (artist != null) {
      conditions.add(" name = :artist");
      parameters.put("artist", artist.getName());
    }

    if (occupation != null) {
        conditions.add(" ref_occupation = :occupation");
        parameters.put("occupation", occupation.getId());
      }

    for (int i = 0; i < conditions.size(); i++) {
      String preffix = i == 0 ? " where" : " and";
      String condition = conditions.get(i);

      sql.append(preffix).append(condition);
    }

    return parameters;
  }
}
