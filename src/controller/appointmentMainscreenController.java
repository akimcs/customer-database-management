package controller;

import javafx.fxml.Initializable;
import mainApplication.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class appointmentMainscreenController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Appointments Menu");

    }
}
