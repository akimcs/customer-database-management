package mainApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**Creates the scene. Holds reusable scene changes and dialog box methods.*/
public class Main extends Application {

    /**Stage object that holds the application window.*/
    static Stage primaryStage;

    /**Loads the primaryStage for the window to show.
     * @param primaryStage the stage/window used as the GUI.
     * @throws IOException In case of improper scene loading.*/
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Locale.setDefault(new Locale("fr"));
        Main.primaryStage = primaryStage;
        primaryStage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login.fxml")))));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /**Launches the application using argument args.
     * @param args the arguments for main.*/
    public static void main(String[] args) {
        launch(args);
    }

    /**Gets the stage.
     * @return the stage.*/
    public static Stage getStage() {
        return primaryStage;
    }

    /**Standardized scene change method for reuse by program.
     * @param filepath The path of the FXML file to switch to.
     * @return FXMLLoader for obtaining controllers during data transfer to next scene.
     * @throws IOException For improper scene changes.*/
    public static FXMLLoader changeScene(String filepath) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(filepath));
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.centerOnScreen();
        primaryStage.show();
        return loader;      // Returns loader for when data needs to be passed between scenes.
    }

    /**Standardized dialog box method for reuse by program.
     * @param messageType Accepts CONFIRMATION,WARNING,INFO,INFORMATION types.
     * @param title The title of the dialog box.
     * @param content The message inside the dialog box.
     * @return The dialog box to check for button press in confirmation screen.*/
    public static Optional<ButtonType> dialogBox(Alert.AlertType messageType, String title, String content) {
        // messageType = CONFIRMATION || WARNING || ERROR || INFORMATION
        Alert dialogBox = new Alert(messageType);
        dialogBox.setTitle(title);
        dialogBox.setContentText(content);
        return dialogBox.showAndWait();     // Returns dialogBox for checking user response to confirmation screen.
    }
}
