package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Handles all database calls that access the divisions table.*/
public class DBdivision {

    /**Auto select division object using original customer's division id to find division.
     * @param division_id the division id of requested division
     * @return the division object
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static Division getDivision(int division_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM first_level_divisions WHERE Division_ID = ?");
        stmt.setInt(1, division_id);
        ResultSet result = stmt.executeQuery();

        result.next();
        int id = result.getInt("Division_ID");
        String name = result.getString("Division");

        JDBC.disconnect();
        return new Division(id, name);
    }

    /**Auto populate division combo box choices using original customer's country id and division combo box after selecting a country.
     * @param country_id the country id of preselected country
     * @return a list of divisions based on given country.
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static ObservableList<Division> getAllDivisions(int country_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM first_level_divisions WHERE Country_ID = ? ORDER BY Division_ID");
        stmt.setInt(1, country_id);
        ResultSet result = stmt.executeQuery();

        ObservableList<Division> allDivisions = FXCollections.observableArrayList();
        while (result.next()) {
            int id = result.getInt("Division_ID");
            String name = result.getString("Division");
            allDivisions.add(new Division(id, name));
        }

        JDBC.disconnect();
        return allDivisions;
    }
}
