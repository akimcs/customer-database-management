package controller;

import database.DBappointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mainApplication.Main;
import model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.Optional;
import java.util.ResourceBundle;

/**Handles the TableView display, radio buttons, appointment deletion, and Add/Modify Scence changes of the Appointments Menu.*/
public class APTmenuController implements Initializable {

    /**Tableview for all appointments*/
    @FXML
    private TableView<Appointment> appointmentTableview;
    /**Column in Tableview for appointment id*/
    @FXML
    private TableColumn<Appointment, Integer> appIdText;
    /**Column in Tableview for title*/
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    /**Column in Tableview for description*/
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    /**Column in Tableview for location*/
    @FXML
    private TableColumn<Appointment, String> locationColumn;
    /**Column in Tableview for contact*/
    @FXML
    private TableColumn<Appointment, Integer> contactColumn;
    /**Column in Tableview for type*/
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    /**Column in Tableview for start*/
    @FXML
    private TableColumn<Appointment, LocalDateTime> startColumn;
    /**Column in Tableview for end*/
    @FXML
    private TableColumn<Appointment, LocalDateTime> endColumn;
    /**Column in Tableview for customer*/
    @FXML
    private TableColumn<Appointment, Integer> cusIdColumn;
    /**Column in Tableview for user*/
    @FXML
    private TableColumn<Appointment, Integer> userIdColumn;

    /**Sets window title name.
     * @param url the URL object
     * @param resourceBundle the ResourceBundle object
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Appointment Menu");
        try {
            populateScreen();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**Populates the tableview with appointment objects obtained using database calls for allAppointments.
     * @throws SQLException Calls SQL Database Statements.
     * */
    private void populateScreen() throws SQLException {
        appointmentTableview.setItems(DBappointment.getAllAppointments());
        appIdText.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        cusIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appointmentTableview.getSortOrder().add(appIdText);
    }

    /** Filters the tableview of appointments to show all.
     * @throws SQLException Calls SQL Database Statements.
     * */
    @FXML
    void clickAllRadioButton() throws SQLException {
        appointmentTableview.setItems(DBappointment.getAllAppointments());
        appointmentTableview.getSortOrder().add(appIdText);
    }

    /**Filters the tableview of appointments to show current month.
     * @throws SQLException Calls SQL Database Statements.
     * */
    @FXML
    void clickMonthRadioButton() throws SQLException {
        Month currentMonth = LocalDateTime.now().getMonth();
        ObservableList<Appointment> currentMonthAppointments = FXCollections.observableArrayList();
        for (Appointment apt : DBappointment.getAllAppointments()) {
            if (apt.getStart().getMonth().equals(currentMonth)) {
                currentMonthAppointments.add(apt);
            }
        }
        appointmentTableview.setItems(currentMonthAppointments);
        appointmentTableview.getSortOrder().add(appIdText);
    }

    /**Filters the tableview of appointments to show current week.
     * @throws SQLException Calls SQL Database Statements.
     * */
    @FXML
    void clickWeekRadioButton() throws SQLException {
        int currentWeek = LocalDateTime.now().get(WeekFields.SUNDAY_START.weekOfWeekBasedYear());
        ObservableList<Appointment> currentWeekAppointments = FXCollections.observableArrayList();
        for (Appointment apt : DBappointment.getAllAppointments()) {
            if (currentWeek == apt.getStart().get(WeekFields.SUNDAY_START.weekOfWeekBasedYear())) {
                currentWeekAppointments.add(apt);
            }
        }
        appointmentTableview.setItems(currentWeekAppointments);
        appointmentTableview.getSortOrder().add(appIdText);
    }

    /** Sends GUI to add form screen.
     * @throws IOException Possible input/out errors.
     * */
    @FXML
    void clickAddButton() throws IOException {
        Main.changeScene("/view/APTadd.fxml");
    }

    /** Sends GUI to modify form screen, bringing selected object.
     * @throws IOException Scene change may cause error.
     * @throws SQLException Database Calls may cause error.
     * */
    @FXML
    void clickModifyButton() throws IOException, SQLException {
        Appointment selectedAppointment = appointmentTableview.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Main.dialogBox(Alert.AlertType.ERROR, "No Appointment Selected", "Please select an appointment and try again.");
        }
        else {
            APTmodController controller = Main.changeScene("/view/APTmod.fxml").getController();
            controller.displayAppointment(selectedAppointment);
        }
    }

    /** Deletes the selected appointment.
     * @throws SQLException Calls SQL Database Statements.
     * */
    @FXML
    void clickDeleteButton() throws SQLException {
        Appointment selectedAppointment = appointmentTableview.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Main.dialogBox(Alert.AlertType.ERROR, "No Appointment Selected", "Please select an appointment and try again.");
        }
        else {
            Optional<ButtonType> confirmationScreen = Main.dialogBox(Alert.AlertType.CONFIRMATION, "Appointment Delete Confirmation", "This action will delete the appointment. Continue?");
            if (confirmationScreen.isPresent() && confirmationScreen.get() == ButtonType.OK) {
                if (DBappointment.deleteAppointment(selectedAppointment.getId()) == 1) {
                    int appId = selectedAppointment.getId();
                    String appType = selectedAppointment.getType();
                    Main.dialogBox(Alert.AlertType.INFORMATION, "Appointment Deleted", "Appointment ID " + appId + " of Type " + appType + " has been successfully deleted.");
                }
                else {
                    Main.dialogBox(Alert.AlertType.ERROR, "Appointment Was Not Deleted", "An error Caused the Customer to not be deleted.");
                }

            }
        }
        populateScreen();
        appointmentTableview.getSelectionModel().select(null);
    }

    /** Go from Appointment Menu to Mainscreen.
     * @throws IOException Possible input/out errors.
     * */
    @FXML
    void clickMainscreenButton() throws IOException {
        Main.changeScene("/view/menu.fxml");
    }
}
