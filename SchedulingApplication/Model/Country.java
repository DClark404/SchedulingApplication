package SchedulingApplication.Model;

/** The class representing server-side Country data. */
public class Country {
    private int countryID;
    private String country;

    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    @Override
    public String toString(){
        return ("[" + countryID + "] " + country);
    }

    /** Setter for the countryID attribute.
     * @param countryID the unique ID of the country
     * */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /** Setter for the country attribute.
     * @param country the name of the country
     * */
    public void setCountry(String country) {
        this.country = country;
    }

    /** Getter for the countryID attribute.
     * @return      the unique ID of the country
     * */
    public int getCountryID() {
        return countryID;
    }

    /** Getter for the country attribute.
     * @return      the name of the country
     * */
    public String getCountry() {
        return country;
    }

}
