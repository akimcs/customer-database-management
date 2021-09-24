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

public class APTmenuController implements Initializable {

    @FXML
    private TableView<Appointment> appointmentTableview;
    @FXML
    private TableColumn<Appointment, Integer> appIdText;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, String> locationColumn;
    @FXML
    private TableColumn<Appointment, Integer> contactColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> startColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> endColumn;
    @FXML
    private TableColumn<Appointment, Integer> cusIdColumn;
    @FXML
    private TableColumn<Appointment, Integer> userIdColumn;
    @FXML
    private ToggleGroup Timespan;

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
    }

    @FXML
    void clickAllRadioButton(ActionEvent event) {
        appointmentTableview.setItems(DBappointment.getAllAppointments());
    }

    @FXML
    void clickMonthRadioButton(ActionEvent event) {
        Month currentMonth = LocalDateTime.now().getMonth();
        ObservableList<Appointment> currentMonthAppointments = FXCollections.observableArrayList();
        for (Appointment apt : DBappointment.getAllAppointments()) {
            if (apt.getStart().getMonth().equals(currentMonth)) {
                currentMonthAppointments.add(apt);
            }
        }
        appointmentTableview.setItems(currentMonthAppointments);
    }

    @FXML
    void clickWeekRadioButton(ActionEvent event) {
        int currentWeek = LocalDateTime.now().get(WeekFields.SUNDAY_START.weekOfWeekBasedYear());
        ObservableList<Appointment> currentWeekAppointments = FXCollections.observableArrayList();
        for (Appointment apt : DBappointment.getAllAppointments()) {
            if (currentWeek == apt.getStart().get(WeekFields.SUNDAY_START.weekOfWeekBasedYear())) {
                currentWeekAppointments.add(apt);
            }
        }
        appointmentTableview.setItems(currentWeekAppointments);
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
                if (DBappointment.deleteAppointment(selectedAppointment.getId()) > 0) {
                    Main.dialogBox(Alert.AlertType.INFORMATION, "Appointment Deleted", "Appointment Successfully Deleted.");
                }
                else {
                    Main.dialogBox(Alert.AlertType.ERROR, "Appointment Was Not Deleted", "An error Caused the Customer to not be deleted.");
                }

            }
        }
        appointmentTableview.getSelectionModel().select(null);
    }

    @FXML
    void clickMainscreenButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/menu.fxml");
    }
}
