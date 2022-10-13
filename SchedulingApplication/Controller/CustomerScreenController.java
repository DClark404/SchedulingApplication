package SchedulingApplication.Controller;

import SchedulingApplication.DAO.CustomerDAO;
import SchedulingApplication.Model.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/** The controller class for the customer screen. Contains FXML ID attributes and event handlers for every table and
 * button.
 * */
public class CustomerScreenController {

    @FXML
    private TableView<Customer> CustomersTableView;

    @FXML
    private TableColumn<Customer, Integer> CustomerIDColumn;

    @FXML
    private TableColumn<Customer, String> CustomerNameColumn;

    @FXML
    private TableColumn<Customer, String> CustomerAddressColumn;

    @FXML
    private TableColumn<Customer, String> CustomerPostalColumn;

    @FXML
    private TableColumn<Customer, String> CustomerPhoneColumn;

    @FXML
    private TableColumn<Customer, String> CustomerCountryColumn;

    @FXML
    private TableColumn<Customer, String> CustomerDivisionColumn;

    /** Initializes the customer tableview within the GUI and all of its columns to their appropriate values.
     * */
    @FXML
    public void initialize() {
        CustomersTableView.setItems(CustomerDAO.getCustomerList());
        CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        CustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        CustomerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        CustomerPostalColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        CustomerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        CustomerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        CustomerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
    }

    /** Deletes a customer that is selected from the table. On click shows a warning dialog box if no item is
     * selected. If an item is selected, displays a confirmation dialog box with the customer id and name listed.
     * */
    @FXML
    public void deleteCustomer() {
        if (CustomersTableView.getSelectionModel().getSelectedItem() != null) {
            Customer delCustomer = CustomersTableView.getSelectionModel().getSelectedItem();
            String deletingID = Integer.toString(delCustomer.getCustomerID());
            String deletingName = delCustomer.getCustomerName();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Attempting to delete customer ID #" + deletingID + " named " + deletingName + ".");
            alert.setContentText("Deleting this customer will also delete all associated appointments. " +
                    "Are you sure you want to delete this customer?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        CustomerDAO.deleteCustomer(delCustomer.getCustomerID());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Customer Selected!");
            alert.setContentText("Please select a customer from the list.");
            alert.showAndWait();
        }
    }

    /** Opens the Add Customer screen.
     * @param event the button is clicked
     * @throws IOException if an input or output exception has occurred
     * */
    @FXML
    public void openAddCustomer(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/SchedulingApplication/View/AddCustomer.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /** Opens the Update Customer screen.
     * If a customer is selected, the customer is stored for reference from the update screen and the update
     * screen is shown. If no appointment is selected, a warning alert appears.
     * @param event the button is clicked
     * @throws IOException if an input or output exception has occurred
     * */
    @FXML
    public void openUpdateCustomer(MouseEvent event) throws IOException {
        if (CustomersTableView.getSelectionModel().getSelectedItem() != null) {
            CustomerDAO.setUpdateCustomer(CustomersTableView.getSelectionModel().getSelectedItem());
            Parent root = FXMLLoader.load(getClass().getResource("/SchedulingApplication/View/UpdateCustomer.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Customer Selected!");
            alert.setContentText("Please select a customer from the list.");
            alert.showAndWait();
        }
    }

    /** Closes the customer screen and returns to the main calender screen.
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
