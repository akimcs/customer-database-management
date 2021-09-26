package controller;

import database.DBcountry;
import database.DBcustomer;
import database.DBdivision;
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

    /**Sets window title name.
     * @param url the URL object
     * @param resourceBundle the ResourceBundle object
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Modify Customer");
    }

    /** Fills in all original customer information to be modified.
     * @param originalCustomer The original customer to be changed.
     * @throws SQLException Calls SQL Database Statements.
     * */
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

    /** Fills Division Choice Box only when Country Combo Box choice is selected.
     * @throws SQLException Calls SQL Database Statements.
     * */
    @FXML
    void countrySelected() throws SQLException {
        firstleveldivisionCBText.setItems(DBdivision.getAllDivisions(countryCBText.getSelectionModel().getSelectedItem().getId()));
    }

    /** Uses a Lambda Expression to detect empty fields in form.
     * The lambda expressions are used to check if fields are empty or null. They provide great use in shrinking the code.
     * Without them, the method requires repeated clutter of checking Strings, Integers, etc. Before they were implemented, the return statement was unreadable and cluttered with many parentheses.
     * @return Boolean true if empty/null field is detected.
     * */
    private boolean emptyFieldDetected() {
        // LAMBDA
        CheckTextEmpty text = s -> s.getText().trim().isEmpty();
        CheckComboNull combo = t -> t.getSelectionModel().getSelectedItem()==null;

        return text.isE(customernameText) || text.isE(addressText) || text.isE(postalcodeText) ||
                text.isE(phonenumberText) || combo.isN(countryCBText) || combo.isN(firstleveldivisionCBText);
    }

    /**Uses a Lambda Expression to detect if changes were made in the form.
     * The lambda expressions used in this form were critical for readability and shortening of code.
     * Before, the method utilized too many repeating segments and cluttered the method, making it difficult to understand.
     * With the lambda expression, the code becomes readable and better.
     * @return Boolean true if original appointment remained unchanged.
     * */
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

    /**The submit button to add the data to the database.
     * Implements many error checks and input validation to ensure data integrity.
     * @throws IOException Possible input/out errors.
     * */
    @FXML
    void clickSubmitButton() throws IOException {
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

    /** Cancels the add/mod form and goes back to menu without saving.
     * @throws IOException Possible input/out errors.
     * */
    @FXML
    void clickCancelButton() throws IOException {
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
