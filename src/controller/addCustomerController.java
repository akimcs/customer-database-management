package controller;

import database.countryRecords;
import database.customerRecords;
import database.divisionRecords;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import mainApplication.Main;
import javafx.fxml.FXML;
import model.Country;
import model.Customer;
import model.Division;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private ComboBox<Country> countryCBText;
    @FXML
    private ComboBox<Division> firstleveldivisionCBText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Add Customer");
        try {
            populateScreen();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void populateScreen() throws SQLException {
        customeridText.setText(String.valueOf(customerRecords.nextCustomerId()));
        countryCBText.setItems(countryRecords.getAllCountries());
    }

    @FXML
    void countrySelected(ActionEvent event) throws SQLException {
        firstleveldivisionCBText.setItems(divisionRecords.getAllDivisions(countryCBText.getSelectionModel().getSelectedItem().getId()));
    }

    @FXML
    void clickCancelButton(ActionEvent event) throws IOException {
        if (!fieldEmpty()) {
            Optional<ButtonType> confirmationScreen = Main.dialogBox(Alert.AlertType.CONFIRMATION, "Changes Detected in Form", "This action will delete all changes, continue?");
            if (confirmationScreen.isPresent() && confirmationScreen.get() == ButtonType.OK) {
                Main.changeScene("/view/customerMainscreen.fxml");
            }
        }
        else {
            Main.changeScene("/view/customerMainscreen.fxml");
        }
    }

    @FXML
    void clickSubmitButton(ActionEvent event) throws IOException {
        if (fieldEmpty()) {
            Main.dialogBox(Alert.AlertType.ERROR, "Empty Field Detected", "Make Sure All Fields Are Filled Out.");
        }
        else {
            try {
                int id = Integer.parseInt(customeridText.getText());
                String name = customernameText.getText();
                String address = addressText.getText();
                String postal = postalcodeText.getText();
                String phone = phonenumberText.getText();
                int divisionId = firstleveldivisionCBText.getSelectionModel().getSelectedItem().getId();

                customerRecords.addCustomer(new Customer(id, name, address, postal, phone, divisionId));
                // TODO - finish adding customer
                Main.changeScene("/view/customerMainscreen.fxml");
            }
            catch (Exception e) {
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
