package controller;

import database.DBappointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    @FXML
    void clickAllRadioButton(ActionEvent event) throws SQLException {
        appointmentTableview.setItems(DBappointment.getAllAppointments());
        appointmentTableview.getSortOrder().add(appIdText);
    }

    @FXML
    void clickMonthRadioButton(ActionEvent event) throws SQLException {
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

    @FXML
    void clickWeekRadioButton(ActionEvent event) throws SQLException {
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

    @FXML
    void clickAddButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/APTadd.fxml");
    }

    @FXML
    void clickModifyButton(ActionEvent event) throws IOException, SQLException {
        Appointment selectedAppointment = appointmentTableview.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Main.dialogBox(Alert.AlertType.ERROR, "No Appointment Selected", "Please select an appointment and try again.");
        }
        else {
            APTmodController controller = Main.changeScene("/view/APTmod.fxml").getController();
            controller.displayAppointment(selectedAppointment);
        }
    }

    @FXML
    void clickDeleteButton(ActionEvent event) throws SQLException {
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

    @FXML
    void clickMainscreenButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/menu.fxml");
    }
}
