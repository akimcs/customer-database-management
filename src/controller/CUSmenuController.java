package controller;

import database.DBappointment;
import database.DBcustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mainApplication.Main;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CUSmenuController implements Initializable {

    @FXML
    private TableView<Customer> customerTableview;
    @FXML
    private TableColumn<Customer, Integer> cusidTable;
    @FXML
    private TableColumn<Customer, String> nameTable;
    @FXML
    private TableColumn<Customer, String> addressTable;
    @FXML
    private TableColumn<Customer, String> postalcodeTable;
    @FXML
    private TableColumn<Customer, String> phonenumberTable;
    @FXML
    private TableColumn<Customer, Integer> countryTable;
    @FXML
    private TableColumn<Customer, Integer> firstleveldivisionTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Customers Menu");
        try {
            populateScreen();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void populateScreen() throws SQLException {
        customerTableview.setItems(DBcustomer.getAllCustomers());
        cusidTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressTable.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalcodeTable.setCellValueFactory(new PropertyValueFactory<>("postal"));
        phonenumberTable.setCellValueFactory(new PropertyValueFactory<>("phone"));
        countryTable.setCellValueFactory(new PropertyValueFactory<>("countryId"));
        firstleveldivisionTable.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        customerTableview.getSortOrder().add(cusidTable);
    }

    @FXML
    void clickAddButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/CUSadd.fxml");
    }

    @FXML
    void clickModifyButton(ActionEvent event) throws IOException, SQLException {
        Customer selectedCustomer = customerTableview.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Main.dialogBox(Alert.AlertType.ERROR, "No Customer Selected", "Please select a customer and try again.");
        }
        else {
            CUSmodController controller = Main.changeScene("/view/CUSmod.fxml").getController();
            controller.displayCustomer(selectedCustomer);
        }
    }

    @FXML
    void clickDeleteButton(ActionEvent event) throws SQLException {
        Customer selectedCustomer = customerTableview.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Main.dialogBox(Alert.AlertType.ERROR, "No Customer Selected", "Please select a customer and try again.");
        }
        else {
            Optional<ButtonType> confirmationScreen = Main.dialogBox(Alert.AlertType.CONFIRMATION, "Customer Delete Confirmation", "This action will delete the customer's record and all their appointments. Continue?");
            if (confirmationScreen.isPresent() && confirmationScreen.get() == ButtonType.OK) {
                if (DBappointment.deleteAllCustomerAppointments(selectedCustomer.getId()) == 1) {
                    Main.dialogBox(Alert.AlertType.INFORMATION, "Customer's Appointments Deleted", "All appointments associated with selected customer were deleted.");
                    if (DBcustomer.deleteCustomer(selectedCustomer.getId()) == 1) {
                        Main.dialogBox(Alert.AlertType.INFORMATION, "Customer Deleted", "Customer Successfully Deleted.");
                    }
                    else {
                        Main.dialogBox(Alert.AlertType.ERROR, "Customer Was Not Deleted", "An error caused the Customer to not be deleted.");
                    }
                }
                else {
                    Main.dialogBox(Alert.AlertType.ERROR, "Customer and Customer's Appointments Were Not Deleted", "An error caused the customer's appointments to not be deleted. Therefore, the customer was also not deleted.");
                }
            }
        }
        populateScreen();
        customerTableview.getSelectionModel().select(null);
    }

    @FXML
    void clickMainscreenButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/menu.fxml");
    }
}
