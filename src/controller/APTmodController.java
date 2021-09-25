package controller;

import database.DBappointment;
import database.DBcontact;
import database.DBcustomer;
import database.DBuser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import mainApplication.Main;
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

public class APTmodController implements Initializable {

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

    private Appointment originalAppointment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Modify Appointment");
    }

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
                contactCBText.getSelectionModel().getSelectedItem()==null || typeText.getText().trim().isEmpty() ||
                dateDPText.getValue() == null || StartHrText.getSelectionModel().getSelectedItem()==null ||
                StartMinText.getSelectionModel().getSelectedItem()==null || EndHrText.getSelectionModel().getSelectedItem()==null ||
                EndMinText.getSelectionModel().getSelectedItem()==null || customeridCBText.getSelectionModel().getSelectedItem()==null ||
                useridCBText.getSelectionModel().getSelectedItem()==null;
    }

    private boolean noChanges() {
        return originalAppointment.getId() == Integer.parseInt(appointmentidText.getText()) &&
                originalAppointment.getTitle().equals(titleText.getText()) &&
                originalAppointment.getDescription().equals(descriptionText.getText()) &&
                originalAppointment.getLocation().equals(locationText.getText()) &&
                originalAppointment.getContactId() == contactCBText.getSelectionModel().getSelectedItem().getId() &&
                originalAppointment.getType().equals(typeText.getText()) &&
                originalAppointment.getStart().toLocalDate().isEqual(dateDPText.getValue()) &&
                originalAppointment.getStart().format(DateTimeFormatter.ofPattern("HH")).equals(StartHrText.getSelectionModel().getSelectedItem()) &&
                originalAppointment.getStart().format(DateTimeFormatter.ofPattern("HH")).equals(StartHrText.getSelectionModel().getSelectedItem()) &&
                originalAppointment.getEnd().format(DateTimeFormatter.ofPattern("mm")).equals(EndHrText.getSelectionModel().getSelectedItem()) &&
                originalAppointment.getEnd().format(DateTimeFormatter.ofPattern("mm")).equals(EndMinText.getSelectionModel().getSelectedItem()) &&
                originalAppointment.getCustomerId() == customeridCBText.getSelectionModel().getSelectedItem().getId() &&
                originalAppointment.getUserId() == useridCBText.getSelectionModel().getSelectedItem().getId();
    }

    @FXML
    void clickSubmitButton(ActionEvent event) throws IOException {
        if (noChanges()) {
            Optional<ButtonType> confirmationScreen = Main.dialogBox(Alert.AlertType.CONFIRMATION, "No Changes Detected in Form", "This action will not update any records. Continue?");
            if (confirmationScreen.isPresent() && confirmationScreen.get() == ButtonType.OK) {
                Main.changeScene("/view/APTmenu.fxml");
            }
        }
        else if (emptyFieldDetected()) {
            Main.dialogBox(Alert.AlertType.ERROR, "Empty Field Detected", "Make Sure All Fields Are Filled Out.");
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

    @FXML
    void clickCancelButton(ActionEvent event) throws IOException {
        if (noChanges()) {
            Main.changeScene("/view/APTmenu.fxml");
        }
        else {
            Optional<ButtonType> confirmationScreen = Main.dialogBox(Alert.AlertType.CONFIRMATION, "No Changes Detected in Form", "Changes will not be saved. Continue?");
            if (confirmationScreen.isPresent() && confirmationScreen.get() == ButtonType.OK) {
                Main.changeScene("/view/APTmenu.fxml");
            }
        }
    }
}
