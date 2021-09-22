package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mainApplication.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainScreenController implements Initializable {

    @FXML
    private Label upcomingappointmentText;
    @FXML
    private ComboBox<?> monthACBText;
    @FXML
    private ComboBox<?> typeACBText;
    @FXML
    private Label totalAText;
    @FXML
    private ComboBox<?> contactBCBText;
    @FXML
    private TableView<?> scheduleBTableview;
    @FXML
    private TableColumn<?, ?> appidBTable;
    @FXML
    private TableColumn<?, ?> titleBTable;
    @FXML
    private TableColumn<?, ?> typeBTable;
    @FXML
    private TableColumn<?, ?> descriptionBTable;
    @FXML
    private TableColumn<?, ?> startBTable;
    @FXML
    private TableColumn<?, ?> endBTable;
    @FXML
    private TableColumn<?, ?> customeridBTable;

    // TODO - Total 3 Reports in GUI (1) Two combo boxes and One text field (2) One combo box and One Tableview (3) Custom

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Mainscreen Menu");
        // TODO - fill tableview, combo boxes,
        // TODO - Put in-stage area in GUI for 15 min alert popup during initialization of stage. Check for appointment in 15 minutes
    }

    @FXML
    void clickCustomersButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/customerMainscreen.fxml");
    }

    @FXML
    void clickAppointmentsButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/appointmentMainscreen.fxml");
    }

    @FXML
    void clickLogoutButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/loginScreen.fxml");
    }
}
