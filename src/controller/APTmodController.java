package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mainApplication.Main;
import model.Contact;
import model.Customer;
import model.User;
import java.net.URL;
import java.util.ResourceBundle;

public class APTmodController implements Initializable {

    // TODO - LEAVE ALONE UNTIL ADD SCREEN FINISHED

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
    private ComboBox<String> typeCBText;
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
        Main.getStage().setTitle("Modify Appointment");
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
