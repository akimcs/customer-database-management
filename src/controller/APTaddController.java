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

    private LocalTime startTime;
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

    private boolean emptyFieldDetected() {
        return titleText.getText().trim().isEmpty() || descriptionText.getText().trim().isEmpty() ||
                locationText.getText().trim().isEmpty() || contactCBText.getSelectionModel().getSelectedItem()==null ||
                typeText.getText().trim().isEmpty() || dateDPText.getValue() == null || StartHrText.getSelectionModel().getSelectedItem()==null ||
                StartMinText.getSelectionModel().getSelectedItem()==null || EndHrText.getSelectionModel().getSelectedItem()==null ||
                EndMinText.getSelectionModel().getSelectedItem()==null || customeridCBText.getSelectionModel().getSelectedItem()==null ||
                useridCBText.getSelectionModel().getSelectedItem()==null;
    }
    
    private boolean textDetected() {
        return !(titleText.getText().trim().isEmpty() && descriptionText.getText().trim().isEmpty() &&
                locationText.getText().trim().isEmpty() && contactCBText.getSelectionModel().getSelectedItem()==null &&
                typeText.getText().trim().isEmpty() && dateDPText.getValue() == null &&
                StartHrText.getSelectionModel().getSelectedItem()==null && StartMinText.getSelectionModel().getSelectedItem()==null &&
                EndHrText.getSelectionModel().getSelectedItem()==null && EndMinText.getSelectionModel().getSelectedItem()==null &&
                customeridCBText.getSelectionModel().getSelectedItem()==null && useridCBText.getSelectionModel().getSelectedItem()==null);
    }

    @FXML
    void clickSubmitButton(ActionEvent event) throws SQLException, IOException {
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
