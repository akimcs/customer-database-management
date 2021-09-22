package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import mainApplication.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addCustomerController implements Initializable {

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
    private ComboBox<?> countryCBText;
    @FXML
    private ComboBox<?> firstleveldivisionCBText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Add Customer");
    }

    @FXML
    void clickCancelButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/mainScreen.fxml");
    }

    @FXML
    void clickSubmitButton(ActionEvent event) throws IOException {
        // TODO - filtering and error checking
        Main.changeScene("/view/mainScreen.fxml");
    }
}
