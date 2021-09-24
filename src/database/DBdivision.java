package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBdivision {

    // CUSmodController - Auto select division object using original customer's division id to find division
    public static Division getDivision(int division_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM first_level_divisions WHERE Division_ID = ?");
        stmt.setInt(1, division_id);
        ResultSet result = stmt.executeQuery();

        result.next();
        int id = result.getInt("Division_ID");
        String name = result.getString("Division");
        String countryId = result.getString("Country_Id");

        JDBC.disconnect();
        return new Division(id, name, countryId);
    }

    // CUSmodController - Auto populate division combo box choices using original customer's country id
    // CUSmodController, CUSaddController - Autopopulate division combo box after selecting a country
    public static ObservableList<Division> getAllDivisions(int country_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM first_level_divisions WHERE Country_ID = ?");
        stmt.setInt(1, country_id);;
        ResultSet result = stmt.executeQuery();

        ObservableList<Division> allDivisions = FXCollections.observableArrayList();
        while (result.next()) {
            int id = result.getInt("Division_ID");
            String name = result.getString("Division");
            String countryId = result.getString("Country_Id");
            allDivisions.add(new Division(id, name, countryId));
        }

        JDBC.disconnect();
        return allDivisions;
    }
}
