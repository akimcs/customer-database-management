package controller;

import database.DBappointment;
import database.DBcontact;
import database.DBcustomer;
import database.DBuser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import mainApplication.Main;
import javafx.fxml.FXML;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
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

    private ObservableList<String> allHours = FXCollections.observableArrayList();
    private ObservableList<String> allMinutes = FXCollections.observableArrayList();

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
        customeridCBText.setItems(DBcustomer.getAllCustomers());
        useridCBText.setItems(DBuser.getAllUsers());

        allHours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        allMinutes.addAll("00", "15", "30", "45");
        StartHrText.setItems(allHours);
        EndHrText.setItems(allHours);
        StartMinText.setItems(allMinutes);
        EndMinText.setItems(allMinutes);
    }

    private boolean validStartEndTimes() {
        int startHr = Integer.parseInt(StartHrText.getSelectionModel().getSelectedItem());
        int startMin = Integer.parseInt(StartMinText.getSelectionModel().getSelectedItem());
        int endHr = Integer.parseInt(EndHrText.getSelectionModel().getSelectedItem());
        int endMin = Integer.parseInt(EndMinText.getSelectionModel().getSelectedItem());

        if (!(endHr < startHr) ||
                ((StartHrText.getSelectionModel().getSelectedItem().equals(EndHrText.getSelectionModel().getSelectedItem())) && endMin <= startMin)) {
            return true;
        }
        return false;
    }

    private boolean emptyFieldDetected() {
        return appointmentidText.getText().trim().isEmpty() || titleText.getText().trim().isEmpty() ||
                descriptionText.getText().trim().isEmpty() || locationText.getText().trim().isEmpty() ||
                contactCBText.getSelectionModel().isEmpty() || typeText.getText().trim().isEmpty() ||
                dateDPText.getValue() == null || StartHrText.getSelectionModel().isEmpty() ||
                StartMinText.getSelectionModel().isEmpty() || EndHrText.getSelectionModel().isEmpty() ||
                EndMinText.getSelectionModel().isEmpty() || customeridCBText.getSelectionModel().isEmpty() ||
                useridCBText.getSelectionModel().isEmpty();
    }

    @FXML
    void clickSubmitButton(ActionEvent event) {
        if (emptyFieldDetected()) {
            Main.dialogBox(Alert.AlertType.ERROR, "Empty Field Detected", "Make Sure All Fields Are Filled Out.");
        }
        else if (!validStartEndTimes()) {
            Main.dialogBox(Alert.AlertType.ERROR, "Improper Start and End Times", "Make sure End Time is after Start Time.");
        }
        else {
            try {
                int id = Integer.parseInt(appointmentidText.getText());
                String title = titleText.getText();
                String description = descriptionText.getText();
                String location = locationText.getText();
                String type = typeText.getText();

                String startHr = StartHrText.getSelectionModel().getSelectedItem();
                String startMin = StartMinText.getSelectionModel().getSelectedItem();
                String endHr = EndHrText.getSelectionModel().getSelectedItem();
                String endMin = EndMinText.getSelectionModel().getSelectedItem();
                LocalTime startTime = LocalTime.parse(startHr + ":" + startMin, DateTimeFormatter.ofPattern("HH:mm"));
                LocalTime endTime = LocalTime.parse(endHr + ":" + endMin, DateTimeFormatter.ofPattern("HH:mm"));
                LocalDate date = dateDPText.getValue();
                LocalDateTime start = LocalDateTime.of(date, startTime);
                LocalDateTime end = LocalDateTime.of(date, endTime);

                int customerId = customeridCBText.getSelectionModel().getSelectedItem().getId();
                int userId = useridCBText.getSelectionModel().getSelectedItem().getId();
                int contactId = contactCBText.getSelectionModel().getSelectedItem().getId();

                DBappointment.addAppointment(new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId));
                Main.changeScene("/view/APTmenu.fxml");
            }
            catch (Exception e) {
                Main.dialogBox(Alert.AlertType.ERROR, "Improper Input Detected", "Ensure All Fields Are Correctly Formatted.");
            }
        }
    }

    @FXML
    void clickCancelButton(ActionEvent event) throws IOException {
        if (!emptyFieldDetected()) {
            Optional<ButtonType> confirmationScreen = Main.dialogBox(Alert.AlertType.CONFIRMATION, "Changes Detected in Form", "This action will delete all changes, continue?");
            if (confirmationScreen.isPresent() && confirmationScreen.get() == ButtonType.OK) {
                Main.changeScene("/view/APTmenu.fxml");
            }
        }
        else {
            Main.changeScene("/view/APTmenu.fxml");
        }
    }
}
