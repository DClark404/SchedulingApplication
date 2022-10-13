package SchedulingApplication.Controller;

import SchedulingApplication.DAO.AppointmentDAO;
import SchedulingApplication.Model.Appointment;
import SchedulingApplication.util.TimeUtility;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

/** The controller class for the calender screen. Contains FXML ID attributes and event handlers for every table and
 * button.
 * */
public class MainCalenderController {

    @FXML
    private TableView<Appointment> AppointmentsTableView;

    @FXML
    private TableColumn<Appointment, Integer> AppointmentIDColumn;

    @FXML
    private TableColumn<Appointment, String> AppointmentTitleColumn;

    @FXML
    private TableColumn<Appointment, String> AppointmentDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> AppointmentLocationColumn;

    @FXML
    private TableColumn<Appointment, String> AppointmentContactColumn;

    @FXML
    private TableColumn<Appointment, String> AppointmentTypeColumn;

    @FXML
    private TableColumn<Appointment, Timestamp> AppointmentStartColumn;

    @FXML
    private TableColumn<Appointment, Timestamp> AppointmentEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> AppointmentCustomerIDColumn;

    /** Initializes the calender tableview within the GUI and all of its columns to their appropriate values.
     * */
    @FXML
    public void initialize() {

        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointmentList();
        AppointmentsTableView.setItems(allAppointments);

        AppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        AppointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        AppointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        AppointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        AppointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        AppointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        AppointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        AppointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        AppointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

    }

    /** Resets the tableview to its initialized state of showing all appointments. */
    @FXML
    public void appAllRB() {
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointmentList();
        AppointmentsTableView.setItems(allAppointments);
    }

    /** Filters tableview to show only the appointments occurring during the current month. */
    @FXML
    public void appMonthlyRB() {
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointmentList();
        Timestamp currentTime = TimeUtility.getCurrentTimestamp();
        Month currentMonth = currentTime.toLocalDateTime().getMonth();

        FilteredList<Appointment> filteredMonthly = allAppointments.filtered(appointment ->
                appointment.getStartDateTime().toLocalDateTime().getMonth() == currentMonth);

        AppointmentsTableView.setItems(filteredMonthly);
    }

    /** Filters tableview to show only the appointments occuring during the current week.*/
    @FXML
    public void appWeeklyRB() {
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointmentList();

        LocalDateTime startDateTime = TimeUtility.getCurrentTimestamp().toLocalDateTime();
        DayOfWeek currentDay = startDateTime.getDayOfWeek();

        while (currentDay != DayOfWeek.MONDAY) {
            startDateTime = startDateTime.minusDays(1);
            currentDay = currentDay.minus(1);
        }

        LocalDateTime finalStartDateTime = startDateTime;
        LocalDateTime finalEndDateTime = finalStartDateTime.plusDays(7);

        FilteredList<Appointment> filteredWeekly = allAppointments.filtered(appointment ->
                appointment.getStartDateTime().toLocalDateTime().isAfter(finalStartDateTime) &&
                        appointment.getStartDateTime().toLocalDateTime().isBefore(finalEndDateTime));

        AppointmentsTableView.setItems(filteredWeekly);
    }

    /** Deletes an appointment that is selected from the table. On click shows a warning dialog box if no item is
     * selected. If an item is selected, displays a confirmation dialog box with the appointment id and type listed.
     * */
    @FXML
    public void deleteAppointment() {
        if (AppointmentsTableView.getSelectionModel().getSelectedItem() != null) {
            Appointment delAppointment = AppointmentsTableView.getSelectionModel().getSelectedItem();
            String deletingID = Integer.toString(delAppointment.getAppointmentID());
            String deletingType = delAppointment.getType();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Attempting to delete appointment ID #" + deletingID + " of type: " +
                    deletingType + ".");
            alert.setContentText("Are you sure you want to delete this appointment?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        AppointmentDAO.deleteAppointment(delAppointment.getAppointmentID());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Appointment Selected!");
            alert.setContentText("Please select an appointment from the calender.");
            alert.showAndWait();
        }
    }

    /** Opens the Add Appointment screen.
     * @param event the button is clicked
     * @throws IOException if an input or output exception has occurred
     * */
    @FXML
    public void openAddAppointments(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/SchedulingApplication/View/AddAppointment.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /** Opens the customer screen.
     * @param event the button is clicked
     * @throws IOException if an input or output exception has occurred
     * */
    @FXML
    public void openCustomerMenu(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/SchedulingApplication/View/CustomerScreen.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /** Opens the reports screen.
     * @param event the button is clicked
     * @throws IOException if an input or output exception has occurred
     * */
    @FXML
    public void openReports(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/SchedulingApplication/View/ReportScreen.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /** Opens the Update Appointments screen.
     * If an appointment is selected, the appointment is stored for reference from the update screen and the update
     * screen is shown. If no appointment is selected, a warning alert appears.
     * @param event the button is clicked
     * @throws IOException if an input or output exception has occurred
     * */
    @FXML
    public void openUpdateAppointments(MouseEvent event) throws IOException {
        if (AppointmentsTableView.getSelectionModel().getSelectedItem() != null) {
            AppointmentDAO.setUpdateAppointment(AppointmentsTableView.getSelectionModel().getSelectedItem());
            Parent root = FXMLLoader.load(getClass().getResource("/SchedulingApplication/View/UpdateAppointment.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Appointment Selected!");
            alert.setContentText("Please select an appointment from the calender.");
            alert.showAndWait();
        }

    }
}
