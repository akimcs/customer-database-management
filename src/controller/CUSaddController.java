package controller;

import database.DBcountry;
import database.DBcustomer;
import database.DBdivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lambda.CheckTextEmpty;
import lambda.CheckComboNull;
import lambda.CheckDateNull;
import mainApplication.Main;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**Handles the input validation, buttons, and display of the Add Customer Form.*/
public class CUSaddController implements Initializable {

    /**Displays the next available customer id*/
    @FXML
    private Label customeridText;
    /**Field to input name*/
    @FXML
    private TextField customernameText;
    /**Field to input address*/
    @FXML
    private TextField addressText;
    /**Field to input postal code*/
    @FXML
    private TextField postalcodeText;
    /**Field to input phone number*/
    @FXML
    private TextField phonenumberText;
    /**Combo Box to select country*/
    @FXML
    private ComboBox<Country> countryCBText;
    /**Combo Box to select division*/
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
        customeridText.setText(String.valueOf(DBcustomer.nextCustomerId()));
        countryCBText.setItems(DBcountry.getAllCountries());
    }

    @FXML
    void countrySelected(ActionEvent event) throws SQLException {
        firstleveldivisionCBText.setItems(DBdivision.getAllDivisions(countryCBText.getSelectionModel().getSelectedItem().getId()));
    }

    private boolean emptyFieldDetected() {
        // LAMBDA
        CheckTextEmpty text = s -> s.getText().trim().isEmpty();
        CheckComboNull combo = t -> t.getSelectionModel().getSelectedItem()==null;
        CheckDateNull date = x -> x.getValue()==null;

        return text.isE(customernameText) || text.isE(addressText) || text.isE(postalcodeText) ||
                text.isE(phonenumberText) || combo.isN(countryCBText) || combo.isN(firstleveldivisionCBText);
    }

    private boolean textDetected() {
        // LAMBDA
        CheckTextEmpty text = s -> s.getText().trim().isEmpty();
        CheckComboNull combo = t -> t.getSelectionModel().getSelectedItem()==null;
        CheckDateNull date = x -> x.getValue()==null;

        return !(text.isE(customernameText) && text.isE(addressText) && text.isE(postalcodeText) &&
                text.isE(phonenumberText) && combo.isN(countryCBText) && combo.isN(firstleveldivisionCBText));
    }

    @FXML
    void clickSubmitButton(ActionEvent event) throws IOException {
        if (emptyFieldDetected()) {
            Main.dialogBox(Alert.AlertType.ERROR, "Empty Field Detected", "Make Sure All Fields Are Filled Out.");
        }
        else {
            try {
                int id = Integer.parseInt(customeridText.getText());
                String name = customernameText.getText();
                String address = addressText.getText();
                String postal = postalcodeText.getText();
                String phone = phonenumberText.getText();
                int countryId = countryCBText.getSelectionModel().getSelectedItem().getId();
                int divisionId = firstleveldivisionCBText.getSelectionModel().getSelectedItem().getId();

                if (DBcustomer.addCustomer(new Customer(id, name, address, postal, phone,countryId, divisionId)) == 1) {
                    Main.dialogBox(Alert.AlertType.INFORMATION, "Customer Successfully Added", "New customer has been added.");
                    Main.changeScene("/view/CUSmenu.fxml");
                }
                else {
                    Main.dialogBox(Alert.AlertType.ERROR, "Customer Not Added", "Customer was unable to be added.");
                }
            }
            catch (Exception e) {
                Main.dialogBox(Alert.AlertType.ERROR, "Improper Input Detected", "Ensure All Fields Are Correctly Formatted.");
            }
        }
    }

    @FXML
    void clickCancelButton(ActionEvent event) throws IOException {
        if (textDetected()) {
            Optional<ButtonType> confirmationScreen = Main.dialogBox(Alert.AlertType.CONFIRMATION, "Changes Detected in Form", "This action will delete all changes, continue?");
            if (confirmationScreen.isPresent() && confirmationScreen.get() == ButtonType.OK) {
                Main.changeScene("/view/CUSmenu.fxml");
            }
        }
        else {
            Main.changeScene("/view/CUSmenu.fxml");
        }
    }
}
