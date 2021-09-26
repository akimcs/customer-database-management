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
import lambda.CheckTextEmpty;
import lambda.CheckComboNull;
import lambda.CheckDateNull;
import mainApplication.Main;
import javafx.fxml.FXML;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

/**Handles the input validation, buttons, and display of the Add Appointment Form.*/
public class APTaddController implements Initializable {

    /**Displays the next available appointment id*/
    @FXML
    private Label appointmentidText;
    /**Field to input title*/
    @FXML
    private TextField titleText;
    /**Field to input description*/
    @FXML
    private TextField descriptionText;
    /**Field to input location*/
    @FXML
    private TextField locationText;
    /**Combo Box to select contact*/
    @FXML
    private ComboBox<Contact> contactCBText;
    /**Field to input type*/
    @FXML
    private TextField typeText;
    /**Field to input date*/
    @FXML
    private DatePicker dateDPText;
    /**Combo Box to select start hour*/
    @FXML
    private ComboBox<String> StartHrText;
    /**Combo Box to select start minute*/
    @FXML
    private ComboBox<String> StartMinText;
    /**Combo Box to select end hour*/
    @FXML
    private ComboBox<String> EndHrText;
    /**Combo Box to select end minute*/
    @FXML
    private ComboBox<String> EndMinText;
    /**Combo Box to select customer*/
    @FXML
    private ComboBox<Customer> customeridCBText;
    /**Combo Box to select user*/
    @FXML
    private ComboBox<User> useridCBText;

    /**Holds strings of all hours in a day*/
    private ObservableList<String> allHours = FXCollections.observableArrayList();
    /**Holds strings of 0,15,30,45 minute increments in an hour*/
    private ObservableList<String> allMinutes = FXCollections.observableArrayList();

    /**Time objet to hold start*/
    private LocalTime startTime;
    /**Time objet to hold end*/
    private LocalTime endTime;

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
        String startHr = StartHrText.getSelectionModel().getSelectedItem();
        String startMin = StartMinText.getSelectionModel().getSelectedItem();
        String endHr = EndHrText.getSelectionModel().getSelectedItem();
        String endMin = EndMinText.getSelectionModel().getSelectedItem();

        startTime = LocalTime.parse(startHr + ":" + startMin);
        endTime = LocalTime.parse(endHr + ":" + endMin);

        return startTime.isBefore(endTime);
    }

    private boolean withinBusinessHours() {
        ZoneId localZoneId = ZoneId.systemDefault();
        ZoneId estZoneId = ZoneId.of("America/New_York");

        LocalDateTime appStartLocal = LocalDateTime.of(dateDPText.getValue(), startTime);
        LocalDateTime appEndLocal = LocalDateTime.of(dateDPText.getValue(), endTime);

        ZonedDateTime appStartLocalZoned = appStartLocal.atZone(localZoneId);
        ZonedDateTime appEndLocalZoned = appEndLocal.atZone(localZoneId);

        ZonedDateTime appStartESTZoned = appStartLocalZoned.withZoneSameInstant(estZoneId);
        ZonedDateTime appEndESTZoned = appEndLocalZoned.withZoneSameInstant(estZoneId);

        LocalDateTime appStartEST = appStartESTZoned.toLocalDateTime();
        LocalDateTime appEndEST = appEndESTZoned.toLocalDateTime();

        LocalTime estStart = appStartEST.toLocalTime();
        LocalTime estEnd = appEndEST.toLocalTime();

        LocalTime estOpen = LocalTime.parse("08:00");
        LocalTime estClose = LocalTime.parse("22:00");

        return ((estStart.equals(estOpen) || estStart.isAfter(estOpen)) && (estStart.isBefore(estClose))) && (estEnd.equals(estClose) || estEnd.isBefore(estClose) && (estEnd.isAfter(estOpen)));
    }

    private boolean timeConflict() throws SQLException {
        return DBappointment.conflictExists(customeridCBText.getSelectionModel().getSelectedItem().getId(), Integer.parseInt(appointmentidText.getText()), LocalDateTime.of(dateDPText.getValue(), startTime), LocalDateTime.of(dateDPText.getValue(), endTime));
    }

    private boolean emptyFieldDetected() {
        // LAMBDA
        CheckTextEmpty text = s -> s.getText().trim().isEmpty();
        CheckComboNull combo = t -> t.getSelectionModel().getSelectedItem()==null;
        CheckDateNull date = x -> x.getValue()==null;

        return text.isE(titleText) || text.isE(descriptionText) || text.isE(locationText) || text.isE(typeText) || combo.isN(contactCBText) ||
                combo.isN(StartHrText) || combo.isN(StartMinText) || combo.isN(EndHrText) || combo.isN(EndMinText) || combo.isN(customeridCBText) ||
                combo.isN(useridCBText) || date.isN(dateDPText);
    }

    private boolean textDetected() {
        // LAMBDA
        CheckTextEmpty text = s -> s.getText().trim().isEmpty();
        CheckComboNull combo = t -> t.getSelectionModel().getSelectedItem()==null;
        CheckDateNull date = x -> x.getValue()==null;
        
        return !(text.isE(titleText) && text.isE(descriptionText) && text.isE(locationText) && text.isE(typeText) && combo.isN(contactCBText) &&
                combo.isN(StartHrText) && combo.isN(StartMinText) && combo.isN(EndHrText) && combo.isN(EndMinText) && combo.isN(customeridCBText) &&
                combo.isN(useridCBText) && date.isN(dateDPText));
    }

    @FXML
    void clickSubmitButton(ActionEvent event) throws SQLException, IOException {
        if (emptyFieldDetected()) {
            Main.dialogBox(Alert.AlertType.ERROR, "Empty Field Detected", "Make Sure All Fields Are Filled Out.");
        }
        else if (!validStartEndTimes()) {
            Main.dialogBox(Alert.AlertType.ERROR, "Improper Start and End Times", "Make sure End Time is after Start Time.");
        }
        else if (!withinBusinessHours()) {
            Main.dialogBox(Alert.AlertType.ERROR, "Appointment Time is Outside Business Hours", "Business Hours (EST): 8:00 AM - 10:00 PM, 7 Days a Week.");
        }
        else if (timeConflict()) {
            Main.dialogBox(Alert.AlertType.ERROR, "Conflicting Appointment Detected", "The requested start/end date and time overlap with an existing appointment.");
        }
        else {
            try {
                int id = Integer.parseInt(appointmentidText.getText());
                String title = titleText.getText();
                String description = descriptionText.getText();
                String location = locationText.getText();
                String type = typeText.getText();
                LocalDateTime start = LocalDateTime.of(dateDPText.getValue(), startTime);
                LocalDateTime end = LocalDateTime.of(dateDPText.getValue(), endTime);
                int customerId = customeridCBText.getSelectionModel().getSelectedItem().getId();
                int userId = useridCBText.getSelectionModel().getSelectedItem().getId();
                int contactId = contactCBText.getSelectionModel().getSelectedItem().getId();

                if (DBappointment.addAppointment(new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId)) == 1) {
                    Main.dialogBox(Alert.AlertType.INFORMATION, "Appointment Successfully Added", "New appointment has been added.");
                    Main.changeScene("/view/APTmenu.fxml");
                }
                else {
                    Main.dialogBox(Alert.AlertType.ERROR, "Appointment Not Added", "Appointment was unable to be added.");
                }
            }
            catch (Exception e) {
                Main.dialogBox(Alert.AlertType.ERROR, "Improper Input Detected", "Ensure All Fields Are Correctly Formatted.");
            }
        }
    }

    @FXML
    void clickCancelButton(ActionEvent event) throws IOException {
        if (textDetected()) {
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
