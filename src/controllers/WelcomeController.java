package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import utils.ApplicationUtilities;

public class WelcomeController implements Initializable {
  @FXML
  private Text textWelcome;
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    loadWelcomeMessage();
  }

  private void loadWelcomeMessage() {
    User activeUser = ApplicationUtilities.getInstance().getActiveUser();
    textWelcome.setText("Olá, " + activeUser.getName());
  }
}
