package controller;

import javafx.fxml.Initializable;
import mainApplication.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class addAppointmentController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Add Appointment");
    }
}
