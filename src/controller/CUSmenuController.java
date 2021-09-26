package controller;

import database.DBappointment;
import database.DBcustomer;
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

/**Handles the TableView display, customer deletion, and Add/Modify Scence changes of the Customer Menu.*/
public class CUSmenuController implements Initializable {

    /**Tableview for all customers*/
    @FXML
    private TableView<Customer> customerTableview;
    /**Column in Tableview for customer id*/
    @FXML
    private TableColumn<Customer, Integer> cusidTable;
    /**Column in Tableview for name*/
    @FXML
    private TableColumn<Customer, String> nameTable;
    /**Column in Tableview for address*/
    @FXML
    private TableColumn<Customer, String> addressTable;
    /**Column in Tableview for postal code*/
    @FXML
    private TableColumn<Customer, String> postalcodeTable;
    /**Column in Tableview for phone number*/
    @FXML
    private TableColumn<Customer, String> phonenumberTable;
    /**Column in Tableview for country*/
    @FXML
    private TableColumn<Customer, Integer> countryTable;
    /**Column in Tableview for division*/
    @FXML
    private TableColumn<Customer, Integer> firstleveldivisionTable;

    /**Sets window title name.
     * @param url the URL object
     * @param resourceBundle the ResourceBundle object
     * */
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

    /**Populates the tableview with customer objects using an allCustomers list.
     * @throws SQLException Calls SQL Database Statements.
     * */
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

    /** Sends GUI to add form screen.
     * @throws IOException Possible input/out errors.
     * */
    @FXML
    void clickAddButton() throws IOException {
        Main.changeScene("/view/CUSadd.fxml");
    }

    /** Sends GUI to modify form screen, bringing selected object.
     * @throws IOException Scene change may cause error.
     * @throws SQLException Database Calls may cause error.
     * */
    @FXML
    void clickModifyButton() throws IOException, SQLException {
        Customer selectedCustomer = customerTableview.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Main.dialogBox(Alert.AlertType.ERROR, "No Customer Selected", "Please select a customer and try again.");
        }
        else {
            CUSmodController controller = Main.changeScene("/view/CUSmod.fxml").getController();
            controller.displayCustomer(selectedCustomer);
        }
    }

    /** Deletes the selected customer after deleting all associated appointments.
     * @throws SQLException Calls SQL Database Statements.
     * */
    @FXML
    void clickDeleteButton() throws SQLException {
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

    /** Go from Customer Menu to Mainscreen.
     * @throws IOException Possible input/out errors.
     * */
    @FXML
    void clickMainscreenButton() throws IOException {
        Main.changeScene("/view/menu.fxml");
    }
}
