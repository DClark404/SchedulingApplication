package SchedulingApplication.util;

import SchedulingApplication.DAO.AppointmentDAO;
import SchedulingApplication.DAO.CustomerDAO;
import SchedulingApplication.Model.Appointment;
import SchedulingApplication.Model.Customer;
import SchedulingApplication.Model.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Utility class that handles functions related to information reporting.
 * Each method is associated with a different reporting feature of the application and the class itself does not
 * utilize any attributes.
 * */
public class ReportUtility {

    /** Creates a list of reports calculating the total number of each type of appointment in each month.
     * Must generate a list of report types due to the user being able to enter any string to describe a report type,
     * and a unique identifier for each month and appointmentType pair.
     * @return      an observableList of Report objects
     * */
    public static ObservableList<Report> totalByTypeAndMonth() {
        ObservableList<Report> reportList = FXCollections.observableArrayList();
        List<String> identifierList = new ArrayList<>();

        for (Appointment item : AppointmentDAO.getAppointmentList()) {
            String monthName = item.getStartDateTime().toLocalDateTime().getMonth().toString();
            String identifier = monthName + item.getType();

            if (!identifierList.contains(identifier)) {
                identifierList.add(identifier);
                Report newReport = new Report();
                newReport.setReportID(identifier);
                newReport.setReportMonth(monthName);
                newReport.setReportType(item.getType());
                reportList.add(newReport);
            }
        }

        for (Appointment item : AppointmentDAO.getAppointmentList()) {
            String monthName = item.getStartDateTime().toLocalDateTime().getMonth().toString();
            String identifier = monthName + item.getType();

            for (Report report : reportList) {
                if (report.getReportID().equals(identifier)) {
                    int counter = report.getReportCount() + 1;
                    report.setReportCount(counter);
                }
            }
        }

        return reportList;
    }

    /** Generates a filtered list of appointments based on the given parameter.
     * A lambda method ".filtered" is used here to filter an ObservableList object into a FilteredList object
     * that only contains the appointments associated with the given parameter.
     * The use of this lambda expression not only increases the readability of the code, but improves performance by
     * eliminating the need for extra temporary variables that scale with input size and increasing memory overhead.
     * @param contactID the unique identifier used to sort the list
     * @return      the FilteredList object containing the sorted appointments
     * */
    public static FilteredList<Appointment> getSortedAppointments(int contactID) {
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointmentList();

        return allAppointments.filtered(appointment ->
                appointment.getContactID() == contactID);
    }

    /** Generates an observableList of reports associating customer data with the average length of their appointments.
     * Creates a report object for each customer in the database using their unique ID and name, then populates the
     * reports with the average length of all appointments associated with that customer ID.
     * @return      an observableList of Report objects
     * */
    public static ObservableList<Report> getAverageAppointmentLengths() {
        ObservableList<Report> reportList = FXCollections.observableArrayList();

        for(Customer item : CustomerDAO.getCustomerList()) {
            Report newReport = new Report();
            newReport.setReportCount(item.getCustomerID());
            newReport.setReportID(item.getCustomerName());
            reportList.add(newReport);
        }

        for (Report report : reportList) {
            long totalTime = 0;
            int reportCounter = 0;
            for (Appointment item : AppointmentDAO.getAppointmentList()) {
                if (report.getReportCount() == item.getCustomerID()) {
                    LocalDateTime startTime = item.getStartDateTime().toLocalDateTime();
                    LocalDateTime endTime = item.getEndDateTime().toLocalDateTime();
                    totalTime = totalTime + Duration.between(startTime, endTime).toMinutes();
                    reportCounter = reportCounter + 1;
                }
            }

            if (reportCounter != 0) {
                report.setReportDuration(totalTime/reportCounter);
            } else {
                report.setReportDuration(0);
            }
        }
        return reportList;
    }
}
