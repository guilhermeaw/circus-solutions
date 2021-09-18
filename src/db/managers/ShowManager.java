package db.managers;

import entities.City;
import entities.Show;

public class ShowManager extends DefaultManager<Show> {
  private static ShowManager instance;

  private ShowManager() {
  }

  public static ShowManager getInstance() {
    if (instance == null){
      instance = new ShowManager();
    }

    return instance;
  }

  @Override
  public Show getById(Class<Show> class1, int id) {
      Show show = super.getById(class1, id);
      City city = CityManager.getInstance().getById(show.getCityId());
      
      show.setCity(city);

      return show;
  }
}
