package database;

import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;

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

    public static void addAppointment(Appointment appointment) throws SQLException {
        // TODO
    }

    public static void modifyAppointment(Appointment updatedAppointment) throws SQLException {
        // TODO
    }

    public static void deleteAppointment(int appointment_id) throws SQLException {
        // TODO
    }

    public static void deleteAppointments(int customer_id) throws SQLException {
        // TODO
    }

    public static ObservableList<Appointment> getAllAppointments() {
        // TODO
    }

    public static ObservableList<String> getAllTypes() {
        // TODO
    }

    public static Integer getMonthTypeAppointments(Month month, String type) {

    }

    public static ObservableList<Appointment> getContactAppointments(int contact_id) {

    }


}
