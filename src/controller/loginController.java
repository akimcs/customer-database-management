package controller;

import database.DBuser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainApplication.Main;
import model.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**Handles the user login control, language changes, login activity recording of the Login Screen.*/
public class loginController implements Initializable {

    /**Label for location*/
    @FXML
    private Label locationLabel;
    /**Label for username*/
    @FXML
    private Label usernameLabel;
    /**Label for password*/
    @FXML
    private Label passwordLabel;
    /**TextField for location*/
    @FXML
    private Label locationText;
    /**TextField for username*/
    @FXML
    private TextField usernameText;
    /**TextField for password*/
    @FXML
    private TextField passwordText;
    /**Button to input credentials and log in*/
    @FXML
    private Button submitButton;

    /**Resource Bundle holding either en or fr language properties*/
    private ResourceBundle rb;

    /**Sets window title name, location, and correct language for stage.
     * @param url the URL object
     * @param resourceBundle the ResourceBundle object
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = ResourceBundle.getBundle("language/loginScreen");
        Main.getStage().setTitle(rb.getString("windowTitle"));
        locationText.setText(ZoneId.systemDefault().toString());        // https://www.javatpoint.com/java-zoneid

        if (Locale.getDefault().getLanguage().equals("fr")) {
            locationLabel.setText(rb.getString("locationLabel"));
            usernameLabel.setText(rb.getString("usernameLabel"));
            passwordLabel.setText(rb.getString("passwordLabel"));
            submitButton.setText(rb.getString("submitButton"));
        }
    }

    /**The submit button to add the data to the database.
     * Implements many error checks and input validation to ensure data integrity.
     * @throws IOException Scene change may cause error.
     * @throws SQLException Database Calls may cause error.
     * */
    @FXML
    void clickSubmitButton() throws IOException, SQLException {
        if (DBuser.isValidLogin(usernameText.getText(), passwordText.getText())) {
            recordLoginActivity("SUCCESS");
            User.setCurrentUserId(DBuser.getUserId(usernameText.getText()));
            User.setCurrentUserName(usernameText.getText());

            Stage stage = Main.getStage();
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/menu.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.centerOnScreen();
            stage.show();
            menuController controller = loader.getController();
            controller.checkForAppointment();
        }
        else {
            recordLoginActivity("FAIL   ");
            Main.dialogBox(Alert.AlertType.ERROR, rb.getString("alertTitle"), rb.getString("alertContent"));
        }
    }

    /** Records each login attempt in the local txt file.
     * @param loginStatus Either "Success" or "Fail"
     * @throws IOException Possible input/out errors.
     * */
    private void recordLoginActivity(String loginStatus) throws IOException {
        BufferedWriter bf = new BufferedWriter(new FileWriter("login_activity.txt", true));
        bf.write(LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " UTC - " + loginStatus + " USER(" + usernameText.getText() + ")");
        bf.newLine();
        bf.close();
    }
}
