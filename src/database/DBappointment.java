package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import static database.JDBC.conn;


public class DBappointment {

    // DONE
    public static int nextAppointmentId() throws SQLException {
        JDBC.connect();

        String query = "SELECT max(Appointment_ID) + 1 as Appointment_ID FROM appointments";
        ResultSet result = conn.createStatement().executeQuery(query);
        result.next();
        int id = result.getInt("Appointment_ID");

        JDBC.disconnect();
        return id;
    }


}
