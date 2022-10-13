package SchedulingApplication.util;

import SchedulingApplication.DAO.AppointmentDAO;
import SchedulingApplication.Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Timestamp;
import java.time.*;
import java.util.TimeZone;

/** Utility class that handles functions related to Time objects and timezones. */
public class TimeUtility {

    /** Contains a list of localtime objects representing all 10 minute intervals of the day.
     * This is used for gathering user input for adding and updating appointments. */
    private static final ObservableList<LocalTime> possibleTimes = FXCollections.observableArrayList();

    /** Populates the possibleTimes observableList attribute with generated LocalTime objects. */
    public static void populateTimeList () {
        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(23, 50);
        while (start.isBefore(end)) {
            possibleTimes.add(start);
            start = start.plusMinutes(10);
        }
    }

    /** Gets the class attribute for use within the program.
     * @return      a reference to the possibleTimes attribute
     * */
    public static ObservableList<LocalTime> getPossibleTimes() {
        return possibleTimes;
    }

    /** Generates a LocalDateTime object at the time of the methods execution in the users local time.
     * @return      a timestamp object
     * */
    public static Timestamp getCurrentTimestamp () {
        LocalDateTime currentDateTime = java.time.LocalDateTime.now();
        return Timestamp.valueOf(currentDateTime);
    }

    /** Checks to see if two given LocalTime objects are within a range of time in another time zone.
     * Creates ZonedDateTime objects representing the operating hours of a business, then creates ZonedDateTime objects
     * representing the input parameters in system local time. The objects created from the input parameters are then
     * converted to instances within the businesses time zone and compared to the businesses operating hours to check
     * if they are valid.
     * @param start the starting time of an appointment as a LocalTime object
     * @param end the ending time of an appointment as a LocalTime object
     * @return      boolean value representing appointment validity
     * */
    public static boolean withinBusinessHours(LocalTime start, LocalTime end) {

        boolean invalid = false;
        ZoneId businessZoneID = ZoneId.of("US/Eastern");
        ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());
        LocalDate today = LocalDate.now();

        ZonedDateTime businessOpen = ZonedDateTime.of(today, LocalTime.of(8,0), businessZoneID);
        ZonedDateTime businessClose = ZonedDateTime.of(today, LocalTime.of(22,0), businessZoneID);

        ZonedDateTime startZoned = ZonedDateTime.of(today, start, localZone);
        ZonedDateTime endZoned = ZonedDateTime.of(today, end, localZone);
        startZoned.toInstant().atZone(businessZoneID);
        endZoned.toInstant().atZone(businessZoneID);

        if (startZoned.isBefore(businessOpen) || startZoned.isAfter(businessClose)) {
            invalid = true;
        }

        if (endZoned.isBefore(businessOpen) || endZoned.isAfter(businessClose)) {
            invalid = true;
        }

        return invalid;
    }

    /** Checks to see if the hours of a new scheduled appointment overlap with existing appointments for a given customer.
     * First generates a list of all appointments associated with a given customer ID, then compares the times of each
     * appointment with the localTime objects passed as parameters. The appointmentID parameter is used to avoid adding
     * an appointment that is currently being edited.
     * @param start the starting time of an appointment
     * @param end the ending time of an appointment
     * @param customerID the ID of customer appointments to check
     * @param appointmentID the ID of an appointment being edited
     * @return      boolean value representing appointment validity
     * */
    public static boolean detectTimeOverlap(LocalDateTime start, LocalDateTime end, int customerID, int appointmentID) {

        boolean invalid = false;
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
        contactAppointments.clear();

        for (Appointment item : AppointmentDAO.getAppointmentList()) {
            if (item.getCustomerID() == customerID && item.getAppointmentID() != appointmentID) {
                contactAppointments.add(item);
            }
        }

        for (Appointment item : contactAppointments) {
            LocalDateTime itemStart = item.getStartDateTime().toLocalDateTime();
            LocalDateTime itemEnd = item.getEndDateTime().toLocalDateTime();

            if (start.isAfter(itemStart) && start.isBefore(itemEnd)) {
                invalid = true;
            }

            if (end.isAfter(itemStart) && end.isBefore(itemEnd)) {
                invalid = true;
            }

            if (itemStart.isEqual(start)) {
                invalid = true;
            }

            if (start.isBefore(itemStart) && end.isAfter(itemEnd)) {
                invalid = true;
            }
        }
        return invalid;
    }

    /** Searches appointment list for appointments scheduled to begin within 15 minutes of the user logging in and
     * displays an alert.
     * The alert will show all appointments for a given user that are scheduled to begin within 15 minutes of the method
     * execution, or a default alert message if none exist.
     * @param userID the ID of the currently logged in user
     * */
    public static void detectUpcomingAppointments(int userID) {

        boolean appointmentNotDetected = true;
        LocalDateTime currentTime = LocalDateTime.now();

        for (Appointment item : AppointmentDAO.getAppointmentList()) {
            LocalDateTime itemEnd = item.getStartDateTime().toLocalDateTime();
            LocalDateTime itemStart = itemEnd.minusMinutes(15);

            if (currentTime.isAfter(itemStart) && currentTime.isBefore(itemEnd) && item.getUserID() == userID) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("Upcoming appointment detected!");
                alert.setContentText("Appointment ID: " + item.getAppointmentID() + "\nFrom: " +
                        item.getStartDateTime().toString() + "\nTo: " + item.getEndDateTime().toString());
                alert.showAndWait();
                appointmentNotDetected = false;
            }
        }

        if (appointmentNotDetected) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("There are no upcoming appointments.");
            alert.showAndWait();
        }
    }
}
