package SchedulingApplication.DAO;

import SchedulingApplication.Model.Customer;
import SchedulingApplication.util.DBConnect;
import SchedulingApplication.util.TimeUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/** Data Access Object class that manages server queries for the Customer data type.
 * Populates and contains a list that is analogous to a table in the database. Capable of making changes to the
 * database via CREATE, UPDATE, and DELETE SQL queries.
 * */
public class CustomerDAO {

    /** Contains a list of Customer objects that is analogous to the customer database table. */
    private static final ObservableList<Customer> customerList = FXCollections.observableArrayList();

    /** Customer reference utilized to pass data between screens in FXML*/
    private static Customer updateCustomer = null;

    /** Getter for the updateCustomer attribute.
     * @return      the customer to be updated
     * */
    public static Customer getUpdateCustomer() {
        return updateCustomer;
    }

    /** Sets the updateCustomer attribute.
     * @param updateCustomer the customer to be updated
     * */
    public static void setUpdateCustomer(Customer updateCustomer) {
        CustomerDAO.updateCustomer = updateCustomer;
    }

    /** Getter for the customerList attribute.
     * @return      list of all customers
     * */
    public static ObservableList<Customer> getCustomerList() {
        return customerList;
    }

    /** Populates the customerList attribute with customer objects representing every row of data within
     * the customer table of the database.
     * @throws SQLException if any database exception has occurred
     * */
    public static void populateCustomerList () throws SQLException {
        DBConnect.setPreparedStatement("SELECT * FROM customers, first_level_divisions, countries WHERE " +
                "customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.COUNTRY_ID" +
                " = countries.Country_ID");
        DBConnect.getPreparedStatement().execute();
        ResultSet rs = DBConnect.getPreparedStatement().getResultSet();
        while (rs.next()) {

            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String customerPhone = rs.getString("Phone");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            String countryName = rs.getString("Country");

            Customer newCustomer = new Customer(customerID, customerName, address, postalCode, customerPhone,
                    createdDate, createdBy, lastUpdate, lastUpdatedBy, divisionID, divisionName, countryName);
            customerList.add(newCustomer);
        }
    }

    /** Uses a collection of input parameters to generate a preparedStatement object that updates the database with
     * new customer information. Adds the same customer object to the customerList attribute so that the
     * application accurately reflects server data.
     * @param customerName the name of the customer
     * @param address the address of the customer
     * @param postalCode the postal code of the customer
     * @param customerPhone the phone number of the customer
     * @param divisionID unique identifier for the division
     * @param divisionName name of the division
     * @param countryName name of the country
     * @throws SQLException if any database exception has occurred
     * */
    public static void createCustomer (String customerName, String address, String postalCode,
                                       String customerPhone, int divisionID, String divisionName,
                                       String countryName) throws SQLException {

        Timestamp currentTime = TimeUtility.getCurrentTimestamp();
        String currentUser = "test";

        DBConnect.setPreparedStatement("INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?,?,?)");
        DBConnect.getPreparedStatement().setString(1, customerName);
        DBConnect.getPreparedStatement().setString(2, address);
        DBConnect.getPreparedStatement().setString(3, postalCode);
        DBConnect.getPreparedStatement().setString(4, customerPhone);
        DBConnect.getPreparedStatement().setTimestamp(5, currentTime);
        DBConnect.getPreparedStatement().setString(6, currentUser);
        DBConnect.getPreparedStatement().setTimestamp(7, currentTime);
        DBConnect.getPreparedStatement().setString(8, currentUser);
        DBConnect.getPreparedStatement().setInt(9, divisionID);
        DBConnect.getPreparedStatement().execute();
        int customerID = DBConnect.returnGeneratedKey();

        Customer newCustomer = new Customer(customerID, customerName, address, postalCode, customerPhone, currentTime,
                currentUser, currentTime, currentUser, divisionID, divisionName, countryName);
        customerList.add(newCustomer);
    }

    /** Uses a collection of input parameters to generate a preparedStatement object that updates existing customer
    * information within the database. Updates the same customer within the customerList attribute so that the
    * application accurately reflects server data.
    * @param customerID the unique identifier of the customer
    * @param customerName the name of the customer
    * @param address the address of the customer
    * @param postalCode the postal code of the customer
    * @param customerPhone the phone number of the customer
    * @param divisionID unique identifier for the division
    * @param divisionName name of the division
    * @param countryName name of the country
    * @throws SQLException if any database exception has occurred
    * */
    public static void updateCustomer (int customerID, String customerName, String address, String postalCode,
                                       String customerPhone, int divisionID, String divisionName,
                                       String countryName) throws SQLException {

        Timestamp currentTime = TimeUtility.getCurrentTimestamp();
        String currentUser = "test";

        DBConnect.setPreparedStatement("UPDATE customers set Customer_Name = ?, Address = ?, Postal_Code = ?, " +
                "Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, " +
                "Division_ID = ? WHERE Customer_ID = ?");
        DBConnect.getPreparedStatement().setString(1, customerName);
        DBConnect.getPreparedStatement().setString(2, address);
        DBConnect.getPreparedStatement().setString(3, postalCode);
        DBConnect.getPreparedStatement().setString(4, customerPhone);
        DBConnect.getPreparedStatement().setTimestamp(5, currentTime);
        DBConnect.getPreparedStatement().setString(6, currentUser);
        DBConnect.getPreparedStatement().setTimestamp(7, currentTime);
        DBConnect.getPreparedStatement().setString(8, currentUser);
        DBConnect.getPreparedStatement().setInt(9, divisionID);
        DBConnect.getPreparedStatement().setInt(10, customerID);
        DBConnect.getPreparedStatement().execute();

        for (Customer item : customerList) {
            if (item.getCustomerID() == customerID) {
                item.setCustomerName(customerName);
                item.setAddress(address);
                item.setPostalCode(postalCode);
                item.setCustomerPhone(customerPhone);
                item.setCreatedDate(currentTime);
                item.setCreatedBy(currentUser);
                item.setLastUpdate(currentTime);
                item.setLastUpdatedBy(currentUser);
                item.setDivisionID(divisionID);
                item.setDivisionName(divisionName);
                item.setCountryName(countryName);
            }
        }
    }

    /** Uses a reference to the unique identifier of a customer to generate a preparedStatement object that deletes
     * a matching customer record from the database and from the appointmentList attribute.
     * To maintain referential integrity within the database, all appointments associated with a given customer must
     * be deleted before removing that customer.
     * @param customerID the unique identifier associated with a customer
     * @throws SQLException if any database exception has occurred
     * */
    public static void deleteCustomer (int customerID) throws SQLException {

        DBConnect.setPreparedStatement("DELETE from appointments WHERE Customer_ID = ?");
        DBConnect.getPreparedStatement().setInt(1, customerID);
        DBConnect.getPreparedStatement().execute();

        AppointmentDAO.getAppointmentList().removeIf(appointment -> appointment.getCustomerID() == customerID);

        DBConnect.setPreparedStatement("DELETE from customers WHERE Customer_ID = ?");
        DBConnect.getPreparedStatement().setInt(1, customerID);
        DBConnect.getPreparedStatement().execute();

        customerList.removeIf(customer -> customer.getCustomerID() == customerID);
    }
}
