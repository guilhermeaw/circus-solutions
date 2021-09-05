package utils;

import entities.User;
import javafx.stage.Stage;
import java.util.List;

public class ApplicationUtilities {
  private static ApplicationUtilities instance;

  public static ApplicationUtilities getInstance() {
    if (instance == null) {
        instance = new ApplicationUtilities();
    }

    return instance;
  }

  private Stage primaryStage;
  private User activeUser;

  public Stage getPrimaryStage() {
      return primaryStage;
  }

  public void setPrimaryStage(Stage primaryStage) {
      this.primaryStage = primaryStage;
  }

  public void handleException(Exception e) {
    e.printStackTrace();
  }

  public User getActiveUser() {
      return activeUser;
  }

  public void setActiveUser(User activeUser) {
      this.activeUser = activeUser;
  }

  public String formatErrorMessage(List<String> errors) {
    String errorMessage = "";

    for (String error : errors) {
        errorMessage += "\n" + error;
    }

    return errorMessage;
}
}
