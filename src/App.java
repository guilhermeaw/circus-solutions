import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String location = "views/login.fxml";
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Parent root = loader.load();
        Scene tela = new Scene(root);

        Font.loadFont(getClass().getResourceAsStream("res/cuprum.ttf"), 14);

        primaryStage.setTitle("teste dus guri");
        primaryStage.setScene(tela);
        primaryStage.show();
    }
}
