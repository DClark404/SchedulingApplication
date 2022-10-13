package SchedulingApplication.Model;

import java.sql.Timestamp;

/** The class representing an appointment listing with the organization. */
public class Appointment {
    private int appointmentID;
    private String appointmentTitle;
    private String description;
    private String location;
    private String type;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;
    private String contactName;

    public Appointment(int appointmentID, String appointmentTitle, String description, String location, String type,
                       Timestamp startDateTime, Timestamp endDateTime, Timestamp createdDate, String createdBy,
                       Timestamp lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID,
                       String contactName) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contactName = contactName;

    }


    /** Setter for the appointmentID attribute.
     * @param appointmentID the unique ID of the appointment
     * */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /** Setter for the appointmentTitle attribute.
     * @param appointmentTitle the title of the appointment
     * */
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    /** Setter for the description attribute.
     * @param description a description of the appointment
     * */
    public void setDescription(String description) {
        this.description = description;
    }

    /** Setter for the location attribute.
     * @param location the location of the appointment
     * */
    public void setLocation(String location) {
        this.location = location;
    }

    /** Setter for the type attribute.
     * @param type the type of appointment
     * */
    public void setType(String type) {
        this.type = type;
    }

    /** Setter for the startDateTime attribute.
     * @param startDateTime the starting date and time of an appointment
     * */
    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    /** Setter for the endDateTime attribute.
     * @param endDateTime the end date and time of an appointment
     * */
    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    /** Setter for the createdDate attribute.
     * @param createdDate the date and time the entry is created
     * */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /** Setter for the createdBy attribute.
     * @param createdBy the user who created the appointment
     * */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** Setter for the lastUpdate attribute.
     * @param lastUpdate the last date and time the appointment was updated
     * */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** Setter for the lastUpdatedBy attribute.
     * @param lastUpdatedBy the user who last updated the appointment
     * */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** Setter for the customerID attribute.
     * @param customerID the unique ID of the customer
     * */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** Setter for the  userID attribute.
     * @param  userID the unique ID of the user
     * */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /** Setter for the contactID attribute.
     * @param contactID the unique ID of the contact
     * */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /** Setter for the contactName attribute.
     * @param contactName the name of the contact
     * */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** Getter for the appointmentID attribute.
     * @return      the unique ID of the appointment
     * */
    public int getAppointmentID() {
        return appointmentID;
    }

    /** Getter for the appointmentTitle attribute.
     * @return      the title of the appointment
     * */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    /** Getter for the description attribute.
     * @return      a description of the appointment
     * */
    public String getDescription() {
        return description;
    }

    /** Getter for the location attribute.
     * @return      the location of the appointment
     * */
    public String getLocation() {
        return location;
    }

    /** Getter for the type attribute.
     * @return      the type of appointment
     * */
    public String getType() {
        return type;
    }

    /** Getter for the startDateTime attribute.
     * @return      the start date and time of an appointment
     * */
    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    /** Getter for the endDateTime attribute.
     * @return      the end date and time of an appointment
     * */
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    /** Getter for the createdDate attribute.
     * @return      the date and time the appointment was created
     * */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /** Getter for the createdBy attribute.
     * @return      the user who created the appointment
     * */
    public String getCreatedBy() {
        return createdBy;
    }

    /** Getter for the lastUpdate attribute.
     * @return      the date and time the appointment was last updated
     * */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /** Getter for the lastUpdatedBy attribute.
     * @return      the user who last updated the appointment
     * */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** Getter for the customerID attribute.
     * @return      the unique ID of the customer
     * */
    public int getCustomerID() {
        return customerID;
    }

    /** Getter for the userID attribute.
     * @return      the unique ID of the user
     * */
    public int getUserID() {
        return userID;
    }

    /** Getter for the contactID attribute.
     * @return      the unique ID of the contact
     * */
    public int getContactID() {
        return contactID;
    }

    /** Getter for the contactName attribute.
     * @return      the name of the contact
     * */
    public String getContactName() {
        return contactName;
    }
}
