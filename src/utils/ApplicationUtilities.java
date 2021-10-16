package utils;

import entities.User;
import javafx.stage.Stage;
import services.AlertService;
import services.ErrorService;

import java.io.PrintWriter;
import java.io.StringWriter;
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
    handleException(e, true);
  }

  public void handleException(Exception e, boolean shouldCreateErrorLog) {
    StringWriter sw = new StringWriter();
    e.printStackTrace(new PrintWriter(sw));
    String exceptionAsString = sw.toString();

    String errorMessage = e.getMessage() + "\n\n" + exceptionAsString;

    AlertService.showError(errorMessage);
    e.printStackTrace();

    if (shouldCreateErrorLog) {
        ErrorService.create(e);
    }
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
