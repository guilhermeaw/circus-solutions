package db.managers;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

  public State getById(Long id) {
    return getStateByIdFromJson(id);
  }
  
  public List<State> getAll() {
    List<State> states = getStatesFromJson();

    Collections.sort(states, new Comparator<State>() {
      @Override
      public int compare(State o1, State o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });
    
    return states;
  }
  
  private State getStateByIdFromJson(Long id) {
    State result = null;

    JSONArray jsonStates = getJsonArrayOfStates();
    
    for (Object state : jsonStates) {
      JSONObject StateObject = (JSONObject) state;
      Long stateId = (Long) (StateObject.get("id"));
  
      if (stateId.equals(id)) {
        String name = (String) (StateObject.get("name"));
        
        result = new State(name, stateId);
        break;
      }
    }
    
    return result;
  }
  
  private List<State> getStatesFromJson() {
    List<State> result = new ArrayList<State>();
    
    JSONArray jsonStates = getJsonArrayOfStates();
    
    for (Object state : jsonStates) {
      JSONObject stateObject = (JSONObject) state;
      
      String name = (String) (stateObject.get("name"));
      Long stateId = (Long) (stateObject.get("id"));
      
      result.add(new State(name, stateId));
    }
    
    return result;
  }

  private JSONArray getJsonArrayOfStates() {
    JSONArray result = new JSONArray();

    try {
      JSONObject citiesStates;
      JSONParser parser = new JSONParser();
      
      InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/res/cities-states.json"));

      citiesStates = (JSONObject) parser.parse(reader);
      result = (JSONArray) citiesStates.get("states");
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
    
    return result;
  }
}
