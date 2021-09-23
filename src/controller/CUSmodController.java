package controller;

import database.DBdivision;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mainApplication.Main;
import model.Country;
import model.Division;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CUSmodController implements Initializable {

    // TODO - LEAVE ALONE UNTIL ADD SCREEN FINISHED

    @FXML
    private Label customeridText;
    @FXML
    private TextField customernameText;
    @FXML
    private TextField addressText;
    @FXML
    private TextField postalcodeText;
    @FXML
    private TextField phonenumberText;
    @FXML
    private ComboBox<Country> countryCBText;
    @FXML
    private ComboBox<Division> firstleveldivisionCBText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Modify Customer");
    }

    @FXML
    void countrySelected(ActionEvent event) throws SQLException {
        firstleveldivisionCBText.setItems(DBdivision.getAllDivisions(countryCBText.getSelectionModel().getSelectedItem().getId()));
    }

    @FXML
    void clickCancelButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/menu.fxml");
    }

    @FXML
    void clickSubmitButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/menu.fxml");
    }
}
