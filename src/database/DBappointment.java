package database;

import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;

public class DBappointment {

    public static int nextAppointmentId() throws SQLException {
        ResultSet result = JDBC.exQuery("SELECT max(Appointment_ID) + 1 as Appointment_ID FROM appointments");
        result.next();
        int id = result.getInt("Appointment_ID");

        JDBC.disconnect();
        return id;
    }

    public static void addAppointment(Appointment appointment) throws SQLException {
        // TODO
        PreparedStatement stmt = JDBC.pStatement("");
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

    public static void deleteAllCustomerAppointments(int customer_id) throws SQLException {
        // TODO
    }

    public static ObservableList<Appointment> getAllAppointments() {
        // TODO
    }

    public static ObservableList<String> getAllTypes() {
        // TODO
    }

    public static Integer getMonthTypeAppointments(Month month, String type) {
        // TODO
    }

    public static ObservableList<Appointment> getContactAppointments(int contact_id) {
        // TODO
    }

    public static ObservableList<Integer> getIdNumbers(String id_type) {
        // TODO
    }

    public static Integer getNumberOfCustomAppointments(int id) {
        // TODO
    }

    public static Appointment getAlertAppointment(int user_id, LocalDateTime loginTime) {
        // TODO
    }
}
