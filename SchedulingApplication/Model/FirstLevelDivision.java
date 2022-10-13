package SchedulingApplication.Model;

/** The class representing server-side First Level Division data. */
public class FirstLevelDivision {
    private int divisionID;
    private String division;
    private int countryID;

    public FirstLevelDivision(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    @Override
    public String toString(){
        return ("[" + divisionID) + "] " + division; }

    /** Setter for the divisionID attribute.
     * @param divisionID the unique ID of the division
     * */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /** Setter for the division attribute.
     * @param division the name of the division
     * */
    public void setDivision(String division) {
        this.division = division;
    }

    /** Setter for the countryID attribute.
     * @param countryID the unique ID of the country
     * */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /** Getter for the divisionID attribute.
     * @return      the unique ID of the division
     * */
    public int getDivisionID() {
        return divisionID;
    }

    /** Getter for the division attribute.
     * @return      the name of the division
     * */
    public String getDivision() {
        return division;
    }

    /** Getter for the countryID attribute.
     * @return      the unique ID of the country
     * */
    public int getCountryID() {
        return countryID;
    }
}
