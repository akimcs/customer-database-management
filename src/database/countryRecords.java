package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

public class countryRecords {

    public static Country getCountry(int country_id) throws SQLException {
        JDBC.connect();

        Query.makeQuery("SELECT * FROM countries WHERE Country_Id = " + country_id);
        ResultSet result = Query.getResult();
        result.next();

        int id = result.getInt("Country_ID");
        String name = result.getString("Country");

        JDBC.disconnect();
        return new Country(id, name);
    }

    public static ObservableList<Country> getAllCountries() throws SQLException {
        JDBC.connect();

        Query.makeQuery("SELECT * FROM countries");
        ResultSet result = Query.getResult();

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
