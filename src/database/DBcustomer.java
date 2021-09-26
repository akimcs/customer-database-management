package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Handles all database calls that access the customers table.*/
public class DBcustomer {

    /**Generates the next highest customer ID to use/
     * @return int of next available customer id
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static int nextCustomerId() throws SQLException {
        ResultSet result = JDBC.exQuery("SELECT max(Customer_ID) + 1 as Customer_ID FROM customers");

        result.next();
        int id = result.getInt("Customer_ID");

        JDBC.disconnect();
        return id;
    }

    /**Adds customer object to database table customers.
     * @param customer customer object to add to database
     * @return number of rows affected.
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static int addCustomer(Customer customer) throws SQLException{
        PreparedStatement stmt = JDBC.pStatement("INSERT INTO customers VALUES (?, ?, ?, ?, ?, NULL, NULL, NULL, NULL, ?)");
        stmt.setInt(1, customer.getId());
        stmt.setString(2, customer.getName());
        stmt.setString(3, customer.getAddress());
        stmt.setString(4, customer.getPostal());
        stmt.setString(5, customer.getPhone());
        stmt.setInt(6, customer.getDivisionId());
        int rowCount = stmt.executeUpdate();
        JDBC.disconnect();
        return rowCount;
    }

    /**Updates customer object in database
     * @param customer customer object to replace existing one
     * @return number of rows affected.
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static int modifyCustomer(Customer customer) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_Id=?");
        stmt.setString(1, customer.getName());
        stmt.setString(2, customer.getAddress());
        stmt.setString(3, customer.getPostal());
        stmt.setString(4, customer.getPhone());
        stmt.setInt(5, customer.getDivisionId());
        stmt.setInt(6, customer.getId());
        int rowCount = stmt.executeUpdate();
        JDBC.disconnect();
        return rowCount;
    }

    /**Customer Menu screen button for deleting selected customer
     * @param customer_id the customer id of customer to delete.
     * @return number of rows affected.
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static int deleteCustomer(int customer_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("DELETE FROM customers WHERE Customer_ID=?");
        stmt.setInt(1, customer_id);
        int rowCount = stmt.executeUpdate();
        JDBC.disconnect();
        return rowCount;
    }

    /**Auto selects original appointment's customer object using customer id.
     * @param customer_id  the customer id of customer requested
     * @return Customer object of requested customer
     * @throws SQLException Calls SQL Database Statements.
     * */
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

    /**Fill tableview with allCustomers objects and Populate customer id combo box with customer objects.
     * @return a list of all customers in database.
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ResultSet result = JDBC.exQuery("SELECT * FROM customers JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID ORDER BY Customer_ID");

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

    /** Obtains all customers in a specified country.
     * @param country_id the chosen country id of country
     * @return a list of all customers in given country
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static ObservableList<Customer> getAllCountryCustomers(int country_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM customers JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID WHERE countries.Country_ID=?");
        stmt.setInt(1, country_id);
        ResultSet result = stmt.executeQuery();

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
