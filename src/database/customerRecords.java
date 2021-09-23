package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class customerRecords {

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        JDBC.connect();

        Query.makeQuery("SELECT cus.Customer_ID, cus.Customer_Name, cus.Address, cus.Postal_Code, cus.Phone, ctry.Country, dvn.Division FROM customers AS cus JOIN first_level_divisions AS dvn ON cus.Division_ID = dvn.Division_ID JOIN countries AS ctry ON dvn.Country_ID = ctry.Country_ID");
        ResultSet result = Query.getResult();

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        while (result.next()) {
            int id = result.getInt("Customer_ID");
            String name = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postal = result.getString("Postal_Code");
            String phone = result.getString("Phone");
            String country = result.getString("Country");
            String division = result.getString("Division");
            Customer customer = new Customer(id, name, address, postal, phone, country, division);
            allCustomers.add(customer);
        }

        JDBC.disconnect();
        return allCustomers;
    }
}
