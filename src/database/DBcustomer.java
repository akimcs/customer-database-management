package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBcustomer {

    // CUSaddController - Generates the next highest customer ID to use
    public static int nextCustomerId() throws SQLException {
        ResultSet result = JDBC.exQuery("SELECT max(Customer_ID) + 1 as Customer_ID FROM customers");

        result.next();
        int id = result.getInt("Customer_ID");

        JDBC.disconnect();
        return id;
    }

    // CUSaddController - Adds customer object to database table customers
    public static int addCustomer(Customer customer) throws SQLException{
        PreparedStatement stmt = JDBC.pStatement("INSERT INTO customers VALUES (?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?)");
        stmt.setInt(1, customer.getId());
        stmt.setString(2, customer.getName());
        stmt.setString(3, customer.getAddress());
        stmt.setString(4, customer.getPostal());
        stmt.setString(5, customer.getPhone());
        stmt.setString(6, User.getCurrentUserName());
        stmt.setString(7, User.getCurrentUserName());
        stmt.setInt(8, customer.getDivisionId());
        int rowCount = stmt.executeUpdate();
        JDBC.disconnect();
        return rowCount;
    }

    // CUSmodController - Updates customer object in database
    public static int modifyCustomer(Customer customer) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update=NOW(), Last_Updated_By=?, Division_ID=? WHERE Customer_Id=?");
        stmt.setString(1, customer.getName());
        stmt.setString(2, customer.getAddress());
        stmt.setString(3, customer.getPostal());
        stmt.setString(4, customer.getPhone());
        stmt.setString(5, User.getCurrentUserName());
        stmt.setInt(6, customer.getDivisionId());
        stmt.setInt(7, customer.getId());
        int rowCount = stmt.executeUpdate();
        JDBC.disconnect();
        return rowCount;
    }

    // CUSmenuController - Customer Menu screen button for deleting selected customer
    public static int deleteCustomer(int customer_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("DELETE FROM customers WHERE Customer_ID=?");
        stmt.setInt(1, customer_id);
        int rowCount = stmt.executeUpdate();
        JDBC.disconnect();
        return rowCount;
    }

    // APTmodController - Auto selects original appointment's customer object using customer id.
    public static Customer getCustomer(int customer_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM customers JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID WHERE customers.Customer_ID = ?");
        stmt.setInt(1, customer_id);
        ResultSet result = stmt.executeQuery();

        result.next();
        int id = result.getInt("Customer_ID");
        String name = result.getString("Customer_Name");
        String address = result.getString("Address");
        String postal = result.getString("Postal_Code");
        String phone = result.getString("Phone");
        int country = result.getInt("Country_ID");
        int division = result.getInt("Division_ID");

        JDBC.disconnect();
        return new Customer(id, name, address, postal, phone, country, division);
    }

    // CUSmenuController - Fill tableview with allCustomers objects
    // APTaddController, APTmodController - Populate customer id combo box with customer objects
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ResultSet result = JDBC.exQuery("SELECT * FROM customers JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID");

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        while (result.next()) {
            int id = result.getInt("Customer_ID");
            String name = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postal = result.getString("Postal_Code");
            String phone = result.getString("Phone");
            int country = result.getInt("Country_ID");
            int division = result.getInt("Division_ID");
            allCustomers.add(new Customer(id, name, address, postal, phone, country, division));
        }

        JDBC.disconnect();
        return allCustomers;
    }
}
