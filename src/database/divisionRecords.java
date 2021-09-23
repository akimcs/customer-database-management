package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;
import java.sql.ResultSet;
import java.sql.SQLException;

public class divisionRecords {

    public static Division getDivision(int division_id) throws SQLException {
        JDBC.connect();

        Query.makeQuery("SELECT * FROM first_level_divisions WHERE Division_ID = " + division_id);
        ResultSet result = Query.getResult();
        result.next();

        int id = result.getInt("Division_ID");
        String name = result.getString("Division");
        String countryId = result.getString("Country_Id");

        JDBC.disconnect();
        return new Division(id, name, countryId);
    }

    public static ObservableList<Division> getAllDivisions() throws SQLException {
        JDBC.connect();

        Query.makeQuery("SELECT * FROM first_level_divisions");
        ResultSet result = Query.getResult();

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
