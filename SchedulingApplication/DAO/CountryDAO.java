package SchedulingApplication.DAO;

import SchedulingApplication.Model.Country;
import SchedulingApplication.util.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/** Data Access Object class that manages server queries for the Country data type.
 * Populates and contains a list that is analogous to a table in the database.
 * */
public class CountryDAO {

    /** Contains a list of country objects that is analogous to the country database table. */
    private static final ObservableList<Country> countryList = FXCollections.observableArrayList();

    /** Getter for the countryList attribute.
     * @return      the list of countries
     * */
    public static ObservableList<Country> getCountryList() {
        return countryList;
    }

    /** Populates the countryList attribute with country objects representing every row of data within the country
     * table of the database.
     * @throws SQLException if any database exception has occurred
     * */
    public static void populateCountryList () throws SQLException {
        DBConnect.setPreparedStatement("SELECT * FROM countries");
        DBConnect.getPreparedStatement().execute();
        ResultSet rs = DBConnect.getPreparedStatement().getResultSet();
        while (rs.next()) {
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country newCountry = new Country(countryID, countryName);
            countryList.add(newCountry);
        }
    }

    /** Searches the countryList attribute to return a country object with a name that matches the input parameter.
     * @param countryName the name of a country to search for
     * @return      the country associated with the search parameter
     * */
    public static Country countryFromName (String countryName) {
        Country newCountry = null;
        for (Country item : countryList) {
            if (item.getCountry().equals(countryName)) {
                newCountry = item;
            }
        }
        return newCountry;
    }
}
