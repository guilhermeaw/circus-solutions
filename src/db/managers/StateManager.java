package db.managers;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import entities.State;
import utils.ApplicationUtilities;

// Este manager não estende o DefaultManager pois não persiste em banco
public class StateManager {
  private static StateManager instance;

  private StateManager() {
  }

  public static StateManager getInstance() {
    if (instance == null){
      instance = new StateManager();
    }

    return instance;
  }

  public State getById(int id) {
    State state = new State();

    try {
      JSONObject citiesStates;
		  JSONParser parser = new JSONParser();
      
      FileReader fileReader = new FileReader(getClass().getResource("/res/cities-states.json").getFile());

      citiesStates = (JSONObject) parser.parse(fileReader);
      JSONArray cities = (JSONArray) citiesStates.get("cities");

      state = getStateById(cities, id);
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
    
    return state;
  }

  private State getStateById(JSONArray cities, int id) {
    State result = null;

    for (Object State : cities) {
      JSONObject StateObject = (JSONObject) State;
      Long stateId = (Long) (StateObject.get("id"));
  
      if (stateId == id) {
        String name = (String) (StateObject.get("name"));
        
        result = new State(name, stateId);
        break;
      }
    }
    
    return result;
  }
}
