package SchedulingApplication.DAO;

import SchedulingApplication.Model.FirstLevelDivision;
import SchedulingApplication.util.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;


/** Data Access Object class that manages server queries for the FirstLevelDivision data type.
 * Populates and contains a list that is analogous to a table in the database.
 * */
public class FirstLevelDivisionDAO {

    /** Contains a list of FirstLevelDivision objects that is analogous to the FirstLevelDivision database table. */
    private static final ObservableList<FirstLevelDivision> divisionList = FXCollections.observableArrayList();

    /** Contains a parsed list of FirstLevelDivision objects. */
    private static final ObservableList<FirstLevelDivision> associatedDivisions = FXCollections.observableArrayList();

    /** Getter for the associatedDivisions attribute.
     * @return      list of associated divisions
     * */
    public static ObservableList<FirstLevelDivision> getAssociatedDivisions() {
        return associatedDivisions;
    }

    /** Sets the associatedDivisions attribute with a list of divisions filtered by their countryID.
     * @param countryID the unique ID of a country
     * */
    public static void setAssociatedDivisions(int countryID) {
        associatedDivisions.clear();
        for (FirstLevelDivision item : divisionList) {
            if (item.getCountryID() == countryID) {
                associatedDivisions.add(item);
            }
        }
    }

    /** Populates the divisionList attribute with FirstLevelDivision objects representing every row of data within
     * the FirstLevelDivision table of the database.
     * @throws SQLException if any database exception has occurred
     * */
    public static void populateDivisionList () throws SQLException {
        DBConnect.setPreparedStatement("SELECT * FROM first_level_divisions");
        DBConnect.getPreparedStatement().execute();
        ResultSet rs = DBConnect.getPreparedStatement().getResultSet();
        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("COUNTRY_ID");
            FirstLevelDivision newDivision = new FirstLevelDivision(divisionID, divisionName, countryID);
            divisionList.add(newDivision);
        }
    }

    /** Searches the divisionList attribute to return a division object with an ID that matches the input parameter.
     * @param divisionID the unique ID to search for
     * @return      the division associated with the search parameter
     * */
    public static FirstLevelDivision divisionFromID (int divisionID) {
        FirstLevelDivision newDivision = null;
        for (FirstLevelDivision item : divisionList) {
            if (item.getDivisionID() == divisionID) {
                newDivision = item;
            }
        }
        return newDivision;
    }
}
