package SchedulingApplication.Model;

/** The class representing server-side Contact data. */
public class Contact {
    private int contactID;
    private String contactName;

    public Contact(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    @Override
    public String toString(){
        return ("[" + contactID + "] " + contactName);
    }

    /** Setter for the contactID attribute.
     * @param contactID the unique ID of the contact
     * */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /** Setter for the contactName attribute.
     * @param contactName the name of the contact
     * */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** Getter for the contactID attribute.
     * @return      the unique ID of the contact
     * */
    public int getContactID() {
        return contactID;
    }

    /** Getter for the contactName attribute.
     * @return      the name of the contact
     * */
    public String getContactName() {
        return contactName;
    }
}
