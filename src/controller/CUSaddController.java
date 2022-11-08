package controller;

import database.DBcountry;
import database.DBcustomer;
import database.DBdivision;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lambda.CheckComboNull;
import lambda.CheckTextEmpty;
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

    /**Sets window title name.
     * @param url the URL object
     * @param resourceBundle the ResourceBundle object
     * */
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

    /**Fills combo boxes using database calls for lists of respective objects.
     * @throws SQLException Calls SQL Database Statements.
     * */
    private void populateScreen() throws SQLException {
        customeridText.setText(String.valueOf(DBcustomer.nextCustomerId()));
        countryCBText.setItems(DBcountry.getAllCountries());
        countryCBText.getSelectionModel().select(0);
        countrySelected();
        firstleveldivisionCBText.getSelectionModel().select(0);
    }

    /** Fills Division Choice Box only when Country Combo Box choice is selected.
     * @throws SQLException Calls SQL Database Statements.
     * */
    @FXML
    void countrySelected() throws SQLException {
        firstleveldivisionCBText.setItems(DBdivision.getAllDivisions(countryCBText.getSelectionModel().getSelectedItem().getId()));
        firstleveldivisionCBText.getSelectionModel().select(0);
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

    /**Uses a Lambda Expression to detect if there is any text in the form.
     * Before the lambda expression was implemented, the method utilized repeated code to check each field, which created a big block of shirnkable code.
     * The lambda expressions are justified in that they allow the user to read the fields while still understanding that the lambda checks for null/empty.
     * @return Boolean true if text/selected values are detected in the form.
     * */
    private boolean textDetected() {
        // LAMBDA
        CheckTextEmpty text = s -> s.getText().trim().isEmpty();
        CheckComboNull combo = t -> t.getSelectionModel().getSelectedItem()==null;

        return !(text.isE(customernameText) && text.isE(addressText) && text.isE(postalcodeText) &&
                text.isE(phonenumberText) && combo.isN(countryCBText) && combo.isN(firstleveldivisionCBText));
    }

    /**The submit button to add the data to the database.
     * Implements many error checks and input validation to ensure data integrity.
     * */
    @FXML
    void clickSubmitButton() {
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

    /** Cancels the add/mod form and goes back to menu without saving.
     * @throws IOException Possible input/out errors.
     * */
    @FXML
    void clickCancelButton() throws IOException {
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
