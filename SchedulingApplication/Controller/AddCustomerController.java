package SchedulingApplication.Controller;

import SchedulingApplication.DAO.CountryDAO;
import SchedulingApplication.DAO.CustomerDAO;
import SchedulingApplication.DAO.FirstLevelDivisionDAO;
import SchedulingApplication.Model.Country;
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

/** The controller class for the add customer screen. Contains fields or ComboBoxes for all relevant customer data and
 * event handlers for every button.
 * */
public class AddCustomerController {

    @FXML
    private TextField txtAddName;

    @FXML
    private TextField txtAddAddress;

    @FXML
    private TextField txtAddPostalCode;

    @FXML
    private ComboBox<Country> comboAddCountry;

    @FXML
    private ComboBox<FirstLevelDivision> comboAddDivision;

    @FXML
    private TextField txtAddPhoneNumber;

    /** Initializes the ComboBox fields to their respective lists of data types.
     * The first level division ComboBox will not be populated until a country is selected.
     * */
    @FXML
    public void initialize() {
        comboAddCountry.setItems(CountryDAO.getCountryList());
        comboAddDivision.setItems(FirstLevelDivisionDAO.getAssociatedDivisions());
    }

    /** Closes the add customer screen and returns to the customer screen.
     * @param event the button is clicked
     * @throws IOException if an input or output exception has occurred
     * */
    @FXML
    public void cancelAddCustomer(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/SchedulingApplication/View/CustomerScreen.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /** Saves the input information to the database as a new customer if it is valid.
     * All input validation occurs in this method, no fields or ComboBoxes may be blank. If the information is valid
     * the new customer will be saved and the application will return to the customer screen.
     * @param event the button is clicked
     * @throws IOException if an input or output exception has occurred
     * @throws SQLException if any database exception has occurred
     * */
    @FXML
    public void saveAddCustomer(MouseEvent event) throws IOException, SQLException {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Invalid Form Data!");

        if (txtAddName.getText().trim().equals("")) {
            alert.setContentText("Please enter a name.");
            alert.showAndWait();
        } else if (txtAddAddress.getText().trim().equals("")) {
            alert.setContentText("Please enter an address.");
            alert.showAndWait();
        } else if (txtAddPostalCode.getText().trim().equals("")) {
            alert.setContentText("Please enter a postal code.");
            alert.showAndWait();
        } else if (txtAddPhoneNumber.getText().trim().equals("")) {
            alert.setContentText("Please enter a phone number.");
            alert.showAndWait();
        } else if (comboAddCountry.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Please select a country.");
            alert.showAndWait();
        } else if (comboAddDivision.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Please select a division.");
            alert.showAndWait();
        } else {

            String name = txtAddName.getText().trim();
            String address = txtAddAddress.getText().trim();
            String postalCode = txtAddPostalCode.getText().trim();
            String phoneNumber = txtAddPhoneNumber.getText().trim();
            int divisionID = comboAddDivision.getSelectionModel().getSelectedItem().getDivisionID();
            String divisionName = comboAddDivision.getSelectionModel().getSelectedItem().getDivision();
            String countryName = comboAddCountry.getSelectionModel().getSelectedItem().getCountry();

            CustomerDAO.createCustomer(name, address, postalCode, phoneNumber, divisionID, divisionName, countryName);

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
        int associatedList = comboAddCountry.getSelectionModel().getSelectedItem().getCountryID();
        FirstLevelDivisionDAO.setAssociatedDivisions(associatedList);
    }

}
