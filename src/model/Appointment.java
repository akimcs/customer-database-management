package model;

import java.time.LocalDateTime;

/**Describes the Appointment object.*/
public class Appointment {

    /** contains appointment id.*/
    private int id;
    /** contains appointment title.*/
    private String title;
    /** contains appointment description.*/
    private final String description;
    /** contains appointment location.*/
    private String location;
    /** contains appointment type.*/
    private String type;
    /** contains appointment start.*/
    private LocalDateTime start;
    /** contains appointment end.*/
    private LocalDateTime end;
    /** contains appointment customer id.*/
    private final int customerId;
    /** contains appointment user id.*/
    private final int userId;
    /** contains appointment contact id.*/
    private final int contactId;

    /**Constructor for appointment object.
     * @param id the id of the appointment.
     * @param title the title of the appointment.
     * @param description the description of the appointment.
     * @param location the location of the appointment.
     * @param type the type of the appointment.
     * @param start the start of the appointment.
     * @param end the end of the appointment.
     * @param customerId the customerId of the appointment.
     * @param userId the userId of the appointment.
     * @param contactId the contactId of the appointment.*/
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**Obtains the id of the appointment.
     * @return id the id of the appointment.*/
    public int getId() {
        return id;
    }

    /**Sets the id of the appointment.
     * @param id appointment id*/
    public void setId(int id) {
        this.id = id;
    }

    /**Obtains the title of the appointment.
     * @return id the title of the appointment.*/
    public String getTitle() {
        return title;
    }

    /**Sets the title of the appointment.
     * @param title appointment title*/
    public void setTitle(String title) {
        this.title = title;
    }

    /**Obtains the description of the appointment.
     * @return id the description of the appointment.*/
    public String getDescription() {
        return description;
    }

    /**Obtains the location of the appointment.
     * @return id the location of the appointment.*/
    public String getLocation() {
        return location;
    }

    /**Sets the location of the appointment.
     * @param location appointment location*/
    public void setLocation(String location) {
        this.location = location;
    }

    /**Obtains the type of the appointment.
     * @return id the type of the appointment.*/
    public String getType() {
        return type;
    }

    /**Sets the type of the appointment.
     * @param type appointment type*/
    public void setType(String type) {
        this.type = type;
    }

    /**Obtains the start of the appointment.
     * @return id the start of the appointment.*/
    public LocalDateTime getStart() {
        return start;
    }

    /**Sets the start of the appointment.
     * @param start appointment start*/
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**Obtains the end of the appointment.
     * @return id the end of the appointment.*/
    public LocalDateTime getEnd() {
        return end;
    }

    /**Sets the end of the appointment.
     * @param end appointment end*/
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**Obtains the customerId of the appointment.
     * @return id the customerId of the appointment.*/
    public int getCustomerId() {
        return customerId;
    }

    /**Obtains the userId of the appointment.
     * @return id the userId of the appointment.*/
    public int getUserId() {
        return userId;
    }

    /**Obtains the contactId of the appointment.
     * @return id the contactId of the appointment.*/
    public int getContactId() {
        return contactId;
    }

}
