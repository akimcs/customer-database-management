package controller;

import database.DBuser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

public class loginController implements Initializable {

    @FXML
    private Label locationLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label locationText;
    @FXML
    private TextField usernameText;
    @FXML
    private TextField passwordText;
    @FXML
    private Button submitButton;

    private ResourceBundle rb;

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
        // TODO - autofill for speed, remove later
        usernameText.setText("test");
        passwordText.setText("test");
    }

    @FXML
    void clickSubmitButton(ActionEvent event) throws IOException, SQLException {
        if (DBuser.isValidLogin(usernameText.getText(), passwordText.getText())) {
            recordLoginActivity("SUCCESS");
            User.setCurrentUserId(DBuser.getUserId(usernameText.getText()));
            User.setCurrentUserName(usernameText.getText());
            Main.changeScene("/view/menu.fxml");
        }
        else {
            recordLoginActivity("FAIL   ");
            Main.dialogBox(Alert.AlertType.ERROR, rb.getString("alertTitle"), rb.getString("alertContent"));
        }
    }

    private void recordLoginActivity(String loginStatus) throws IOException {
        BufferedWriter bf = new BufferedWriter(new FileWriter("login_activity.txt", true));
        bf.write(LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " UTC - " + loginStatus + " USER(" + usernameText.getText() + ")");
        bf.newLine();
        bf.close();
    }
}
