package SchedulingApplication.Controller;

import SchedulingApplication.DAO.CountryDAO;
import SchedulingApplication.DAO.CustomerDAO;
import SchedulingApplication.DAO.FirstLevelDivisionDAO;
import SchedulingApplication.Model.Country;
import SchedulingApplication.Model.Customer;
import SchedulingApplication.Model.FirstLevelDivision;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/** The controller class for the update customer screen. Contains fields or ComboBoxes for all relevant customer data
 * and event handlers for every button.
 * */
public class UpdateCustomerController {

    @FXML
    private TextField txtCustomerID;

    @FXML
    private TextField txtUpdateName;

    @FXML
    private TextField txtUpdateAddress;

    @FXML
    private TextField txtUpdatePostalCode;

    @FXML
    private ComboBox<Country> comboUpdateCountry;

    @FXML
    private ComboBox<FirstLevelDivision> comboUpdateDivision;

    @FXML
    private TextField txtUpdatePhoneNumber;

    /** Initializes the ComboBox fields to their respective lists of data types.
     * The first level division ComboBox will not be populated until a country is selected. Form data is automatically
     * populated based on selection of customer to update.
     * */
    public void initialize() {
        Customer modCustomer = CustomerDAO.getUpdateCustomer();

        Country modCountry = CountryDAO.countryFromName(modCustomer.getCountryName());
        FirstLevelDivision modDivision = FirstLevelDivisionDAO.divisionFromID(modCustomer.getDivisionID());
        FirstLevelDivisionDAO.setAssociatedDivisions(modCountry.getCountryID());

        comboUpdateCountry.setItems(CountryDAO.getCountryList());
        comboUpdateDivision.setItems(FirstLevelDivisionDAO.getAssociatedDivisions());

        txtCustomerID.setText(Integer.toString(modCustomer.getCustomerID()));
        txtUpdateName.setText(modCustomer.getCustomerName());
        txtUpdateAddress.setText(modCustomer.getAddress());
        txtUpdatePostalCode.setText(modCustomer.getPostalCode());
        txtUpdatePhoneNumber.setText(modCustomer.getCustomerPhone());
        comboUpdateCountry.setValue(modCountry);
        comboUpdateDivision.setValue(modDivision);
    }

    /** Closes the update customer screen and returns to the customer screen.
     * @param event the button is clicked
     * @throws IOException if an input or output exception has occurred
     * */
    @FXML
    public void cancelUpdateCustomer(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/SchedulingApplication/View/CustomerScreen.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /** Updates an existing customer with the input information if it is valid.
     * All input validation occurs in this method, no fields or ComboBoxes may be blank. If the information is valid
     * the new customer will be saved and the application will return to the customer screen.
     * @param event the button is clicked
     * @throws IOException if an input or output exception has occurred
     * @throws SQLException if any database exception has occurred
     * */
    @FXML
    public void saveUpdateCustomer(MouseEvent event) throws IOException, SQLException {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Invalid Form Data!");

        if (txtUpdateName.getText().trim().equals("")) {
            alert.setContentText("Please enter a name.");
            alert.showAndWait();
        } else if (txtUpdateAddress.getText().trim().equals("")) {
            alert.setContentText("Please enter an address.");
            alert.showAndWait();
        } else if (txtUpdatePostalCode.getText().trim().equals("")) {
            alert.setContentText("Please enter a postal code.");
            alert.showAndWait();
        } else if (txtUpdatePhoneNumber.getText().trim().equals("")) {
            alert.setContentText("Please enter a phone number.");
            alert.showAndWait();
        } else if (comboUpdateCountry.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Please select a country.");
            alert.showAndWait();
        } else if (comboUpdateDivision.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Please select a division.");
            alert.showAndWait();
        } else {

            int customerID = CustomerDAO.getUpdateCustomer().getCustomerID();
            String name = txtUpdateName.getText().trim();
            String address = txtUpdateAddress.getText().trim();
            String postalCode = txtUpdatePostalCode.getText().trim();
            String phoneNumber = txtUpdatePhoneNumber.getText().trim();
            int divisionID = comboUpdateDivision.getSelectionModel().getSelectedItem().getDivisionID();
            String divisionName = comboUpdateDivision.getSelectionModel().getSelectedItem().getDivision();
            String countryName = comboUpdateCountry.getSelectionModel().getSelectedItem().getCountry();

            CustomerDAO.updateCustomer(customerID, name, address, postalCode, phoneNumber, divisionID, divisionName,
                    countryName);

            Parent root = FXMLLoader.load(getClass().getResource("/SchedulingApplication/View/CustomerScreen.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }

    }

    /** Triggers when a new country is selected, populating the First Level Division ComboBox with all divisions
     * associated with that country.
     * */
    @FXML
    public void updateDivisionList() {
        int associatedList = comboUpdateCountry.getSelectionModel().getSelectedItem().getCountryID();
        FirstLevelDivisionDAO.setAssociatedDivisions(associatedList);
    }

}
