package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBcontact {

    // APTmodController - Auto select original appointment's contact object in combo box
    public static Contact getContact(int contact_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM contacts WHERE Contact_ID = ?");
        stmt.setInt(1, contact_id);
        ResultSet result = stmt.executeQuery();

        result.next();
        int id = result.getInt("Contact_ID");
        String name = result.getString("Contact_Name");
        String email = result.getString("Email");

        JDBC.disconnect();
        return new Contact(id, name, email);
    }

    // APTmodController, APTaddController, menuController - Autopopulate combo box with all contacts
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ResultSet result = JDBC.exQuery("SELECT * FROM contacts ORDER BY Contact_ID");

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
