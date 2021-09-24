package controller;

import database.DBappointment;
import database.DBcontact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mainApplication.Main;
import model.Appointment;
import model.Contact;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;

public class menuController implements Initializable {

    @FXML
    private Label upcomingappointmentText;
    @FXML
    private ComboBox<Month> monthACBText;
    @FXML
    private ComboBox<String> typeACBText;
    @FXML
    private Label totalAText;
    @FXML
    private ComboBox<Contact> contactBCBText;
    @FXML
    private TableView<Appointment> scheduleBTableview;
    @FXML
    private TableColumn<Appointment, Integer> appidBTable;
    @FXML
    private TableColumn<Appointment, String> titleBTable;
    @FXML
    private TableColumn<Appointment, String> typeBTable;
    @FXML
    private TableColumn<Appointment, String> descriptionBTable;
    @FXML
    private TableColumn<Appointment, LocalDateTime> startBTable;
    @FXML
    private TableColumn<Appointment, LocalDateTime> endBTable;
    @FXML
    private TableColumn<Appointment, Integer> customeridBTable;
    @FXML
    private ComboBox<String> customIdType;
    @FXML
    private ComboBox<Integer> customIdNumber;
    @FXML
    private Label customAnswerText;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Menu");
        try {
            populateScreen();
            checkForAppointment();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void checkForAppointment() throws SQLException {
        Appointment upcomingAppointment = DBappointment.getAlertAppointment(User.getCurrentUserId(), LocalDateTime.now());
        if (upcomingAppointment != null) {
            String msg = "User " + User.getCurrentUserName() + " has an upcoming appointment (ID=" + upcomingAppointment.getId() + ") at " + upcomingAppointment.getStart();
            upcomingappointmentText.setText(msg);
            Main.dialogBox(Alert.AlertType.INFORMATION, "Upcoming Appointment Detected", msg);
        }
        else {
            String msg = "User " + User.getCurrentUserName() + " does not have any upcoming appointments within 15 minutes.";
            upcomingappointmentText.setText(msg);
            Main.dialogBox(Alert.AlertType.INFORMATION, "No Upcoming Appointment Detected", msg);
        }
    }

    private void populateScreen() throws SQLException {
        // Report A
        ObservableList<Month> allMonths = FXCollections.observableArrayList();
        for (int i = 1; i <= 12; i++) {
            allMonths.add(Month.of(i));
        }
        monthACBText.setItems(allMonths);
        typeACBText.setItems(DBappointment.getAllTypes());
        // Report B
        contactBCBText.setItems(DBcontact.getAllContacts());
        // Report C
        ObservableList<String> idTypes = FXCollections.observableArrayList();
        idTypes.addAll("Customer_ID", "User_ID", "Contact_ID");
        customIdType.setItems(idTypes);
    }

    @FXML
    void selectedMonthReportA(ActionEvent event) {
        if (typeACBText.getSelectionModel().getSelectedItem() != null) {
            totalAText.setText(String.valueOf(DBappointment.getMonthTypeAppointments(monthACBText.getSelectionModel().getSelectedItem(), typeACBText.getSelectionModel().getSelectedItem())));
        }
    }

    @FXML
    void selectedTypeReportA(ActionEvent event) {
        if (monthACBText.getSelectionModel().getSelectedItem() != null) {
            totalAText.setText(String.valueOf(DBappointment.getMonthTypeAppointments(monthACBText.getSelectionModel().getSelectedItem(), typeACBText.getSelectionModel().getSelectedItem())));
        }
    }

    @FXML
    void selectedContactReportB(ActionEvent event) {
        scheduleBTableview.setItems(DBappointment.getContactAppointments(contactBCBText.getSelectionModel().getSelectedItem().getId()));
        appidBTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleBTable.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeBTable.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionBTable.setCellValueFactory(new PropertyValueFactory<>("description"));
        startBTable.setCellValueFactory(new PropertyValueFactory<>("start"));
        endBTable.setCellValueFactory(new PropertyValueFactory<>("end"));
        customeridBTable.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    @FXML
    void selectedCustomIdType(ActionEvent event) {
        customIdNumber.setItems(DBappointment.getIdNumbers(customIdType.getSelectionModel().getSelectedItem()));
    }

    @FXML
    void selectedCustomIdNumber(ActionEvent event) {
        customAnswerText.setText(String.valueOf(DBappointment.getNumberOfCustomAppointments(customIdNumber.getSelectionModel().getSelectedItem())));
    }

    @FXML
    void clickCustomersButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/CUSmenu.fxml");
    }

    @FXML
    void clickAppointmentsButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/APTmenu.fxml");
    }

    @FXML
    void clickLogoutButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/login.fxml");
    }
}
