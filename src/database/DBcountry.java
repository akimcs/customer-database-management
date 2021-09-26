package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Handles all database calls that access the countries table.*/
public class DBcountry {

    /**Autoselect country in combo box using original customer's country id
     * @param country_id the country id of requested country
     * @return teh country object requested
     * @throws SQLException Calls SQL Database Statements.
     * */
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

    /**Populate combo box with all countries
     * @return a list of all countries in the database
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ResultSet result = JDBC.exQuery("SELECT * FROM countries ORDER BY Country_ID");

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
