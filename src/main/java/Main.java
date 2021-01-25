import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.screens.ApplicationScreen;

public class Main extends Application {

    private static ApplicationScreen scene;

    public static void main(String[] args) {
        // TODO: new DictionaryHandler();
        // TODO: Get game time from settings file
        // TODO: Get if should ignore time limit from game file
        // TODO: Get if should ignore incorrect
        launch(args);
    }

    // TODO: Get SQL Connection
    

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

}
