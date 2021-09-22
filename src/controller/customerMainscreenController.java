package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mainApplication.Main;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class customerMainscreenController implements Initializable {

    @FXML
    private TableView<Customer> customerTableview; // TODO - Fill ? in Brackets
    @FXML
    private TableColumn<?, ?> cusidTable;
    @FXML
    private TableColumn<?, ?> nameTable;
    @FXML
    private TableColumn<?, ?> addressTable;
    @FXML
    private TableColumn<?, ?> postalcodeTable;
    @FXML
    private TableColumn<?, ?> phonenumberTable;
    @FXML
    private TableColumn<?, ?> countryTable;
    @FXML
    private TableColumn<?, ?> firstleveldivisionTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Customers Menu");
    }

    @FXML
    void clickAddButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/addCustomer.fxml");
    }

    @FXML
    void clickModifyButton(ActionEvent event) {

    }

    @FXML
    void clickDeleteButton(ActionEvent event) {
        Customer selectedCustomer = customerTableview.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Main.dialogBox(Alert.AlertType.ERROR, "No Customer Selected", "Please select a customer and try again.");
        }
        else {
            Optional<ButtonType> confirmationScreen = Main.dialogBox(Alert.AlertType.CONFIRMATION, "Customer Delete Confirmation", "This action will delete the customer's record and all appointments. Continue?");
            if (confirmationScreen.isPresent() && confirmationScreen.get() == ButtonType.OK) {
                // TODO - Delete Associated Appointments then Delete Customer
            }
        }
        customerTableview.getSelectionModel().select(null);
    }

    @FXML
    void clickMainscreenButton(ActionEvent event) throws IOException {
        Main.changeScene("/view/mainScreen.fxml");
    }
}
