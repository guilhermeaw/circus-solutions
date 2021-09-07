package controllers;

import common.EditorCallback;
import db.managers.ArtistManager;
import editors.ArtistEditor;
import entities.Artist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.ApplicationUtilities;

public class ArtistController {
  @FXML
  private Button buttonAdd;

  @FXML
  private Button buttonEdit;

  @FXML
  private Button buttonDelete;

  public void refreshContent() {
    
  }

  @FXML
  public void handleAddArtist(ActionEvent event) {
    new ArtistEditor(new EditorCallback<Artist>(new Artist()) {
      @Override
      public void onEvent() {
        try {
          ArtistManager.getInstance().create((Artist) getSource());

          refreshContent();
        } catch (Exception e) {
          ApplicationUtilities.getInstance().handleException(e);
        }
      }
    }).open();
  }

  @FXML
  public void handleDeleteArtist(ActionEvent event) {

  }

  @FXML
  public void handleEditArtist(ActionEvent event) {

  }
}