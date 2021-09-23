package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import mainApplication.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
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
    private ComboBox<?> typeCBText;
    @FXML
    private ComboBox<?> StartHrText;
    @FXML
    private ComboBox<?> contactCBText;
    @FXML
    private DatePicker dateDPText;
    @FXML
    private ComboBox<?> StartMinText;
    @FXML
    private ComboBox<?> customeridCBText;
    @FXML
    private ComboBox<?> useridCBText;
    @FXML
    private ComboBox<?> EndHrText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Add Appointment");
    }

    @FXML
    void clickCancelButton(ActionEvent event) {

    }

    @FXML
    void clickSubmitButton(ActionEvent event) {

    }

    @FXML
    void selectedStartHour(ActionEvent event) {

    }

    @FXML
    void selectedStartMin(ActionEvent event) {

    }
}
