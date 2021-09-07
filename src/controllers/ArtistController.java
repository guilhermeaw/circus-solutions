package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import common.EditorCallback;
import db.managers.ArtistManager;
import editors.ArtistEditor;
import entities.Artist;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import services.AlertService;
import utils.ApplicationUtilities;

public class ArtistController implements Initializable {
  @FXML
  private Button buttonAdd;

  @FXML
  private Button buttonEdit;

  @FXML
  private Button buttonDelete;

  @FXML
  private TableView<Artist> artistsTable;

  @FXML
  private TableColumn<Artist, String> nameColumn;

  @FXML
  private TableColumn<Artist, String> occupationColumn;

  @FXML
  private TableColumn<Artist, String> phoneColumn;

  @FXML
  private TableColumn<Artist, String> cpfColumn;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    refreshContent();

    buttonEdit.setDisable(true);
    buttonDelete.setDisable(true);

    artistsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        buttonEdit.setDisable(false);
        buttonDelete.setDisable(false);
      } else {
        buttonEdit.setDisable(true);
        buttonDelete.setDisable(true);
      }
    });
  }
  
  public void refreshContent() {
    try {
      List<Artist> artists = ArtistManager.getInstance().getAll();
      ObservableList<Artist> artistsObservableList = FXCollections.observableArrayList(artists);

      nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getName()));
      phoneColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getPhone()));
      cpfColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getCpf()));
      occupationColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getOccupation().getName()));

      artistsTable.setItems(artistsObservableList);
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
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
    Artist selectedArtist = artistsTable.getSelectionModel().getSelectedItem();

    if (selectedArtist != null) {
      if (AlertService.showConfirmation("Tem certeza que deseja excluir o artista " + selectedArtist.getName() + "?")) {
        try {
          ArtistManager.getInstance().delete(selectedArtist);

          refreshContent();
        } catch (Exception e) {
          ApplicationUtilities.getInstance().handleException(e);
        }
      }
    } else {
      AlertService.showWarning("É necessário selecionar um artista");
    }
  }

  @FXML
  public void handleEditArtist(ActionEvent event) {
    Artist selectedArtist = artistsTable.getSelectionModel().getSelectedItem();

    if (selectedArtist != null) {
      new ArtistEditor(new EditorCallback<Artist>(selectedArtist) {
        @Override
        public void onEvent() {
          try {
            ArtistManager.getInstance().update((Artist) getSource());

            refreshContent();
          } catch ( Exception e ) {
            ApplicationUtilities.getInstance().handleException(e);
          }
        }
      }).open();
    } else {
      AlertService.showWarning("É necessário selecionar um artista");
    }
  }
}