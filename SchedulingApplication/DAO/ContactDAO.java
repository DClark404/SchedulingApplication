package SchedulingApplication.DAO;

import SchedulingApplication.Model.Contact;
import SchedulingApplication.util.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/** Data Access Object class that manages server queries for the Contact data type.
 * Populates and contains a list that is analogous to a table in the database.
 * */
public class ContactDAO {

    /** Contains a list of Contact objects that is analogous to the contact database table. */
    private static final ObservableList<Contact> contactList = FXCollections.observableArrayList();

    /** Getter for the contactList attribute.
     * @return      the list of contacts
     * */
    public static ObservableList<Contact> getContactList() {
        return contactList;
    }

    /** Populates the contactList attribute with contact objects representing every row of data within the contact
     * table of the database.
     * @throws SQLException if any database exception has occurred
     * */
    public static void populateContactList () throws SQLException {
        DBConnect.setPreparedStatement("SELECT * FROM contacts");
        DBConnect.getPreparedStatement().execute();
        ResultSet rs = DBConnect.getPreparedStatement().getResultSet();
        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            Contact newContact = new Contact(contactID, contactName);
            contactList.add(newContact);
        }
    }
}
