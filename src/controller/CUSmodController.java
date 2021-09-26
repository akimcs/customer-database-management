package controller;

import database.DBcountry;
import database.DBcustomer;
import database.DBdivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lambda.*;
import mainApplication.Main;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**Handles the input validation, buttons, and display of the Modify Customer Form.*/
public class CUSmodController implements Initializable {

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

    /**Holds original customer being modification*/
    private Customer originalCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Modify Customer");
    }

    public void displayCustomer(Customer originalCustomer) throws SQLException {
        this.originalCustomer = originalCustomer;

        countryCBText.setItems(DBcountry.getAllCountries());
        firstleveldivisionCBText.setItems(DBdivision.getAllDivisions(originalCustomer.getCountryId()));

        customeridText.setText(String.valueOf(originalCustomer.getId()));
        customernameText.setText(originalCustomer.getName());
        addressText.setText(originalCustomer.getAddress());
        postalcodeText.setText(originalCustomer.getPostal());
        phonenumberText.setText(originalCustomer.getPhone());
        countryCBText.getSelectionModel().select(DBcountry.getCountry(originalCustomer.getCountryId()));
        firstleveldivisionCBText.getSelectionModel().select(DBdivision.getDivision(originalCustomer.getDivisionId()));
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

    private boolean noChanges() {
        // LAMBDA
        VerifyEqualString text = (q, r) -> q.equals(r);
        VerifyEqualInteger combo = (s, t) -> s==t;
        GetStr str = a -> a.getText();

        return  text.eq(originalCustomer.getName(),         str.gt(customernameText)) &&
                text.eq(originalCustomer.getAddress(),      str.gt(addressText)) &&
                text.eq(originalCustomer.getPostal(),       str.gt(postalcodeText)) &&
                text.eq(originalCustomer.getPhone(),        str.gt(phonenumberText)) &&
                combo.eq(originalCustomer.getCountryId(),   countryCBText.getSelectionModel().getSelectedItem().getId()) &&
                combo.eq(originalCustomer.getDivisionId(),  firstleveldivisionCBText.getSelectionModel().getSelectedItem().getId());
    }

    @FXML
    void clickSubmitButton(ActionEvent event) throws IOException {
        if (noChanges()) {
            Optional<ButtonType> confirmationScreen = Main.dialogBox(Alert.AlertType.INFORMATION, "No Changes Detected in Form", "No Changes Detected. Customer Will Not Be Modified.");
            if (confirmationScreen.isPresent() && confirmationScreen.get() == ButtonType.OK) {
                Main.changeScene("/view/CUSmenu.fxml");
            }
        }
        else if (emptyFieldDetected()) {
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

                if (DBcustomer.modifyCustomer(new Customer(id, name, address, postal, phone,countryId, divisionId)) == 1) {
                    Main.dialogBox(Alert.AlertType.INFORMATION, "Customer Successfully Modified", "Customer has been modified.");
                    Main.changeScene("/view/CUSmenu.fxml");
                }
                else {
                    Main.dialogBox(Alert.AlertType.ERROR, "Customer Not Modified", "Customer was unable to be modified.");
                }
            }
            catch (Exception e) {
                Main.dialogBox(Alert.AlertType.ERROR, "Improper Input Detected", "Ensure All Fields Are Correctly Formatted.");
            }
        }
    }

    @FXML
    void clickCancelButton(ActionEvent event) throws IOException {
        if (noChanges()) {
            Main.changeScene("/view/CUSmenu.fxml");
        }
        else {
            Optional<ButtonType> confirmationScreen = Main.dialogBox(Alert.AlertType.CONFIRMATION, "Changes Detected in Form", "Changes will not be saved. Continue?");
            if (confirmationScreen.isPresent() && confirmationScreen.get() == ButtonType.OK) {
                Main.changeScene("/view/CUSmenu.fxml");
            }
        }
    }
}
