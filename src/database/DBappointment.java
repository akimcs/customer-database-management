package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;

/**Handles all database calls that access the appointments table.*/
public class DBappointment {

    /** display/create next highest available appointment id.
     * @return id next highest appointment id
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static int nextAppointmentId() throws SQLException {
        ResultSet result = JDBC.exQuery("SELECT max(Appointment_ID) + 1 as Appointment_ID FROM appointments");

        result.next();
        int id = result.getInt("Appointment_ID");

        JDBC.disconnect();
        return id;
    }

    /**Adds appointment object to database table appointments.
     * @param appointment The appointment to add to database.
     * @return number of rows affected.
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static int addAppointment(Appointment appointment) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("INSERT INTO appointments VALUES (?, ?, ?, ?, ?, ?, ?, NULL, NULL, NULL, NULL, ?, ?, ?)");
        stmt.setInt(1, appointment.getId());
        stmt.setString(2, appointment.getTitle());
        stmt.setString(3, appointment.getDescription());
        stmt.setString(4, appointment.getLocation());
        stmt.setString(5, appointment.getType());
        stmt.setTimestamp(6, Timestamp.valueOf(appointment.getStart()));
        stmt.setTimestamp(7, Timestamp.valueOf(appointment.getEnd()));
        stmt.setInt(8, appointment.getCustomerId());
        stmt.setInt(9, appointment.getUserId());
        stmt.setInt(10, appointment.getContactId());
        int rowCount = stmt.executeUpdate();
        JDBC.disconnect();
        return rowCount;
    }

    /** Updates appointment object in database
     * @param appointment the new appointment to replace the old
     * @return number of rows affected.
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static int modifyAppointment(Appointment appointment) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?");
        stmt.setString(1, appointment.getTitle());
        stmt.setString(2, appointment.getDescription());
        stmt.setString(3, appointment.getLocation());
        stmt.setString(4, appointment.getType());
        stmt.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
        stmt.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
        stmt.setInt(7, appointment.getCustomerId());
        stmt.setInt(8, appointment.getUserId());
        stmt.setInt(9, appointment.getContactId());
        stmt.setInt(10, appointment.getId());
        int rowCount = stmt.executeUpdate();
        JDBC.disconnect();
        return rowCount;
    }

    /**Appointment Menu screen button for deleting selected appointment.
     * @return number of rows affected.
     * @param appointment_id appointment id of appointment to delete
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static int deleteAppointment(int appointment_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("DELETE FROM appointments WHERE Appointment_ID=?");
        stmt.setInt(1, appointment_id);
        int rowCount = stmt.executeUpdate();
        JDBC.disconnect();
        return rowCount;
    }

    /**Appointment menu screen button for deleting all appointments associated with given customer.
     * @return number of rows affected.
     * @param customer_id customer id of of customer appointments to delete
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static int deleteAllCustomerAppointments(int customer_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("DELETE FROM appointments WHERE Customer_ID=?");
        stmt.setInt(1, customer_id);
        int rowCount = stmt.executeUpdate();
        JDBC.disconnect();
        return rowCount;
    }

    /** Populate Tableview with all appointments.
     * @return list of all appointments from database
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ResultSet result = JDBC.exQuery("SELECT * FROM appointments ORDER BY Appointment_ID");

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        while (result.next()) {
            int id = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String location = result.getString("Location");
            String type = result.getString("Type");
            LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
            int customerId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");
            allAppointments.add(new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId));
        }

        JDBC.disconnect();
        return allAppointments;
    }

    /**Fill Type combo box for report A
     * @return a list of all types
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static ObservableList<String> getAllDistinctTypes() throws SQLException {
        ResultSet result = JDBC.exQuery("SELECT DISTINCT Type FROM appointments ORDER BY Type");

        ObservableList<String> allTypes = FXCollections.observableArrayList();
        while (result.next()) {
            String type = result.getString("Type");
            allTypes.add(type);
        }

        JDBC.disconnect();
        return allTypes;
    }

    /**Returns number of specified appointments for Report A.
     * @param month the selected month from report A.
     * @param type the selected type from report A.
     * @return an int for the number of appointments in report A
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static Integer getMonthTypeAppointments(Month month, String type) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM appointments WHERE MONTH(Start)=? AND Type=?");
        stmt.setInt(1, month.getValue());
        stmt.setString(2, type);
        ResultSet result = stmt.executeQuery();

        int count = 0;
        while (result.next()) {
            count++;
        }

        JDBC.disconnect();
        return count;
    }

    /**Gets all appointments for specified contact.
     * @param contact_id the contact id of contact
     * @return a list of all appointments associated with contact.
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static ObservableList<Appointment> getContactAppointments(int contact_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM appointments WHERE Contact_ID=? ORDER BY Appointment_ID");
        stmt.setInt(1, contact_id);
        ResultSet result = stmt.executeQuery();

        ObservableList<Appointment> allContactAppointments = FXCollections.observableArrayList();
        while (result.next()) {
            int id = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String location = result.getString("Location");
            String type = result.getString("Type");
            LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
            int customerId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");
            allContactAppointments.add(new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId));
        }

        JDBC.disconnect();
        return allContactAppointments;
    }

    /**Returns next soonest appointment within 15 minutes, null otherwise
     * @param user_id the logged in user for alert detection.
     * @return a list of all appointments within 15 minutes
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static ObservableList<Appointment> getAlertAppointments(int user_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM appointments WHERE User_ID=? AND Start >= ? AND Start <= DATE_ADD(?, INTERVAL 15 MINUTE) ORDER BY Start ASC");
        stmt.setInt(1, user_id);
        stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
        stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        ResultSet result = stmt.executeQuery();

        ObservableList<Appointment> allUpcomingAppointments = FXCollections.observableArrayList();
        while (result.next()) {
            int id = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String location = result.getString("Location");
            String type = result.getString("Type");
            LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
            int customerId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");
            allUpcomingAppointments.add(new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId));
        }

        JDBC.disconnect();
        return allUpcomingAppointments;
    }

    /**Scans database for given customer's time conflicting appointments
     * @param cus_Id the customer id of appointment
     * @param app_Id the appointment id of appointment
     * @param startTime the start time of appointment
     * @param endTime the end time of appointment
     * @return true if suggested appointment overlaps with existing ones
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static boolean conflictExists(int cus_Id, int app_Id, LocalDateTime startTime, LocalDateTime endTime) throws SQLException {
        Timestamp start = Timestamp.valueOf(startTime);
        Timestamp end = Timestamp.valueOf(endTime);
        String c1 = "(? >= Start AND ? < End)";
        String c2 = "(? > Start AND ? <= End)";
        String c3 = "(? <= Start AND ? >= End)";
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM appointments WHERE Customer_ID=? AND Appointment_ID<>? AND (" + c1 + " OR " + c2 + " OR " + c3 + ")");
        stmt.setInt(1, cus_Id);
        stmt.setInt(2, app_Id);
        stmt.setTimestamp(3, start);
        stmt.setTimestamp(4, start);
        stmt.setTimestamp(5, end);
        stmt.setTimestamp(6, end);
        stmt.setTimestamp(7, start);
        stmt.setTimestamp(8, end);
        ResultSet result = stmt.executeQuery();

        boolean conflictingAppointmentDetected = result.next();
        JDBC.disconnect();
        return conflictingAppointmentDetected;
    }
}
