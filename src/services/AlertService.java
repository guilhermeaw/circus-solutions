package services;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public class AlertService {
  public static void showWarning(String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Alerta");

    TextArea area = new TextArea(message);
    area.setWrapText(true);
    area.setEditable(false);

    alert.getDialogPane().setContent(area);
    alert.setResizable(true);

    alert.showAndWait();
}
}
