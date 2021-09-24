package database;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;

public class DBappointment {

    // APTaddController - display/create next highest available appointment id
    public static int nextAppointmentId() throws SQLException {
        ResultSet result = JDBC.exQuery("SELECT max(Appointment_ID) + 1 as Appointment_ID FROM appointments");

        result.next();
        int id = result.getInt("Appointment_ID");

        JDBC.disconnect();
        return id;
    }

    // APTaddController - Adds appointment object to database table appointments
    public static int addAppointment(Appointment appointment) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("INSERT INTO appointments VALUES (?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)");
        stmt.setInt(1, appointment.getId());
        stmt.setString(2, appointment.getTitle());
        stmt.setString(3, appointment.getDescription());
        stmt.setString(4, appointment.getLocation());
        stmt.setString(5, appointment.getType());
        stmt.setTimestamp(6, Timestamp.valueOf(appointment.getStart()));
        stmt.setTimestamp(7, Timestamp.valueOf(appointment.getEnd()));
        stmt.setString(8, User.getCurrentUserName());
        stmt.setString(9, User.getCurrentUserName());
        stmt.setInt(10, appointment.getCustomerId());
        stmt.setInt(11, appointment.getUserId());
        stmt.setInt(12, appointment.getContactId());
        int rowCount = stmt.executeUpdate();
        JDBC.disconnect();
        return rowCount;
    }

    // APTmodController - Updates appointment object in database
    public static int modifyAppointment(Appointment appointment) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Last_Update=NOW(), Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?");
        stmt.setString(1, appointment.getTitle());
        stmt.setString(2, appointment.getDescription());
        stmt.setString(3, appointment.getLocation());
        stmt.setString(4, appointment.getType());
        stmt.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
        stmt.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
        stmt.setString(7, User.getCurrentUserName());
        stmt.setInt(8, appointment.getCustomerId());
        stmt.setInt(9, appointment.getUserId());
        stmt.setInt(10, appointment.getContactId());
        stmt.setInt(11, appointment.getId());
        int rowCount = stmt.executeUpdate();
        JDBC.disconnect();
        return rowCount;
    }

    // APTmenuController - Appointment Menu screen button for deleting selected appointment
    public static int deleteAppointment(int appointment_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("DELETE FROM appointments WHERE Appointment_ID=?");
        stmt.setInt(1, appointment_id);
        int rowCount = stmt.executeUpdate();
        JDBC.disconnect();
        return rowCount;
    }

    // CUSmenuController - Appointment menu screen button for deleting all appointments associated with given customer.
    public static int deleteAllCustomerAppointments(int customer_id) throws SQLException {
        // TODO
        PreparedStatement stmt = JDBC.pStatement("DELETE FROM appointments WHERE Customer_ID=?");
        stmt.setInt(1, customer_id);
        int rowCount = stmt.executeUpdate();
        JDBC.disconnect();
        return rowCount;
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
