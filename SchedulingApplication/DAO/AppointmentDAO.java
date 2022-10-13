package SchedulingApplication.DAO;

import SchedulingApplication.Model.Appointment;
import SchedulingApplication.util.DBConnect;
import SchedulingApplication.util.TimeUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/** Data Access Object class that manages server queries for the Appointment data type.
 * Populates and contains a list that is analogous to a table in the database. Capable of making changes to the
 * database via CREATE, UPDATE, and DELETE SQL queries.
 * */
public class AppointmentDAO {

    /** Contains a list of Appointment objects that is analogous to the appointments database table. */
    private static final ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    /** Appointment reference utilized to pass data between screens in FXML*/
    private static Appointment updateAppointment = null;

    /** Getter for the updateAppointment attribute.
     * @return      the appointment to be updated
     * */
    public static Appointment getUpdateAppointment() {
        return updateAppointment;
    }

    /** Sets the updateAppointment attribute.
     * @param updateAppointment the appointment to be updated
     * */
    public static void setUpdateAppointment(Appointment updateAppointment) {
        AppointmentDAO.updateAppointment = updateAppointment;
    }

    /** Getter for the appointmentList attribute.
     * @return      list of all appointments
     * */
    public static ObservableList<Appointment> getAppointmentList() {
        return appointmentList;
    }

    /** Populates the appointmentList attribute with appointment objects representing every row of data within
     * the appointment table of the database.
     * @throws SQLException if any database exception has occurred
     * */
    public static void populateAppointmentList () throws SQLException {
        DBConnect.setPreparedStatement("SELECT * FROM appointments, contacts WHERE appointments.Contact_ID = contacts.Contact_ID");
        DBConnect.getPreparedStatement().execute();
        ResultSet rs = DBConnect.getPreparedStatement().getResultSet();
        while (rs.next()) {

            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            Appointment newAppointment = new Appointment(appointmentID, title, description, location, type,
                    start, end, createdDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID,
                    contactID, contactName);
            appointmentList.add(newAppointment);
        }
    }

    /** Uses a collection of input parameters to generate a preparedStatement object that updates the database with
     * new appointment information. Adds the same appointment object to the appointmentList attribute so that the
     * application accurately reflects server data.
     * @param title title of the appointment
     * @param description a description of the appointment
     * @param location location of the appointment
     * @param type the type of appointment
     * @param start start date and time of the appointment
     * @param end end date and time of the appointment
     * @param customerID unique identifier for the customer
     * @param userID unique identifier for the user
     * @param contactID unique identifier for the contact
     * @param contactName name of the contact
     * @throws SQLException if any database exception has occurred
     * */
    public static void createAppointment (String title, String description, String location, String type,
                                          Timestamp start, Timestamp end, int customerID, int userID,
                                          int contactID, String contactName ) throws SQLException {

        Timestamp currentTime = TimeUtility.getCurrentTimestamp();
        String currentUser = "test";

        DBConnect.setPreparedStatement("INSERT INTO appointments(Title, Description, Location, Type, Start, End, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        DBConnect.getPreparedStatement().setString(1, title);
        DBConnect.getPreparedStatement().setString(2, description);
        DBConnect.getPreparedStatement().setString(3, location);
        DBConnect.getPreparedStatement().setString(4, type);
        DBConnect.getPreparedStatement().setTimestamp(5, start);
        DBConnect.getPreparedStatement().setTimestamp(6, end);
        DBConnect.getPreparedStatement().setTimestamp(7, currentTime);
        DBConnect.getPreparedStatement().setString(8, currentUser);
        DBConnect.getPreparedStatement().setTimestamp(9, currentTime);
        DBConnect.getPreparedStatement().setString(10, currentUser);
        DBConnect.getPreparedStatement().setInt(11, customerID);
        DBConnect.getPreparedStatement().setInt(12, userID);
        DBConnect.getPreparedStatement().setInt(13, contactID);
        DBConnect.getPreparedStatement().execute();
        int appointmentID = DBConnect.returnGeneratedKey();

        Appointment newAppointment = new Appointment(appointmentID, title, description, location, type,
                start, end, currentTime, currentUser, currentTime, currentUser, customerID, userID,
                contactID, contactName);
        appointmentList.add(newAppointment);
    }

    /** Uses a collection of input parameters to generate a preparedStatement object that updates existing appointment
     * information within the database. Updates the same appointment within the appointmentList attribute so that the
     * application accurately reflects server data.
     * @param appointmentID unique identifier for the appointment
     * @param title title of the appointment
     * @param description a description of the appointment
     * @param location location of the appointment
     * @param type the type of appointment
     * @param start start date and time of the appointment
     * @param end end date and time of the appointment
     * @param customerID unique identifier for the customer
     * @param userID unique identifier for the user
     * @param contactID unique identifier for the contact
     * @param contactName name of the contact
     * @throws SQLException if any database exception has occurred
     * */
    public static void updateAppointment (int appointmentID, String title, String description, String location, String type,
                                          Timestamp start, Timestamp end, int customerID, int userID,
                                          int contactID, String contactName) throws SQLException {

        Timestamp currentTime = TimeUtility.getCurrentTimestamp();
        String currentUser = "test";

        DBConnect.setPreparedStatement("UPDATE appointments set Title = ?, Description = ?, Location = ?, Type = ?, " +
                "Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, " +
                "Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?");
        DBConnect.getPreparedStatement().setString(1, title);
        DBConnect.getPreparedStatement().setString(2, description);
        DBConnect.getPreparedStatement().setString(3, location);
        DBConnect.getPreparedStatement().setString(4, type);
        DBConnect.getPreparedStatement().setTimestamp(5, start);
        DBConnect.getPreparedStatement().setTimestamp(6, end);
        DBConnect.getPreparedStatement().setTimestamp(7, currentTime);
        DBConnect.getPreparedStatement().setString(8, currentUser);
        DBConnect.getPreparedStatement().setTimestamp(9, currentTime);
        DBConnect.getPreparedStatement().setString(10, currentUser);
        DBConnect.getPreparedStatement().setInt(11, customerID);
        DBConnect.getPreparedStatement().setInt(12, userID);
        DBConnect.getPreparedStatement().setInt(13, contactID);
        DBConnect.getPreparedStatement().setInt(14, appointmentID);
        DBConnect.getPreparedStatement().execute();

        for (Appointment item : appointmentList) {
            if (item.getAppointmentID() == appointmentID) {
                item.setAppointmentTitle(title);
                item.setDescription(description);
                item.setLocation(location);
                item.setType(type);
                item.setStartDateTime(start);
                item.setEndDateTime(end);
                item.setCustomerID(customerID);
                item.setUserID(userID);
                item.setContactID(contactID);
                item.setContactName(contactName);
            }
        }
    }

    /** Uses a reference to the unique identifier of an appointment to generate a preparedStatement object that deletes
     * a matching appointment record from the database and from the appointmentList attribute.
     * A lambda method ".removeIf" is used here to search through the appointmentList and remove any entries that match
     * the given input parameter, in lieu of using a for loop with an internal if statement and ObservableList.Remove
     * method. the method removeIf not only presents in cleaner and more readable code, but takes significantly less
     * operations due to using an internal iterator and not having additional index shifts.
     * @param  appointmentID the unique identifier associated with an appointment
     * @throws SQLException if any database exception has occurred
     * */
    public static void deleteAppointment (int appointmentID) throws SQLException {
        DBConnect.setPreparedStatement("DELETE from appointments WHERE Appointment_ID = ?");
        DBConnect.getPreparedStatement().setInt(1, appointmentID);
        DBConnect.getPreparedStatement().execute();

        appointmentList.removeIf(appointment -> appointment.getAppointmentID() == appointmentID);
    }

}
