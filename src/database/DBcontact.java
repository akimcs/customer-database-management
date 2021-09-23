package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import static database.JDBC.conn;

public class DBcontact {

    // DONE
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        JDBC.connect();

        String query = "SELECT * FROM contacts";
        ResultSet result = conn.createStatement().executeQuery(query);

        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        while (result.next()) {
            int id = result.getInt("Contact_ID");
            String name = result.getString("Contact_Name");
            String email = result.getString("Email");

            allContacts.add(new Contact(id, name, email));
        }

        JDBC.disconnect();
        return allContacts;
    }
}
