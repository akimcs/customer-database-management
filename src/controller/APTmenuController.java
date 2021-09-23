package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import mainApplication.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
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
    private TableColumn<Appointment, String> startColumn;
    @FXML
    private TableColumn<Appointment, String> endColumn;
    @FXML
    private TableColumn<Appointment, Integer> cusIdColumn;
    @FXML
    private TableColumn<Appointment, Integer> userIdColumn;
    @FXML
    private ToggleGroup Timespan;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Appointment Menu");

    }

    @FXML
    void clickAllRadioButton(ActionEvent event) {

    }

    @FXML
    void clickMonthRadioButton(ActionEvent event) {

    }

    @FXML
    void clickWeekRadioButton(ActionEvent event) {

    }

    @FXML
    void clickAddButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/APTadd.fxml");
    }

    @FXML
    void clickDeleteButton(ActionEvent event) {

    }

    @FXML
    void clickMainscreenButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/menu.fxml");
    }

    @FXML
    void clickModifyButton(ActionEvent event) {

    }
}
