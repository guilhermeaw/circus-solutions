package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Operation;
import entities.Pane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import services.AlertService;
import services.LoginService;
import services.PermissionService;
import services.ShowService;
import utils.ApplicationUtilities;

public class DashboardController implements Initializable {
    @FXML
    private Button buttonLogout;
    
    @FXML
    private Button buttonEditUser;

    @FXML
    private StackPane dashboardStackPane;

    @FXML
    private Button artistsButton;

    @FXML
    private Button occupationsButton;

    @FXML
    private Button attractionsButton;

    @FXML
    private Button ticketsButton;
    
    @FXML
    private Button showsButton;

    @FXML
    private Button ticketOfficeButton;

    @FXML
    private Button usersButton;

    @FXML
    private Button administrationButton;

    @FXML
    private Button graphicsButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      loadDefaultPane();
      loadViewPermissions();
      ApplicationUtilities.getInstance().setDashboardPane(dashboardStackPane);
    }

    public void handleChangePane(ActionEvent actionEvent) {
      if (actionEvent.getSource() == artistsButton) {
        loadPane("/views/components/panes/artist.fxml");
      } else if (actionEvent.getSource() == occupationsButton) {
        loadPane("/views/components/panes/occupation.fxml");
      } else if (actionEvent.getSource() == attractionsButton) {
        loadPane("/views/components/panes/attraction.fxml");
      } else if (actionEvent.getSource() == ticketsButton) {
        loadPane("/views/components/panes/tickets.fxml");
      } else if (actionEvent.getSource() == showsButton) {
        loadPane("/views/components/panes/show.fxml");
      } else if (actionEvent.getSource() == administrationButton) {
        loadPane("/views/components/panes/auditErrorsTabbedPane.fxml");
      } else if (actionEvent.getSource() == graphicsButton) {
        loadPane("/views/components/panes/graphics.fxml");
      } else if (actionEvent.getSource() == ticketOfficeButton) {
        boolean hasActiveShow = ShowService.getCurrentActiveShow() != null;
        
        if (!hasActiveShow) {
          AlertService.showWarning("Não há nenhum show ativo na bilheteria");
          return;
        }

        loadPane("/views/components/panes/ticketOffice.fxml");
      } else if (actionEvent.getSource() == usersButton) {
        loadPane("/views/components/panes/users.fxml");
      }
  }
    
    @FXML
    public void handleLogout(ActionEvent event) {
      LoginService.doLogout();
    }

    @FXML
    public void handleEditUser(ActionEvent event) {
      loadPane("/views/components/panes/userEditor.fxml");
    }

    private void loadDefaultPane() {
      loadPane("/views/components/panes/welcome.fxml");
    }

    private void loadPane(String paneSrc) {
      try {
          Parent pane = FXMLLoader.load(getClass().getResource(paneSrc));

          dashboardStackPane.getChildren().setAll(pane);
      } catch (Exception e) {
          ApplicationUtilities.getInstance().handleException(e);
      }
    }

    private void loadViewPermissions() {
      boolean canAccessArtistsPane = PermissionService.hasAccess(Operation.VIEW, Pane.ARTISTS);
      boolean canAccessOccupationsPane = PermissionService.hasAccess(Operation.VIEW, Pane.OCCUPATIONS);
      boolean canAccessAttractionsPane = PermissionService.hasAccess(Operation.VIEW, Pane.ATTRACTIONS);
      boolean canAccessTicketsPane = PermissionService.hasAccess(Operation.VIEW, Pane.TICKETS);
      boolean canAccessShowsPane = PermissionService.hasAccess(Operation.VIEW, Pane.SHOWS);
      boolean canAccessTicketOfficePane = PermissionService.hasAccess(Operation.VIEW, Pane.TICKET_OFFICE);
      boolean canAccessUsersPane = PermissionService.hasAccess(Operation.VIEW, Pane.USERS);
      boolean canAccessAdminPane = PermissionService.hasAccess(Operation.VIEW, Pane.ADMIN);
      boolean canAccessGraphicPane = PermissionService.hasAccess(Operation.VIEW, Pane.GRAPHICS);
      
      artistsButton.setDisable(!canAccessArtistsPane);
      occupationsButton.setDisable(!canAccessOccupationsPane);
      attractionsButton.setDisable(!canAccessAttractionsPane);
      ticketsButton.setDisable(!canAccessTicketsPane);
      showsButton.setDisable(!canAccessShowsPane);
      ticketOfficeButton.setDisable(!canAccessTicketOfficePane);
      usersButton.setDisable(!canAccessUsersPane);
      administrationButton.setDisable(!canAccessAdminPane);
      graphicsButton.setDisable(!canAccessGraphicPane);
    }
}
