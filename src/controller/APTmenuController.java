package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import mainApplication.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import java.net.URL;
import java.util.ResourceBundle;

public class APTmenuController implements Initializable {

    @FXML
    private TableView<?> appointmentTableview;
    @FXML
    private TableColumn<?, ?> appIdText;
    @FXML
    private TableColumn<?, ?> titleColumn;
    @FXML
    private TableColumn<?, ?> descriptionColumn;
    @FXML
    private TableColumn<?, ?> locationColumn;
    @FXML
    private TableColumn<?, ?> contactColumn;
    @FXML
    private TableColumn<?, ?> typeColumn;
    @FXML
    private TableColumn<?, ?> startColumn;
    @FXML
    private TableColumn<?, ?> endColumn;
    @FXML
    private TableColumn<?, ?> cusIdColumn;
    @FXML
    private TableColumn<?, ?> userIdColumn;
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
    void clickAddButton(ActionEvent event) {

    }

    @FXML
    void clickDeleteButton(ActionEvent event) {

    }

    @FXML
    void clickMainscreenButton(ActionEvent event) {

    }

    @FXML
    void clickModifyButton(ActionEvent event) {

    }
}
