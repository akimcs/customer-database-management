package mainApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public class Main extends Application {

    static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        //Locale.setDefault(new Locale("fr"));
        Main.primaryStage = primaryStage;
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login.fxml"))));
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public static Stage getStage() {
        return primaryStage;
    }

    public static FXMLLoader changeScene(String filepath) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(filepath));
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
        return loader;      // Returns loader for when data needs to be passed between scenes.
    }

    public static Optional<ButtonType> dialogBox(Alert.AlertType messageType, String title, String content) {
        // messageType = CONFIRMATION || WARNING || ERROR || INFORMATION
        Alert dialogBox = new Alert(messageType);
        dialogBox.setTitle(title);
        dialogBox.setContentText(content);
        return dialogBox.showAndWait();     // Returns dialogBox for checking user response to confirmation screen.
    }
}
