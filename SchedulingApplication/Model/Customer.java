package SchedulingApplication.Model;

import java.sql.Timestamp;

/** The class representing a customer registered with the organization. */
public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String customerPhone;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;
    private String divisionName;
    private String countryName;

    public Customer(int customerID, String customerName, String address, String postalCode,
                    String customerPhone, Timestamp createdDate, String createdBy, Timestamp lastUpdate,
                    String lastUpdatedBy, int divisionID, String divisionName, String countryName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.customerPhone = customerPhone;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryName = countryName;
    }

    @Override
    public String toString(){
        return ("[" + customerID + "] " + customerName);
    }

    /** Setter for the customerID attribute.
     * @param customerID the unique ID of the customer
     * */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** Setter for the customerName attribute.
     * @param customerName the name of the customer
     * */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /** Setter for the address attribute.
     * @param address the address of the customer
     * */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Setter for the postalCode attribute.
     * @param postalCode the postal code of the customer
     * */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** Setter for the customerPhone attribute.
     * @param customerPhone the phone number of a customer
     * */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /** Setter for the createdDate attribute.
     * @param createdDate the date and time the customer entry was created
     * */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /** Setter for the createdBy attribute.
     * @param createdBy the user that created the customer entry
     * */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** Setter for the lastUpdate attribute.
     * @param lastUpdate the date and time of the last update to the customer entry
     * */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** Setter for the lastUpdatedBy attribute.
     * @param lastUpdatedBy the last user that updated the customer entry
     * */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** Setter for the divisionID attribute.
     * @param divisionID the unique ID of the division
     * */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /** Setter for the divisionName attribute.
     * @param divisionName the name of the division
     * */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /** Setter for the countryName attribute.
     * @param countryName the name of the country
     * */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /** Getter for the customerID attribute.
     * @return      the unique ID of the customer
     * */
    public int getCustomerID() {
        return customerID;
    }

    /** Getter for the customerName attribute.
     * @return      the name of the customer
     * */
    public String getCustomerName() {
        return customerName;
    }

    /** Getter for the address attribute.
     * @return      the address of the customer
     * */
    public String getAddress() {
        return address;
    }

    /** Getter for the postalCode attribute.
     * @return      the postal code of the customer
     * */
    public String getPostalCode() {
        return postalCode;
    }

    /** Getter for the customerPhone attribute.
     * @return      the phone number of the customer
     * */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /** Getter for the createdDate attribute.
     * @return      the date and time the customer entry was created
     * */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /** Getter for the createdBy attribute.
     * @return      the user who created the customer entry
     * */
    public String getCreatedBy() {
        return createdBy;
    }

    /** Getter for the lastUpdate attribute.
     * @return      the date and time the customer entry was last updated
     * */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /** Getter for the lastUpdatedBy attribute.
     * @return      the last user that updated the customer entry
     * */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** Getter for the divisionID attribute.
     * @return      the unique ID of the division
     * */
    public int getDivisionID() {
        return divisionID;
    }

    /** Getter for the divisionName attribute.
     * @return      the name of the division
     * */
    public String getDivisionName() {
        return divisionName;
    }

    /** Getter for the countryName attribute.
     * @return      the name of the country
     * */
    public String getCountryName() {
        return countryName;
    }
}
