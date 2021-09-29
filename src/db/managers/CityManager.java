package db.managers;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import entities.City;
import utils.ApplicationUtilities;

// Este manager não estende o DefaultManager pois não persiste em banco
public class CityManager {
  private static CityManager instance;

  private CityManager() {
  }

  public static CityManager getInstance() {
    if (instance == null){
      instance = new CityManager();
    }

    return instance;
  }

  public City getById(int id) {
    City city = new City();

    try {
      JSONObject citiesStates;
		  JSONParser parser = new JSONParser();
      
      FileReader fileReader = new FileReader(getClass().getResource("/res/cities-states.json").getFile());

      citiesStates = (JSONObject) parser.parse(fileReader);
      JSONArray cities = (JSONArray) citiesStates.get("cities");

      city = getCityById(cities, id);
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
    
    return city;
  }

  private City getCityById(JSONArray cities, int id) {
    City result = null;

    for (Object city : cities) {
      JSONObject cityObject = (JSONObject) city;
      int cityId = (int) (cityObject.get("id"));
  
      if (cityId == id) {
        String name = (String) (cityObject.get("name"));
        Long stateId = (Long) (cityObject.get("state_id"));
        
        result = new City(name, cityId, stateId);
        break;
      }
    }
    
    return result;
  }
}
