package db.managers;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import entities.City;
import entities.State;
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

  public City getById(Long id) {
    return getCityByIdFromJson(id);
  }

  public List<City> getAllByState(State state) {
    List<City> cities = getCitiesByStateFromJson(state);
    
    Collections.sort(cities, new Comparator<City>(){
      @Override
      public int compare(City o1, City o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });

    return cities;
  }

  private List<City> getCitiesByStateFromJson(State state) {
    List<City> result = new ArrayList<City>();
    
    JSONArray jsonCities = getJsonArrayOfCities();
    
    for (Object city : jsonCities) {
      JSONObject cityObject = (JSONObject) city;
      
      String name = (String) (cityObject.get("name"));
      Long cityId = (Long) (cityObject.get("id"));
      Long stateId = (Long) (cityObject.get("state_id"));

      if (stateId.equals(state.getId())) {
        result.add(new City(name, cityId, stateId));
      }
    }
    
    return result;
  }
  
  private City getCityByIdFromJson(Long id) {
    City result = null;
    
    JSONArray jsonCities = getJsonArrayOfCities();
    
    for (Object city : jsonCities) {
      JSONObject cityObject = (JSONObject) city;
      Long cityId = (Long) (cityObject.get("id"));
      
      if (cityId.equals(id)) {
        String name = (String) (cityObject.get("name"));
        Long stateId = (Long) (cityObject.get("state_id"));
        
        result = new City(name, cityId, stateId);
        break;
      }
    }
    
    return result;
  }

  private JSONArray getJsonArrayOfCities() {
    JSONArray result = new JSONArray();

    try {
      JSONObject citiesStates;
      JSONParser parser = new JSONParser();
      
      FileReader fileReader = new FileReader(getClass().getResource("/res/cities-states.json").getFile());

      citiesStates = (JSONObject) parser.parse(fileReader);
      result = (JSONArray) citiesStates.get("cities");
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
    
    return result;
  }
}
