import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("teste.fxml"));
        Parent root = loader.load();
        Scene tela = new Scene(root);

        primaryStage.setTitle("teste dus guri");
        primaryStage.setScene(tela);
        primaryStage.show();
    }
}
