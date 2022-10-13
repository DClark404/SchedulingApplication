package SchedulingApplication.Controller;


import SchedulingApplication.DAO.ContactDAO;
import SchedulingApplication.Model.Appointment;
import SchedulingApplication.Model.Contact;
import SchedulingApplication.Model.Report;
import SchedulingApplication.util.ReportUtility;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;


/** The controller class for the reports screen. Contains FXML ID attributes and event handlers for every table and
 * ComboBox. Utilizes a tabPane to display multiple reports to the user.
 * */
public class ReportScreenController {

    @FXML
    private TableView<Report> totalAppointmentsTable;

    @FXML
    private TableColumn<Report, String> totalMonthColumn;

    @FXML
    private TableColumn<Report, String> totalTypeColumn;

    @FXML
    private TableColumn<Report, Integer> totalAmountColumn;

    @FXML
    private TableView<Appointment> scheduleTable;

    @FXML
    private TableColumn<Appointment, Integer> ScheduleIDColumn;

    @FXML
    private TableColumn<Appointment, String> ScheduleTitleColumn;

    @FXML
    private TableColumn<Appointment, String> ScheduleTypeColumn;

    @FXML
    private TableColumn<Appointment, String> ScheduleDescriptionColumn;

    @FXML
    private TableColumn<Appointment, Timestamp> ScheduleStartColumn;

    @FXML
    private TableColumn<Appointment, Timestamp> ScheduleEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> ScheduleCustomerIDColumn;

    @FXML
    private ComboBox<Contact> comboScheduleContact;

    @FXML
    private TableView<Report> lengthsTable;

    @FXML
    private TableColumn<Report, Integer> lengthsIDColumn;

    @FXML
    private TableColumn<Report, String> lengthsNameColumn;

    @FXML
    private TableColumn<Report, Long> lengthsMinutesColumn;

    /** Initializes all tableviews within the GUI and all of their associated columns to the appropriate values.
     * */
    @FXML
    public void initialize() {
        totalAppointmentsTable.setItems(ReportUtility.totalByTypeAndMonth());
        totalMonthColumn.setCellValueFactory(new PropertyValueFactory<>("reportMonth"));
        totalTypeColumn.setCellValueFactory(new PropertyValueFactory<>("reportType"));
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("reportCount"));

        comboScheduleContact.setItems(ContactDAO.getContactList());
        comboScheduleContact.setValue(ContactDAO.getContactList().get(0));

        int currentSelection = comboScheduleContact.getSelectionModel().getSelectedItem().getContactID();
        FilteredList<Appointment> filteredAppointments = ReportUtility.getSortedAppointments(currentSelection);
        scheduleTable.setItems(filteredAppointments);

        ScheduleIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        ScheduleTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        ScheduleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        ScheduleDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        ScheduleStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        ScheduleEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        ScheduleCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        lengthsTable.setItems(ReportUtility.getAverageAppointmentLengths());
        lengthsIDColumn.setCellValueFactory(new PropertyValueFactory<>("reportCount"));
        lengthsNameColumn.setCellValueFactory(new PropertyValueFactory<>("reportID"));
        lengthsMinutesColumn.setCellValueFactory(new PropertyValueFactory<>("reportDuration"));

    }

    /** Triggers when a new contact is selected, populating the scheduleTable with all appointment data associated
     * with the selected contact.
     * */
    @FXML
    public void ScheduleContactSelected() {
        int currentSelection = comboScheduleContact.getSelectionModel().getSelectedItem().getContactID();
        FilteredList<Appointment> filteredAppointments = ReportUtility.getSortedAppointments(currentSelection);
        scheduleTable.setItems(filteredAppointments);
    }

    /** Closes the reports screen and returns to the main calender screen.
     * @param event the button is clicked
     * @throws IOException if an input or output exception has occurred
     * */
    @FXML
    public void returnAppointmentsMenu(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/SchedulingApplication/View/MainCalender.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
