package services;

import org.xml.sax.helpers.DefaultHandler;

import entities.Operation;
import entities.Pane;
import entities.Role;
import entities.User;
import utils.ApplicationUtilities;
import utils.PermissionXMLReader;

public class PermissionService extends DefaultHandler {
  public static boolean hasAccess(Operation operation, Pane pane) {
    User activeUser = ApplicationUtilities.getInstance().getActiveUser();
    Role role = activeUser.getRole();
    
    PermissionXMLReader permissionReader = new PermissionXMLReader(role, operation, pane);
    permissionReader.parse();
    
    return permissionReader.canAccess();
  }
}
