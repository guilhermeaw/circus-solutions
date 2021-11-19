package controllers;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import common.EditorCallback;
import db.managers.ArtistManager;
import editors.ArtistEditor;
import entities.Artist;
import entities.Operation;
import entities.Pane;
import filters.data.ArtistFilter;
import filters.editors.ArtistFilterEditor;
import formatters.CpfFormatter;
import formatters.PhoneFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import reports.ArtistListReport;
import services.AlertService;
import services.PermissionService;
import utils.ApplicationUtilities;
import utils.FileUtilities;

public class ArtistController implements Initializable {
  @FXML
  private Button buttonAdd;

  @FXML
  private Button buttonEdit;

  @FXML
  private Button buttonDelete;

  @FXML
  private Button buttonReport;

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
    buttonReport.setTooltip(new Tooltip( "Gerar Relatório" ));

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
      List<Artist> artists = ArtistManager.getInstance().getByFilter(filter);
      //List<Artist> artists = ArtistManager.getInstance().getAll();
      ObservableList<Artist> artistsObservableList = FXCollections.observableArrayList(artists);

      nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getName()));
      phoneColumn.setCellValueFactory(column -> new SimpleStringProperty(PhoneFormatter.format(column.getValue().getPhone())));
      cpfColumn.setCellValueFactory(column -> new SimpleStringProperty(CpfFormatter.format(column.getValue().getCpf())));
      occupationColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getOccupation().getName()));

      artistsTable.setItems(artistsObservableList);
    } catch (Exception e) {
      ApplicationUtilities.getInstance().handleException(e);
    }
  }

  @FXML
  public void handleAddArtist(ActionEvent event) {
    if (PermissionService.hasAccess(Operation.INSERT, Pane.ARTISTS)) {
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
  }

  @FXML
  public void handleDeleteArtist(ActionEvent event) {
    if (PermissionService.hasAccess(Operation.DELETE, Pane.ARTISTS)) {
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
  }

  @FXML
  public void handleEditArtist(ActionEvent event) {
    if (PermissionService.hasAccess(Operation.DELETE, Pane.ARTISTS)) {
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

  @FXML
  public void handleReport() {
    try {
      File file = FileUtilities.saveFile( "Imprimir Relatório", "ArtistListReport-" + System.currentTimeMillis() +".pdf" );

      if (file != null) {
          ArtistListReport report = new ArtistListReport(ArtistManager.getInstance().getByFilter(filter));
          report.generatePDF(file);
      }
    } catch (Exception e) {
        ApplicationUtilities.getInstance().handleException(e);
    }
  }

  @FXML
  void handleOpenFilter(ActionEvent event) {
    new ArtistFilterEditor(new EditorCallback<ArtistFilter>(filter) {
      @Override
      public void onEvent() {
          try {
              refreshContent();
          } catch ( Exception e ) {
              ApplicationUtilities.getInstance().handleException(e);
          }
      }
    }).open();
  }

  private ArtistFilter filter = new ArtistFilter();
}