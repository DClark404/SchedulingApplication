package SchedulingApplication.Controller;

import SchedulingApplication.DAO.AppointmentDAO;
import SchedulingApplication.DAO.ContactDAO;
import SchedulingApplication.DAO.CustomerDAO;
import SchedulingApplication.DAO.UserDAO;
import SchedulingApplication.Model.Contact;
import SchedulingApplication.Model.Customer;
import SchedulingApplication.Model.User;
import SchedulingApplication.util.TimeUtility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** The controller class for the add appointment screen. Contains fields for all relevant appointment data and event
 * handlers for every button.
 * */
public class AddAppointmentController {

    @FXML
    private TextField txtAddType;

    @FXML
    private TextField txtAddTitle;

    @FXML
    private TextField txtAddDescription;

    @FXML
    private TextField txtAddLocation;

    @FXML
    private ComboBox<Contact> comboAddContact;

    @FXML
    private ComboBox<LocalTime> comboAddStartTime;

    @FXML
    private ComboBox<LocalTime> comboAddEndTime;

    @FXML
    private ComboBox<Customer> comboAddCustomer;

    @FXML
    private DatePicker dateAddStartDate;

    @FXML
    private DatePicker dateAddEndDate;

    @FXML
    private ComboBox<User> comboAddUser;

    /** Initializes the ComboBox fields to their respective lists of data types. */
    @FXML
    public void initialize() {
        comboAddUser.setItems(UserDAO.getUserList());
        comboAddUser.setVisibleRowCount(5);
        comboAddCustomer.setItems(CustomerDAO.getCustomerList());
        comboAddCustomer.setVisibleRowCount(5);
        comboAddContact.setItems(ContactDAO.getContactList());
        comboAddContact.setVisibleRowCount(5);
        comboAddStartTime.setItems(TimeUtility.getPossibleTimes());
        comboAddStartTime.setVisibleRowCount(10);
        comboAddEndTime.setItems(TimeUtility.getPossibleTimes());
        comboAddEndTime.setVisibleRowCount(10);
    }

    /** Closes the add appointment screen and returns to the main calender screen.
     * @param event the button is clicked
     * @throws IOException if an input or output exception has occurred
     * */
    @FXML
    public void cancelAddAppointment(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/SchedulingApplication/View/MainCalender.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /** Saves the input information to the database as a new appointment if it is valid.
     * All input validation occurs in this method. Some of the user input data is collected earlier to enable this input
     * validation. No fields may be blank. Appointment times must be logically sequential, within business operating
     * hours, and cannot overlap with other appointments from the same customer. If the information is valid the new
     * appointment will be saved and the application will return to the main screen.
     * @param event the button is clicked
     * @throws IOException if an input or output exception has occurred
     * @throws SQLException if any database exception has occurred
     * */
    @FXML
    public void saveAddAppointment(MouseEvent event) throws IOException, SQLException {

        Timestamp start = Timestamp.valueOf(LocalDateTime.of(dateAddStartDate.getValue(),
                comboAddStartTime.getValue()));
        Timestamp end = Timestamp.valueOf(LocalDateTime.of(dateAddEndDate.getValue(),
                comboAddEndTime.getValue()));

        int customerID = comboAddCustomer.getSelectionModel().getSelectedItem().getCustomerID();
        String customerName = comboAddCustomer.getSelectionModel().getSelectedItem().getCustomerName();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Invalid Form Data!");

        if (txtAddTitle.getText().trim().equals("")) {
            alert.setContentText("Please enter a title.");
            alert.showAndWait();
        } else if (txtAddDescription.getText().trim().equals("")) {
            alert.setContentText("Please enter a description.");
            alert.showAndWait();
        } else if (txtAddLocation.getText().trim().equals("")) {
            alert.setContentText("Please enter a location.");
            alert.showAndWait();
        } else if (comboAddContact.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Please select the contact.");
            alert.showAndWait();
        } else if (txtAddType.getText().trim().equals("")) {
            alert.setContentText("Please enter a type.");
            alert.showAndWait();
        } else if (dateAddStartDate.getValue() == null) {
            alert.setContentText("Please select the start date.");
            alert.showAndWait();
        } else if (comboAddStartTime.getValue() == null) {
            alert.setContentText("Please select the start time.");
            alert.showAndWait();
        } else if (dateAddEndDate.getValue() == null) {
            alert.setContentText("Please select the end date.");
            alert.showAndWait();
        } else if (comboAddEndTime.getValue() == null) {
            alert.setContentText("Please select the end time.");
            alert.showAndWait();
        } else if (comboAddCustomer.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Please select the customer.");
            alert.showAndWait();
        } else if (comboAddUser.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Please select the user.");
            alert.showAndWait();
        } else if (dateAddStartDate.getValue().isAfter(dateAddEndDate.getValue())) {
            alert.setContentText("Start date must be before end date.");
            alert.showAndWait();
        } else if (comboAddStartTime.getValue().isAfter(comboAddEndTime.getValue()) &&
                dateAddStartDate.getValue().equals(dateAddEndDate.getValue())) {
            alert.setContentText("Start time must be before end time.");
            alert.showAndWait();
        } else if (TimeUtility.withinBusinessHours(comboAddStartTime.getValue(), comboAddEndTime.getValue())) {
            alert.setHeaderText("Appointment is not within business operating hours!");
            alert.setContentText("Business operating hours are between 8:00 AM and 10:00 PM EST every day.");
            alert.showAndWait();
        } else if (TimeUtility.detectTimeOverlap(start.toLocalDateTime(), end.toLocalDateTime(),
                comboAddCustomer.getSelectionModel().getSelectedItem().getCustomerID(), 0)) {
            alert.setHeaderText("Customer may not have overlapping appointments!");
            alert.setContentText("Customer #" + customerID + " " + customerName + " already has an appointment " +
                    "scheduled during the selected times.");
            alert.showAndWait();
        }
        else {

            String title = txtAddTitle.getText().trim();
            String description = txtAddDescription.getText().trim();
            String location = txtAddLocation.getText().trim();
            String type = txtAddType.getText().trim();
            int customer = comboAddCustomer.getSelectionModel().getSelectedItem().getCustomerID();
            int user = comboAddUser.getSelectionModel().getSelectedItem().getUserID();
            int contact = comboAddContact.getSelectionModel().getSelectedItem().getContactID();
            String contactName = comboAddContact.getSelectionModel().getSelectedItem().getContactName();

            AppointmentDAO.createAppointment(title, description, location, type, start, end, customer,
                    user, contact, contactName);

            Parent root = FXMLLoader.load(getClass().getResource("/SchedulingApplication/View/MainCalender.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }
}
