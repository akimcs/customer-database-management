package controller;

import database.DBappointment;
import database.DBcontact;
import database.DBcustomer;
import database.DBuser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lambda.*;
import mainApplication.Main;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/**Handles the input validation, buttons, and display of the Modify Appointment Form.*/
public class APTmodController implements Initializable {

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

    /**Holds the original appointment being modified*/
    private Appointment originalAppointment;

    /**Sets window title name.
     * @param url the URL object
     * @param resourceBundle the ResourceBundle object
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Modify Appointment");
    }

    /** Fills in all original appointment information to be modified.
     * @param originalAppointment The original appointment to be changed.
     * @throws SQLException Calls SQL Database Statements.
     * */
    public void displayAppointment(Appointment originalAppointment) throws SQLException {
        this.originalAppointment = originalAppointment;

        allHours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        allMinutes.addAll("00", "15", "30", "45");

        contactCBText.setItems(DBcontact.getAllContacts());
        StartHrText.setItems(allHours);
        StartMinText.setItems(allMinutes);
        EndHrText.setItems(allHours);
        EndMinText.setItems(allMinutes);
        customeridCBText.setItems(DBcustomer.getAllCustomers());
        useridCBText.setItems(DBuser.getAllUsers());

        appointmentidText.setText(String.valueOf(originalAppointment.getId()));
        titleText.setText(originalAppointment.getTitle());
        descriptionText.setText(originalAppointment.getDescription());
        locationText.setText(originalAppointment.getLocation());
        contactCBText.getSelectionModel().select(DBcontact.getContact(originalAppointment.getContactId()));
        typeText.setText(originalAppointment.getType());
        dateDPText.setValue(originalAppointment.getStart().toLocalDate());
        StartHrText.getSelectionModel().select(originalAppointment.getStart().format(DateTimeFormatter.ofPattern("HH")));
        StartMinText.getSelectionModel().select(originalAppointment.getStart().format(DateTimeFormatter.ofPattern("mm")));
        EndHrText.getSelectionModel().select(originalAppointment.getEnd().format(DateTimeFormatter.ofPattern("HH")));
        EndMinText.getSelectionModel().select(originalAppointment.getEnd().format(DateTimeFormatter.ofPattern("mm")));
        customeridCBText.getSelectionModel().select(DBcustomer.getCustomer(originalAppointment.getCustomerId()));
        useridCBText.getSelectionModel().select(DBuser.getUser(originalAppointment.getUserId()));
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

    /**Checks if suggested appointment time overlaps an existing appointment.
     * @return Boolean true if there is an overlap.
     * @throws SQLException Calls SQL Database Statements.
     * */
    private boolean timeConflict() throws SQLException {
        if (originalAppointment.getStart().toLocalDate().isEqual(dateDPText.getValue()) &&
                originalAppointment.getStart().getHour()==Integer.parseInt(StartHrText.getSelectionModel().getSelectedItem()) &&
                originalAppointment.getStart().getMinute()==Integer.parseInt(StartMinText.getSelectionModel().getSelectedItem()) &&
                originalAppointment.getEnd().getHour()==Integer.parseInt(EndHrText.getSelectionModel().getSelectedItem()) &&
                originalAppointment.getEnd().getMinute()==Integer.parseInt(EndMinText.getSelectionModel().getSelectedItem())) {
            return false;
        }
        else return DBappointment.conflictExists(customeridCBText.getSelectionModel().getSelectedItem().getId(), Integer.parseInt(appointmentidText.getText()), LocalDateTime.of(dateDPText.getValue(), startTime), LocalDateTime.of(dateDPText.getValue(), endTime));
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

    /**Uses a Lambda Expression to detect if changes were made in the form.
     * The lambda expressions used in this form were critical for readability and shortening of code.
     * Before, the method utilized too many repeating segments and cluttered the method, making it difficult to understand.
     * With the lambda expression, the code becomes readable and better.
     * @return Boolean true if original appointment remained unchanged.
     * */
    private boolean noChanges() {
        // LAMBDA
        VerifyEqualString text = (q, r) -> q.equals(r);
        VerifyEqualInteger combo = (s, t) -> s==t;
        VerifyEqualDate date = (u, v) -> u.equals(v);
        GetStr str = a -> a.getText();

        return  text.eq(originalAppointment.getTitle(),                 str.gt(titleText)) &&
                text.eq(originalAppointment.getDescription(),           str.gt(descriptionText)) &&
                text.eq(originalAppointment.getLocation(),              str.gt(locationText)) &&
                text.eq(originalAppointment.getType(),                  str.gt(typeText)) &&
                combo.eq(originalAppointment.getContactId(),            contactCBText.getSelectionModel().getSelectedItem().getId()) &&
                combo.eq(originalAppointment.getCustomerId(),           customeridCBText.getSelectionModel().getSelectedItem().getId()) &&
                combo.eq(originalAppointment.getUserId(),               useridCBText.getSelectionModel().getSelectedItem().getId()) &&
                combo.eq(originalAppointment.getStart().getHour(),      Integer.parseInt(StartHrText.getSelectionModel().getSelectedItem())) &&
                combo.eq(originalAppointment.getStart().getMinute(),    Integer.parseInt(StartMinText.getSelectionModel().getSelectedItem())) &&
                combo.eq(originalAppointment.getEnd().getHour(),        Integer.parseInt(EndHrText.getSelectionModel().getSelectedItem())) &&
                combo.eq(originalAppointment.getEnd().getMinute(),      Integer.parseInt(EndMinText.getSelectionModel().getSelectedItem())) &&
                date.eq(originalAppointment.getStart().toLocalDate(),   dateDPText.getValue());
    }

    /**The submit button to add the data to the database.
     * Implements many error checks and input validation to ensure data integrity.
     * @throws IOException Scene change may cause error.
     * @throws SQLException Database Calls may cause error.
     * */
    @FXML
    void clickSubmitButton() throws IOException, SQLException {
        if (noChanges()) {
            Optional<ButtonType> confirmationScreen = Main.dialogBox(Alert.AlertType.CONFIRMATION, "No Changes Detected in Form", "No Changes Detected. Appointment Will Not Be Modified.");
            if (confirmationScreen.isPresent() && confirmationScreen.get() == ButtonType.OK) {
                Main.changeScene("/view/APTmenu.fxml");
            }
        }
        else if (emptyFieldDetected()) {
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

                if (DBappointment.modifyAppointment(new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId)) == 1) {
                    Main.dialogBox(Alert.AlertType.INFORMATION, "Appointment Successfully Modified", "Appointment has been added.");
                    Main.changeScene("/view/APTmenu.fxml");
                }
                else {
                    Main.dialogBox(Alert.AlertType.ERROR, "Appointment Not Modified", "Appointment was unable to be modified.");
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
        if (noChanges()) {
            Main.changeScene("/view/APTmenu.fxml");
        }
        else {
            Optional<ButtonType> confirmationScreen = Main.dialogBox(Alert.AlertType.CONFIRMATION, "Changes Detected in Form", "Changes will not be saved. Continue?");
            if (confirmationScreen.isPresent() && confirmationScreen.get() == ButtonType.OK) {
                Main.changeScene("/view/APTmenu.fxml");
            }
        }
    }
}
