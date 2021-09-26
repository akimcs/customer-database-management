package controller;

import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mainApplication.Main;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class menuController implements Initializable {

    // Upcoming Appointment
    @FXML
    private Label upcomingappointmentText;

    // Report A
    @FXML
    private ComboBox<Month> monthACBText;
    @FXML
    private ComboBox<String> typeACBText;
    @FXML
    private Label totalAText;


    // Report B
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

    // Report C
    @FXML
    private ComboBox<Country> customCountryCB;
    @FXML
    private TableView<Customer> customCustomersTable;
    @FXML
    private TableColumn<Customer, Integer> customIDColumn;
    @FXML
    private TableColumn<Customer, String> customNameColumn;
    @FXML
    private TableColumn<Customer, String> customAddressColumn;
    @FXML
    private TableColumn<Customer, String> customPostalColumn;
    @FXML
    private TableColumn<Customer, String> customPhoneColumn;
    @FXML
    private TableColumn<Customer, Integer> customDivisionColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Menu");
        try {
            populateScreen();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void checkForAppointment() throws SQLException {
        ObservableList<Appointment> allUpcomingAppointments = DBappointment.getAlertAppointments(User.getCurrentUserId());
        String msg;

        if (allUpcomingAppointments.isEmpty()) {
            msg = "User " + User.getCurrentUserName() + " does not have any upcoming appointments within 15 minutes.";
            upcomingappointmentText.setText(msg);
            Main.dialogBox(Alert.AlertType.INFORMATION, "No Upcoming Appointments", msg);
        }
        else {
            Appointment soonestAppointment = allUpcomingAppointments.get(0);
            for (Appointment apt : allUpcomingAppointments) {
                msg = "User   " + User.getCurrentUserName() + "   has an upcoming appointment   (ID=" + apt.getId() + ")   at   " + apt.getStart().format(DateTimeFormatter.ofPattern("HH:mm   yyyy-MM-dd")) + "   local time.";
                upcomingappointmentText.setText(msg);
                Main.dialogBox(Alert.AlertType.INFORMATION, "Upcoming Appointment Detected", msg);
            }
            msg = "User   " + User.getCurrentUserName() + "   has their soonest upcoming appointment   (ID=" + soonestAppointment.getId() + ")   at   " + soonestAppointment.getStart().format(DateTimeFormatter.ofPattern("HH:mm   yyyy-MM-dd")) + "   local time.";
            upcomingappointmentText.setText(msg);
        }
    }

    private void populateScreen() throws SQLException {
        // Report A
        ObservableList<Month> allMonths = FXCollections.observableArrayList();
        for (int i = 1; i <= 12; i++) {
            allMonths.add(Month.of(i));
        }
        monthACBText.setItems(allMonths);
        monthACBText.setVisibleRowCount(12);
        typeACBText.setItems(DBappointment.getAllDistinctTypes());
        // Report B
        contactBCBText.setItems(DBcontact.getAllContacts());
        // Report C
        customCountryCB.setItems(DBcountry.getAllCountries());
    }

    // Report A

    @FXML
    void selectedMonthReportA(ActionEvent event) throws SQLException {
        if (typeACBText.getSelectionModel().getSelectedItem() != null) {
            totalAText.setText(String.valueOf(DBappointment.getMonthTypeAppointments(monthACBText.getSelectionModel().getSelectedItem(), typeACBText.getSelectionModel().getSelectedItem())));
        }
    }

    @FXML
    void selectedTypeReportA(ActionEvent event) throws SQLException {
        if (monthACBText.getSelectionModel().getSelectedItem() != null) {
            totalAText.setText(String.valueOf(DBappointment.getMonthTypeAppointments(monthACBText.getSelectionModel().getSelectedItem(), typeACBText.getSelectionModel().getSelectedItem())));
        }
    }

    // Report B

    @FXML
    void selectedContactReportB(ActionEvent event) throws SQLException {
        scheduleBTableview.setItems(DBappointment.getContactAppointments(contactBCBText.getSelectionModel().getSelectedItem().getId()));
        appidBTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleBTable.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeBTable.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionBTable.setCellValueFactory(new PropertyValueFactory<>("description"));
        startBTable.setCellValueFactory(new PropertyValueFactory<>("start"));
        endBTable.setCellValueFactory(new PropertyValueFactory<>("end"));
        customeridBTable.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    // Report C

    @FXML
    void selectedCustomCountryCB(ActionEvent event) throws SQLException {
        customCustomersTable.setItems(DBcustomer.getAllCountryCustomers(customCountryCB.getSelectionModel().getSelectedItem().getId()));
        customIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customPostalColumn.setCellValueFactory(new PropertyValueFactory<>("postal"));
        customPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        customCustomersTable.getSortOrder().add(customIDColumn);
    }


    // Menu Buttons

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
