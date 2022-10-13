package SchedulingApplication.util;

import java.sql.*;

/** Utility class that handles functions related to the database connection. */
public class DBConnect {

    /** Contains the URL needed to establish a database connection. */
    private static final String jdbcURL = "jdbc:mysql://wgudb.ucertify.com/WJ05TfJ";
    /** Contains the database connection reference. */
    private static Connection databaseConnection = null;
    /** Contains and executes the queries used to access and modify database information. */
    private static PreparedStatement statement;

    /** Starts a connection with the database using the JDBC driver.
     * @param password The password required for database access
     * @param username the username required for database access
     * @throws SQLException if any database exception has occurred
     * */
    public static void startConnection (String username, String password) throws SQLException {
        System.out.println("Attempting connection to database!");
        databaseConnection = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Database connection was successful!");
    }

    /** Closes any referenced connection with the database.
     * @throws SQLException if any database exception has occurred
     * */
    public static void closeConnection () throws SQLException {
        databaseConnection.close();
        System.out.println("Database connection has closed!");
    }

    /** Sets the preparedStatement attribute with an SQL command.
     * @param sqlStatement The SQL command to be passed into the preparedStatement.
     * @throws SQLException if any database exception has occurred
     * */
    public static void setPreparedStatement(String sqlStatement) throws SQLException {
        statement = databaseConnection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
    }

    /** Returns the primary key generated after running a preparedStatement.
     * Used for populating model objects with accurate reference ID numbers after running update or create commands.
     * @return      the keys generated from the last execution of the preparedStatement
     * @throws SQLException if any database exception has occurred
     * */
    public static int returnGeneratedKey() throws SQLException {
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }

    /** Returns a reference to the preparedStatement object.
     * This reference can be used to run the SQL commands or obtain ResultSets.
     * @return     a reference to the preparedStatement object
     * */
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
}
