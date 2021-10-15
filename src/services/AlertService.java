package services;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlertService {
  public static void showWarning(String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Alerta");
    ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/res/images/alert.png"));

    TextArea area = new TextArea(message);
    area.setWrapText(true);
    area.setEditable(false);

    alert.getDialogPane().setContent(area);
    alert.setResizable(true);

    alert.showAndWait();
  }

  public static void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Erro");
    ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/res/images/error.png"));

    TextArea area = new TextArea(message);
    area.setWrapText(true);
    area.setEditable(false);

    alert.getDialogPane().setContent(area);
    alert.setResizable(true);

    alert.showAndWait();
  }

  public static boolean showConfirmation(String message) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmação");
    ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/res/images/confirm.png"));

    TextArea area = new TextArea(message);
    area.setWrapText(true);
    area.setEditable(false);

    alert.getDialogPane().setContent(area);
    alert.setResizable(true);

    Optional<ButtonType> result = alert.showAndWait();

    return result.get() == ButtonType.OK;
  }
}
