package utils;

import javafx.stage.Stage;

public class ApplicationUtilities {
  private static ApplicationUtilities instance;

  public static ApplicationUtilities getInstance() {
    if (instance == null) {
        instance = new ApplicationUtilities();
    }

    return instance;
  }

  private Stage primaryStage;

  public Stage getPrimaryStage() {
      return primaryStage;
  }

  public void setPrimaryStage(Stage primaryStage) {
      this.primaryStage = primaryStage;
  }

  public void handleException(Exception e) {
    e.printStackTrace();
  }
}
