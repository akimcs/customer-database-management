package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;

import java.sql.*;
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

    // APTmodController - Updates appointment object in database
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
        PreparedStatement stmt = JDBC.pStatement("DELETE FROM appointments WHERE Customer_ID=?");
        stmt.setInt(1, customer_id);
        int rowCount = stmt.executeUpdate();
        JDBC.disconnect();
        return 1;
    }

    // APTmenuController - Populate Tableview
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ResultSet result = JDBC.exQuery("SELECT * FROM appointments");

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

    // menuController - Fill Type combo box for report A
    public static ObservableList<String> getAllDistinctTypes() throws SQLException {
        ResultSet result = JDBC.exQuery("SELECT DISTINCT Type FROM appointments");

        ObservableList<String> allTypes = FXCollections.observableArrayList();
        while (result.next()) {
            String type = result.getString("Type");
            allTypes.add(type);
        }

        JDBC.disconnect();
        return allTypes;
    }

    // menuController - Returns number of specified appointments for Report A
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

    // menuController - Gets all appointments for specified contact
    public static ObservableList<Appointment> getContactAppointments(int contact_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM appointments WHERE Contact_ID=?");
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

    // menuController - Returns a List of all unique ID numbers of a given ID type
    public static ObservableList<Integer> getIdNumbers(String id_type) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT DISTINCT ? FROM appointments");
        stmt.setString(1, id_type);
        ResultSet result = stmt.executeQuery();

        ObservableList<Integer> allIdNumbers = FXCollections.observableArrayList();
        while (result.next()) {
            int id = result.getInt(id_type);
            allIdNumbers.add(id);
        }

        JDBC.disconnect();
        return allIdNumbers;
    }

    public static String getCustomIdName(String idType, int idNum) throws SQLException {
        String idTypeFormat = idType.substring(0, idType.length() - 3);
        String tableName = idTypeFormat.toLowerCase() + "s";
        String idColumnName = idTypeFormat + "_ID";
        String nameColumnName = idTypeFormat + "_Name";

        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM ? WHERE ? = ?");
        stmt.setString(1, tableName);
        stmt.setString(2, idColumnName);
        stmt.setInt(3, idNum);
        ResultSet result = stmt.executeQuery();

        result.next();
        String name = result.getString(nameColumnName);

        JDBC.disconnect();
        return name;
    }

    // menuController - Returns the number of appointments associated with the id type's id number
    public static Integer getNumberOfCustomAppointments(String id_type, int id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM appointments WHERE ? = ?");
        stmt.setString(1, id_type);
        stmt.setInt(2, id);
        ResultSet result = stmt.executeQuery();

        int counter = 0;
        while (result.next()) {
            counter++;
        }

        JDBC.disconnect();
        return counter;
    }

    // menucontroller - Returns next soonest appointment within 15 minutes, null otherwise
    public static Appointment getAlertAppointment(int user_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM appointments WHERE User_ID=? AND NOW() <= Start AND Start <= DATE_ADD(NOW(), INTERVAL 15 MINUTE) ORDER BY Start ASC LIMIT 1");
        stmt.setInt(1, user_id);
        ResultSet result = stmt.executeQuery();

        Appointment nextAppointment = null;
        if (result.next()) {
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
            nextAppointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId);
        }

        JDBC.disconnect();
        return nextAppointment;
    }
}
