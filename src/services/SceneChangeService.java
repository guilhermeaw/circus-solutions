package services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import utils.ApplicationUtilities;

public class SceneChangeService {
  public void changeSceneTo(String newScene) {
    try {
      Parent pane = FXMLLoader.load(getClass().getResource(newScene));

      Stage stage = ApplicationUtilities.getInstance().getPrimaryStage();
      stage.getScene().setRoot(pane);
    } catch (Exception e) {
        ApplicationUtilities.getInstance().handleException(e);
    }
  }
}
