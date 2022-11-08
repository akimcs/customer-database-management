package controller;

import database.DBappointment;
import database.DBcontact;
import database.DBcustomer;
import database.DBuser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private final ObservableList<String> allHours = FXCollections.observableArrayList();
    /**Holds strings of 0,15,30,45 minute increments in an hour*/
    private final ObservableList<String> allMinutes = FXCollections.observableArrayList();
    /**Time objet to hold start*/
    private LocalTime startTime;
    /**Time objet to hold end*/
    private LocalTime endTime;

    /**Sets window title name.
     * @param url the URL object
     * @param resourceBundle the ResourceBundle object
     * */
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

    /**Fills all combo boxes on the form with lists and fills the hours/minutes in the time combo boxes.
     * @throws SQLException Calls SQL Database Statements.
     * */
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

        contactCBText.getSelectionModel().select(0);
        customeridCBText.getSelectionModel().select(0);
        useridCBText.getSelectionModel().select(0);
        StartHrText.setValue("08");
        StartMinText.setValue("00");
        dateDPText.setValue(LocalDate.now());
    }

    /** Ensures that the inputted times are valid.
     * @return Boolean true if valid time.
     * */
    private boolean validStartEndTimes() {
        String startHr = StartHrText.getSelectionModel().getSelectedItem();
        String startMin = StartMinText.getSelectionModel().getSelectedItem();
        String endHr = EndHrText.getSelectionModel().getSelectedItem();
        String endMin = EndMinText.getSelectionModel().getSelectedItem();

        startTime = LocalTime.parse(startHr + ":" + startMin);
        endTime = LocalTime.parse(endHr + ":" + endMin);

        return startTime.isBefore(endTime);
    }

    /** Checks that the start/end times are within 8am-10pm EST.
     * @return Boolean true if time within businees hours.
     * */
    private boolean withinBusinessHours() {
        // Grab the zone id of local timezone and EST timezone.
        ZoneId localZoneId = ZoneId.systemDefault();
        ZoneId estZoneId = ZoneId.of("America/New_York");

        // Convert inputted date and time into local LocalDateTime object.
        LocalDateTime appStartLocal = LocalDateTime.of(dateDPText.getValue(), startTime);
        LocalDateTime appEndLocal = LocalDateTime.of(dateDPText.getValue(), endTime);

        // Convert the LocalDateTime format into a local ZonedDateTime object.
        ZonedDateTime appStartLocalZoned = appStartLocal.atZone(localZoneId);
        ZonedDateTime appEndLocalZoned = appEndLocal.atZone(localZoneId);

        // Convert the local ZonedDateTime object into EST ZonedDateTime object.
        ZonedDateTime appStartESTZoned = appStartLocalZoned.withZoneSameInstant(estZoneId);
        ZonedDateTime appEndESTZoned = appEndLocalZoned.withZoneSameInstant(estZoneId);

        // Convert the EST ZonedDateTime object into EST LocalDateTimeObject.
        LocalDateTime appStartEST = appStartESTZoned.toLocalDateTime();
        LocalDateTime appEndEST = appEndESTZoned.toLocalDateTime();

        // Convert EST LocalDateTimeObject into EST LocalTime object.
        LocalTime estStart = appStartEST.toLocalTime();
        LocalTime estEnd = appEndEST.toLocalTime();

        // Evaluate the new EST time to 8am - 10pm EST timeframe.
        LocalTime estOpen = LocalTime.parse("08:00");
        LocalTime estClose = LocalTime.parse("22:00");
        return ((estStart.equals(estOpen) || estStart.isAfter(estOpen)) && (estStart.isBefore(estClose))) && (estEnd.equals(estClose) || estEnd.isBefore(estClose) && (estEnd.isAfter(estOpen)));
    }

    /**Checks if suggested appointment time overlaps an existing appointment.
     * @return Boolean true if there is an overlap.
     * @throws SQLException Calls SQL Database Statements.
     * */
    private boolean timeConflict() throws SQLException {
        return DBappointment.conflictExists(customeridCBText.getSelectionModel().getSelectedItem().getId(), Integer.parseInt(appointmentidText.getText()), LocalDateTime.of(dateDPText.getValue(), startTime), LocalDateTime.of(dateDPText.getValue(), endTime));
    }

    /** Uses a Lambda Expression to detect empty fields in form.
     * The lambda expressions are used to check if fields are empty or null. They provide great use in shrinking the code.
     * Without them, the method requires repeated clutter of checking Strings, Integers, etc. Before they were implemented, the return statement was unreadable and cluttered with many parentheses.
     * @return Boolean true if empty/null field is detected.
     * */
    private boolean emptyFieldDetected() {
        // LAMBDA
        CheckTextEmpty text = s -> s.getText().trim().isEmpty();
        CheckComboNull combo = t -> t.getSelectionModel().getSelectedItem()==null;
        CheckDateNull date = x -> x.getValue()==null;

        return text.isE(titleText) || text.isE(descriptionText) || text.isE(locationText) || text.isE(typeText) || combo.isN(contactCBText) ||
                combo.isN(StartHrText) || combo.isN(StartMinText) || combo.isN(EndHrText) || combo.isN(EndMinText) || combo.isN(customeridCBText) ||
                combo.isN(useridCBText) || date.isN(dateDPText);
    }

    /**Uses a Lambda Expression to detect if there is any text in the form.
     * Before the lambda expression was implemented, the method utilized repeated code to check each field, which created a big block of shirnkable code.
     * The lambda expressions are justified in that they allow the user to read the fields while still understanding that the lambda checks for null/empty.
     * @return Boolean true if text/selected values are detected in the form.
     * */
    private boolean textDetected() {
        // LAMBDA
        CheckTextEmpty text = s -> s.getText().trim().isEmpty();
        CheckComboNull combo = t -> t.getSelectionModel().getSelectedItem()==null;
        CheckDateNull date = x -> x.getValue()==null;
        
        return !(text.isE(titleText) && text.isE(descriptionText) && text.isE(locationText) && text.isE(typeText) && combo.isN(contactCBText) &&
                combo.isN(StartHrText) && combo.isN(StartMinText) && combo.isN(EndHrText) && combo.isN(EndMinText) && combo.isN(customeridCBText) &&
                combo.isN(useridCBText) && date.isN(dateDPText));
    }

    /** The submit button to add the data to the database.
     * Implements many error checks and input validation to ensure data integrity.
     * @throws SQLException Calls SQL Database Statements.
     * */
    @FXML
    void clickSubmitButton() throws SQLException {
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

    /** Cancels the add/mod form and goes back to menu without saving.
     * @throws IOException Possible input/out errors.
     * */
    @FXML
    void clickCancelButton() throws IOException {
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
