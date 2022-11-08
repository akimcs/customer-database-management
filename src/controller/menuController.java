package controller;

import database.DBappointment;
import database.DBcontact;
import database.DBcountry;
import database.DBcustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/** Handles the access to customer/appointment menus, 3 required reports to display, and upcoming appointment alerts in the Main Menu.*/
public class menuController implements Initializable {

    /**Label for upcoming appointment text*/
    @FXML
    private Label upcomingappointmentText;


    /**Combo Box for report A month*/
    @FXML
    private ComboBox<Month> monthACBText;
    /**Combo Box for report A type*/
    @FXML
    private ComboBox<String> typeACBText;
    /**Label for report A result*/
    @FXML
    private Label totalAText;


    /**Combo Box for report B contacts*/
    @FXML
    private ComboBox<Contact> contactBCBText;
    /**Tableview for report B appointments*/
    @FXML
    private TableView<Appointment> scheduleBTableview;
    /**Table Column for report B appointment id*/
    @FXML
    private TableColumn<Appointment, Integer> appidBTable;
    /**Table Column for report B title*/
    @FXML
    private TableColumn<Appointment, String> titleBTable;
    /**Table Column for report B type*/
    @FXML
    private TableColumn<Appointment, String> typeBTable;
    /**Table Column for report B description*/
    @FXML
    private TableColumn<Appointment, String> descriptionBTable;
    /**Table Column for report B start*/
    @FXML
    private TableColumn<Appointment, LocalDateTime> startBTable;
    /**Table Column for report B end*/
    @FXML
    private TableColumn<Appointment, LocalDateTime> endBTable;
    /**Table Column for report B customer id*/
    @FXML
    private TableColumn<Appointment, Integer> customeridBTable;


    /**Combo Box for custom report countries*/
    @FXML
    private ComboBox<Country> customCountryCB;
    /**Tableview for custome report customers*/
    @FXML
    private TableView<Customer> customCustomersTable;
    /**Table Column for custom report customer id*/
    @FXML
    private TableColumn<Customer, Integer> customIDColumn;
    /**Table Column for custom report name*/
    @FXML
    private TableColumn<Customer, String> customNameColumn;
    /**Table Column for custom report address*/
    @FXML
    private TableColumn<Customer, String> customAddressColumn;
    /**Table Column for custom report postal code*/
    @FXML
    private TableColumn<Customer, String> customPostalColumn;
    /**Table Column for custom report phone*/
    @FXML
    private TableColumn<Customer, String> customPhoneColumn;
    /**Table Column for custom report division*/
    @FXML
    private TableColumn<Customer, Integer> customDivisionColumn;

    /**Sets window title name.
     * @param url the URL object
     * @param resourceBundle the ResourceBundle object
     * */
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

    /** Checks if the logged user has any appointments within 15 minutes.
     * @throws SQLException Calls SQL Database Statements.
     * */
    public void checkForAppointment() throws SQLException {
        ObservableList<Appointment> allUpcomingAppointments = DBappointment.getAlertAppointments(User.getCurrentUserId());
        String msg;

        if (allUpcomingAppointments.isEmpty()) {
            msg = "User " + User.getCurrentUserName() + " does not have any upcoming appointments within 15 minutes.";
            upcomingappointmentText.setText(msg);
            // Main.dialogBox(Alert.AlertType.INFORMATION, "No Upcoming Appointments", msg);
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

    /** Fills the combo boxes with their respective lists of objects and fills the month combo box with a custom list of months.
     * @throws SQLException Calls SQL Database Statements.
     * */
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
        // Auto-Select Item for Default Views
        monthACBText.getSelectionModel().select(0);
        typeACBText.getSelectionModel().select(0);
        contactBCBText.getSelectionModel().select(0);
        customCountryCB.getSelectionModel().select(0);
        selectedTypeReportA();
        selectedContactReportB();
        selectedCustomCountryCB();
    }

    // Report A

    /** Displays the number of appointments associated with the month and type.
     * @throws SQLException Calls SQL Database Statements.
     * */
    @FXML
    void selectedMonthReportA() throws SQLException {
        if (typeACBText.getSelectionModel().getSelectedItem() != null) {
            totalAText.setText(String.valueOf(DBappointment.getMonthTypeAppointments(monthACBText.getSelectionModel().getSelectedItem(), typeACBText.getSelectionModel().getSelectedItem())));
        }
    }

    /** Displays the number of appointments associated with the month and type.
     * @throws SQLException Calls SQL Database Statements.
     * */
    @FXML
    void selectedTypeReportA() throws SQLException {
        if (monthACBText.getSelectionModel().getSelectedItem() != null) {
            totalAText.setText(String.valueOf(DBappointment.getMonthTypeAppointments(monthACBText.getSelectionModel().getSelectedItem(), typeACBText.getSelectionModel().getSelectedItem())));
        }
    }

    // Report B

    /** Displays the schedule for the chosen contact.
     * @throws SQLException Calls SQL Database Statements.
     * */
    @FXML
    void selectedContactReportB() throws SQLException {
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

    /** Displays all associated customers to the selected country.
     * @throws SQLException Calls SQL Database Statements.
     * */
    @FXML
    void selectedCustomCountryCB() throws SQLException {
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

    /** Changes the screen to the Customers Menu.
     * @throws IOException Possible input/out errors.
     * */
    @FXML
    void clickCustomersButton() throws IOException {
        Main.changeScene("/view/CUSmenu.fxml");
    }

    /** Changes the screen to the Appointments Menu.
     * @throws IOException Possible input/out errors.
     * */
    @FXML
    void clickAppointmentsButton() throws IOException {
        Main.changeScene("/view/APTmenu.fxml");
    }

    /** Logs the user out by sending them to the login screen.
     * @throws IOException Possible input/out errors.
     * */
    @FXML
    void clickLogoutButton() throws IOException {
        Main.changeScene("/view/login.fxml");
    }
}
