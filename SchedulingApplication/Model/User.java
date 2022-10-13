package SchedulingApplication.Model;

/** Class representing a user of the application. */
public class User {
    private int userID;
    private String userName;
    private String password;

    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString(){
        return ("[" + userID + "] " + userName); }

    /** Setter for the userID attribute.
     * @param userID the unique identifier
     * */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    /** Setter for the userName attribute.
     * @param userName the user name
     * */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** Setter for the password attribute.
     * @param password the user password
     * */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Getter for the userID attribute.
     * @return      the registered user ID
     * */
    public int getUserID() {
        return userID;
    }

    /** Getter for the username attribute.
     * @return      the user name
     * */
    public String getUserName() {
        return userName;
    }

    /** Getter for the password attribute.
     * @return      the user password
     * */
    public String getPassword() {
        return password;
    }
}
