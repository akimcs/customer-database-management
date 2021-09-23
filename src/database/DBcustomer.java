package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBcustomer {

    // TODO - create / modify / delete (insert, update, delete)

    public static int nextCustomerId() throws SQLException {
        JDBC.connect();

        Query.makeQuery("SELECT max(Customer_ID) + 1 as Customer_ID FROM customers");
        ResultSet result = Query.getResult();
        result.next();
        int id = result.getInt("Customer_ID");

        JDBC.disconnect();
        return id;
    }

    public static void addCustomer(Customer customer) throws SQLException{
        JDBC.connect();

        String creator = User.sessionUser;
        Query.makeQuery("");
        // TODO - finish adding customer
    }

    public static Customer getCustomer(int cus_id) throws SQLException {
        JDBC.connect();

        Query.makeQuery("SELECT * FROM customers AS cus JOIN first_level_divisions AS dvn ON cus.Division_ID = dvn.Division_ID JOIN countries AS ctry ON dvn.Country_ID = ctry.Country_ID WHERE cus.Customer_ID = " + cus_id);
        ResultSet result = Query.getResult();
        result.next();

        int id = result.getInt("Customer_ID");
        String name = result.getString("Customer_Name");
        String address = result.getString("Address");
        String postal = result.getString("Postal_Code");
        String phone = result.getString("Phone");
        int division = result.getInt("Division_ID");

        JDBC.disconnect();
        return new Customer(id, name, address, postal, phone, division);
    }

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        JDBC.connect();

        Query.makeQuery("SELECT * FROM customers AS cus JOIN first_level_divisions AS dvn ON cus.Division_ID = dvn.Division_ID JOIN countries AS ctry ON dvn.Country_ID = ctry.Country_ID");
        ResultSet result = Query.getResult();

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        while (result.next()) {
            int id = result.getInt("Customer_ID");
            String name = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postal = result.getString("Postal_Code");
            String phone = result.getString("Phone");
            int division = result.getInt("Division_ID");

            allCustomers.add(new Customer(id, name, address, postal, phone, division));
        }

        JDBC.disconnect();
        return allCustomers;
    }
}
