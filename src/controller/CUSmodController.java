package controller;

import database.DBcountry;
import database.DBcustomer;
import database.DBdivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import mainApplication.Main;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CUSmodController implements Initializable {

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
        return customeridText.getText().trim().isEmpty() || customernameText.getText().trim().isEmpty() ||
                addressText.getText().trim().isEmpty() || postalcodeText.getText().trim().isEmpty() ||
                phonenumberText.getText().trim().isEmpty() || countryCBText.getSelectionModel().getSelectedItem()==null ||
                firstleveldivisionCBText.getSelectionModel().getSelectedItem()==null;
    }

    private boolean noChanges() {
        return originalCustomer.getId() == Integer.parseInt(customeridText.getText()) &&
                originalCustomer.getName().equals(customernameText.getText()) &&
                originalCustomer.getAddress().equals(addressText.getText()) &&
                originalCustomer.getPostal().equals(postalcodeText.getText()) &&
                originalCustomer.getPhone().equals(phonenumberText.getText()) &&
                originalCustomer.getCountryId() == countryCBText.getSelectionModel().getSelectedItem().getId() &&
                originalCustomer.getDivisionId() == firstleveldivisionCBText.getSelectionModel().getSelectedItem().getId();
    }

    @FXML
    void clickSubmitButton(ActionEvent event) throws IOException {
        if (noChanges()) {
            Optional<ButtonType> confirmationScreen = Main.dialogBox(Alert.AlertType.CONFIRMATION, "No Changes Detected in Form", "This action will not update any records. Continue?");
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

                // TODO
                int ZZZ = DBcustomer.modifyCustomer(new Customer(id, name, address, postal, phone,countryId, divisionId));
                System.out.println(ZZZ);
                if (ZZZ > 0) {
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
            Optional<ButtonType> confirmationScreen = Main.dialogBox(Alert.AlertType.CONFIRMATION, "No Changes Detected in Form", "Changes will not be saved. Continue?");
            if (confirmationScreen.isPresent() && confirmationScreen.get() == ButtonType.OK) {
                Main.changeScene("/view/CUSmenu.fxml");
            }
        }
    }
}
