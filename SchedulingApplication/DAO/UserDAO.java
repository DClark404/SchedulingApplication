package SchedulingApplication.DAO;

import SchedulingApplication.Model.User;
import SchedulingApplication.util.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/** Data Access Object class that manages server queries for the User data type.
 * Populates and contains a list that is analogous to a table in the database.
 * */
public class UserDAO {

    /** Contains a list of user objects that is analogous to the user database table. */
    private static final ObservableList<User> userList = FXCollections.observableArrayList();

    /** Getter for the userList attribute.
     * @return      the list of users
     * */
    public static ObservableList<User> getUserList() {
        return userList;
    }

    /** Populates the userList attribute with user objects representing every row of data within the user table of the
     * database.
     * @throws SQLException if any database exception has occurred
     * */
    public static void populateUserList () throws SQLException {
        DBConnect.setPreparedStatement("SELECT * FROM users");
        DBConnect.getPreparedStatement().execute();
        ResultSet rs = DBConnect.getPreparedStatement().getResultSet();
        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            User newUser = new User(userID, userName, password);
            userList.add(newUser);
        }
    }
}
