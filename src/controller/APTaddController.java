package controller;

import database.DBappointment;
import database.DBcontact;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import mainApplication.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Contact;
import model.Customer;
import model.User;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class APTaddController implements Initializable {

    @FXML
    private Label appointmentidText;
    @FXML
    private TextField titleText;
    @FXML
    private TextField descriptionText;
    @FXML
    private TextField locationText;
    @FXML
    private ComboBox<Contact> contactCBText;
    @FXML
    private TextField typeText;
    @FXML
    private DatePicker dateDPText;
    @FXML
    private ComboBox<String> StartHrText;
    @FXML
    private ComboBox<String> StartMinText;
    @FXML
    private ComboBox<String> EndHrText;
    @FXML
    private ComboBox<String> EndMinText;
    @FXML
    private ComboBox<Customer> customeridCBText;
    @FXML
    private ComboBox<User> useridCBText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Add Appointment");
        try {
            populateScreen();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void populateScreen() throws SQLException {
        appointmentidText.setText(String.valueOf(DBappointment.nextAppointmentId()));
        contactCBText.setItems(DBcontact.getAllContacts());
    }

    @FXML
    void selectedStartHour(ActionEvent event) {

    }

    @FXML
    void selectedStartMin(ActionEvent event) {

    }

    @FXML
    void clickCancelButton(ActionEvent event) {

    }

    @FXML
    void clickSubmitButton(ActionEvent event) {

    }
}
