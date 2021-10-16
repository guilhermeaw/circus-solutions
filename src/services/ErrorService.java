package services;

import java.sql.Timestamp;
import java.util.Date;

import db.managers.ErrorManager;
import entities.Error;
import utils.ApplicationUtilities;

public class ErrorService {
  public static void create(Exception e) {
    Error error = new Error();
    error.setUser(ApplicationUtilities.getInstance().getActiveUser());
    error.setDate(new Timestamp(new Date().getTime()));
    error.setError(e.getMessage());

    ErrorManager.getInstance().create(error);
  }
}
