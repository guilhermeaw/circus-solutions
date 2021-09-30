package services;

import db.managers.ShowManager;
import entities.Show;

public class ShowService {
  public static Show getCurrentActiveShow() {
    return ShowManager.getInstance().getActiveShow();
  }
}
