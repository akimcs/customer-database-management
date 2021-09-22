package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import mainApplication.Main;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
        // TODO - customeridText = max+1 = (SELECT MAX(Customer_ID) FROM customers) + 1
        // TODO - Fill choices in both combo boxes
    }

    @FXML
    void clickCancelButton(ActionEvent event) throws IOException {
        if (!fieldEmpty()) {
            Optional<ButtonType> confirmationScreen = Main.dialogBox(Alert.AlertType.CONFIRMATION, "Changes Detected in Form", "This action will delete all changes, continue?");
            if (confirmationScreen.isPresent() && confirmationScreen.get() == ButtonType.OK) {
                Main.changeScene("/view/mainScreen.fxml");
            }
        }
        else {
            Main.changeScene("/view/mainScreen.fxml");
        }
    }

    @FXML
    void clickSubmitButton(ActionEvent event) throws IOException {
        // TODO - Finish Method
        if (fieldEmpty()) {
            Main.dialogBox(Alert.AlertType.ERROR, "Empty Field Detected", "Make Sure All Fields Are Filled Out.");
        }
        else {
            try {
                Integer cusID = Integer.parseInt(customeridText.getText());
                // TODO - filtering and error checking
                Main.changeScene("/view/mainScreen.fxml");
            }
            catch(Exception e) {
                Main.dialogBox(Alert.AlertType.ERROR, "Improper Input Detected", "Ensure All Fields Are Correctly Formatted");
            }
        }
    }

    private boolean fieldEmpty() {
        if (customernameText.getText().trim().isEmpty() || addressText.getText().trim().isEmpty() || postalcodeText.getText().trim().isEmpty() || phonenumberText.getText().trim().isEmpty() || countryCBText.getSelectionModel().isEmpty() || firstleveldivisionCBText.getSelectionModel().isEmpty()) {
            return true;
        }
        return false;
    }
}
