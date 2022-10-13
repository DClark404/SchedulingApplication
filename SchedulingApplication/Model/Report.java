package SchedulingApplication.Model;

/** Class representing a type of data report.
 * Attributes contained within a report object may not all be initialized in every method, and data contained
 * within the attributes may vary. For example, one ReportUtility method populates the reportID attribute with
 * concatenated string pairs to use as unique identifiers when generating a list of reports, while another method
 * populates the same attribute with customer names to use as a unique identifier. Both the visual GUI associated with
 * this type of data report and this class are capable of expansion as needed.
 * */
public class Report {
    private String reportID;
    private String reportMonth;
    private String reportType;
    private int reportCount;
    private long reportDuration;

    /** Getter for the reportType attribute.
     * @return      the type of report
     * */
    public String getReportType() {
        return reportType;
    }

    /** Setter for the reportType attribute.
     * @param reportType associated appointment type
     * */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    /** Getter for the reportCount attribute.
     * @return      the reports counter
     * */
    public int getReportCount() {
        return reportCount;
    }

    /** Setter for the reportCount attribute.
     * @param reportCount an incrementing integer
     * */
    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }

    /** Getter for the reportMonth attribute.
     * @return      the saved month
     * */
    public String getReportMonth() {
        return reportMonth;
    }

    /** Setter for the reportMonth attribute.
     * @param reportMonth a calender month
     * */
    public void setReportMonth(String reportMonth) {
        this.reportMonth = reportMonth;
    }

    /** Getter for the reportID attribute.
     * @return      the unique identifier
     * */
    public String getReportID() {
        return reportID;
    }

    /** Setter for the reports ID.
     * @param reportID the unique identifier
     * */
    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    /** Getter for the reportDuration attribute.
     * @return      a duration of time
     * */
    public long getReportDuration() {
        return reportDuration;
    }

    /** Setter for the reportDuration attribute.
     * @param reportDuration a duration of time
     * */
    public void setReportDuration(long reportDuration) {
        this.reportDuration = reportDuration;
    }
}


