package SchedulingApplication.Controller;

import SchedulingApplication.DAO.AppointmentDAO;
import SchedulingApplication.DAO.ContactDAO;
import SchedulingApplication.DAO.CustomerDAO;
import SchedulingApplication.DAO.UserDAO;
import SchedulingApplication.Model.Appointment;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** The controller class for the update appointment screen. Contains fields for all relevant appointment data and event
 * handlers for every button.
 * */
public class UpdateAppointmentController {

    @FXML
    private TextField txtUpdateID;

    @FXML
    private TextField txtUpdateTitle;

    @FXML
    private TextField txtUpdateDescription;

    @FXML
    private TextField txtUpdateLocation;

    @FXML
    private TextField txtUpdateType;

    @FXML
    private ComboBox<Contact> comboUpdateContact;

    @FXML
    private ComboBox<LocalTime> comboUpdateStartTime;

    @FXML
    private ComboBox<LocalTime> comboUpdateEndTime;

    @FXML
    private ComboBox<Customer> comboUpdateCustomer;

    @FXML
    private DatePicker dateUpdateStartDate;

    @FXML
    private DatePicker dateUpdateEndDate;

    @FXML
    private ComboBox<User> comboUpdateUser;

    /** Initializes the ComboBox fields to their respective lists of data types. Form data is automatically populated
     * based on selection of appointment to update.
     * */
    public void initialize() {
        Appointment modAppointment = AppointmentDAO.getUpdateAppointment();

        LocalDate modStartDate = modAppointment.getStartDateTime().toLocalDateTime().toLocalDate();
        LocalTime modStartTime = modAppointment.getStartDateTime().toLocalDateTime().toLocalTime();
        LocalDate modEndDate = modAppointment.getEndDateTime().toLocalDateTime().toLocalDate();
        LocalTime modEndTime = modAppointment.getEndDateTime().toLocalDateTime().toLocalTime();

        comboUpdateContact.setItems(ContactDAO.getContactList());
        comboUpdateStartTime.setItems(TimeUtility.getPossibleTimes());
        comboUpdateEndTime.setItems(TimeUtility.getPossibleTimes());
        comboUpdateCustomer.setItems(CustomerDAO.getCustomerList());
        comboUpdateUser.setItems(UserDAO.getUserList());

        txtUpdateID.setText(Integer.toString(modAppointment.getAppointmentID()));
        txtUpdateTitle.setText(modAppointment.getAppointmentTitle());
        txtUpdateDescription.setText(modAppointment.getDescription());
        txtUpdateLocation.setText(modAppointment.getLocation());
        txtUpdateType.setText(modAppointment.getType());
        comboUpdateContact.setValue(ContactDAO.getContactList().get(modAppointment.getContactID()-1));
        dateUpdateStartDate.setValue(modStartDate);
        comboUpdateStartTime.setValue(modStartTime);
        dateUpdateEndDate.setValue(modEndDate);
        comboUpdateEndTime.setValue(modEndTime);
        comboUpdateCustomer.setValue(CustomerDAO.getCustomerList().get(modAppointment.getCustomerID()-1));
        comboUpdateUser.setValue(UserDAO.getUserList().get(modAppointment.getUserID()-1));
    }

    /** Closes the update appointment screen and returns to the main calender screen.
     * @param event the button is clicked
     * @throws IOException if an input or output exception has occurred
     * */
    @FXML
    public void cancelUpdateAppointment(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/SchedulingApplication/View/MainCalender.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /** Updates an existing appointment with the input information if it is valid.
     * All input validation occurs in this method. Some of the user input data is collected earlier to enable this input
     * validation. No fields may be blank. Appointment times must be logically sequential, within business operating
     * hours, and cannot overlap with other appointments from the same customer. If the information is valid the
     * appointment will be updated and the application will return to the main screen.
     * @param event the button is clicked
     * @throws IOException if an input or output exception has occurred
     * @throws SQLException if any database exception has occurred
     * */
    @FXML
    public void saveUpdateAppointment(MouseEvent event) throws IOException, SQLException {

        Timestamp start = Timestamp.valueOf(LocalDateTime.of(dateUpdateStartDate.getValue(),
                comboUpdateStartTime.getValue()));
        Timestamp end = Timestamp.valueOf(LocalDateTime.of(dateUpdateEndDate.getValue(),
                comboUpdateEndTime.getValue()));

        int customerID = comboUpdateCustomer.getSelectionModel().getSelectedItem().getCustomerID();
        String customerName = comboUpdateCustomer.getSelectionModel().getSelectedItem().getCustomerName();
        int appointmentID = AppointmentDAO.getUpdateAppointment().getAppointmentID();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Invalid Form Data!");

        if (txtUpdateTitle.getText().trim().equals("")) {
            alert.setContentText("Please enter a title.");
            alert.showAndWait();
        } else if (txtUpdateDescription.getText().trim().equals("")) {
            alert.setContentText("Please enter a description.");
            alert.showAndWait();
        } else if (txtUpdateLocation.getText().trim().equals("")) {
            alert.setContentText("Please enter a location.");
            alert.showAndWait();
        } else if (comboUpdateContact.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Please select the contact.");
            alert.showAndWait();
        } else if (txtUpdateType.getText().trim().equals("")) {
            alert.setContentText("Please enter a type.");
            alert.showAndWait();
        } else if (dateUpdateStartDate.getValue() == null) {
            alert.setContentText("Please select the start date.");
            alert.showAndWait();
        } else if (comboUpdateStartTime.getValue() == null) {
            alert.setContentText("Please select the start time.");
            alert.showAndWait();
        } else if (dateUpdateEndDate.getValue() == null) {
            alert.setContentText("Please select the end date.");
            alert.showAndWait();
        } else if (comboUpdateEndTime.getValue() == null) {
            alert.setContentText("Please select the end time.");
            alert.showAndWait();
        } else if (comboUpdateCustomer.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Please select the customer.");
            alert.showAndWait();
        } else if (comboUpdateUser.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Please select the user.");
            alert.showAndWait();
        } else if (dateUpdateStartDate.getValue().isAfter(dateUpdateEndDate.getValue())) {
            alert.setContentText("Start date must be before end date.");
            alert.showAndWait();
        } else if (comboUpdateStartTime.getValue().isAfter(comboUpdateEndTime.getValue()) &&
                dateUpdateStartDate.getValue().equals(dateUpdateEndDate.getValue())) {
            alert.setContentText("Start time must be before end time.");
            alert.showAndWait();
        } else if (TimeUtility.withinBusinessHours(comboUpdateStartTime.getValue(), comboUpdateEndTime.getValue())) {
            alert.setHeaderText("Appointment is not within business operating hours!");
            alert.setContentText("Business operating hours are between 8:00 AM and 10:00 PM EST every day.");
            alert.showAndWait();
        }
        else if (TimeUtility.detectTimeOverlap(start.toLocalDateTime(), end.toLocalDateTime(),
                comboUpdateCustomer.getSelectionModel().getSelectedItem().getCustomerID(), appointmentID)) {
            alert.setHeaderText("Customer may not have overlapping appointments!");
            alert.setContentText("Customer #" + customerID + " " + customerName + " already has an appointment " +
                    "scheduled during the selected times.");
            alert.showAndWait();
        }
        else {
            String title = txtUpdateTitle.getText().trim();
            String description = txtUpdateDescription.getText().trim();
            String location = txtUpdateLocation.getText().trim();
            String type = txtUpdateType.getText().trim();
            int customer = comboUpdateCustomer.getSelectionModel().getSelectedItem().getCustomerID();
            int user = comboUpdateUser.getSelectionModel().getSelectedItem().getUserID();
            int contact = comboUpdateContact.getSelectionModel().getSelectedItem().getContactID();
            String contactName = comboUpdateContact.getSelectionModel().getSelectedItem().getContactName();

            AppointmentDAO.updateAppointment(appointmentID, title, description, location, type, start, end, customer,
                    user, contact, contactName);

            Parent root = FXMLLoader.load(getClass().getResource("/SchedulingApplication/View/MainCalender.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

}
