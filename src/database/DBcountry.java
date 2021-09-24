package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBcountry {

    // CUSmodController - Autoselect country in combo box using original customer's country id
    public static Country getCountry(int country_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM countries WHERE Country_Id = ?");
        stmt.setInt(1, country_id);
        ResultSet result = stmt.executeQuery();

        result.next();
        int id = result.getInt("Country_ID");
        String name = result.getString("Country");

        JDBC.disconnect();
        return new Country(id, name);
    }

    // CUSaddController, CUSmodController - Populate combo box with all countries
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ResultSet result = JDBC.exQuery("SELECT * FROM countries");

        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        while (result.next()) {
            int id = result.getInt("Country_ID");
            String name = result.getString("Country");
            allCountries.add(new Country(id, name));
        }

        JDBC.disconnect();
        return allCountries;
    }
}
