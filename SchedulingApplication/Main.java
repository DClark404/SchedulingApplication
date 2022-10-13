package SchedulingApplication;

import SchedulingApplication.DAO.*;
import SchedulingApplication.util.DBConnect;
import SchedulingApplication.util.LanguageUtility;
import SchedulingApplication.util.TimeUtility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/** The class that creates and runs an instance of the application. */
public class Main extends Application {

    /** Overrides the start method of the launch command.
     * This method creates the primary stage used by the application, setting it to the initial scene of the program.
     * @param primaryStage the stage object responsible for holding scenes in the application
     * @throws IOException if an input or output exception has occurred */
    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println("Scheduling Application is starting!");
        Parent root = FXMLLoader.load(getClass().getResource("/SchedulingApplication/View/LoginScreen.fxml"));

        if (Locale.getDefault().getLanguage().equals("fr")) {
            ResourceBundle rb = LanguageUtility.getRb();
            primaryStage.setTitle(rb.getString("programTitle"));
        } else
            primaryStage.setTitle("WGU Scheduling Application");

        primaryStage.setScene(new Scene(root, 414, 241));
        primaryStage.show();
    }

    /** Establishes the operations that occur on program launch. Begins by opening a connection to the database.
     * The database connection is the used to populate object models before beginning the launch method.
     * The database connection is closed when the program is exited.
     * @param  args any additional arguments passed into the main method
     * @throws SQLException if any database exception has occurred*/
    public static void main(String[] args) throws SQLException {

        DBConnect.startConnection("U05TfJ", "53688599900");

        TimeUtility.populateTimeList();
        CountryDAO.populateCountryList();
        FirstLevelDivisionDAO.populateDivisionList();
        UserDAO.populateUserList();
        ContactDAO.populateContactList();
        CustomerDAO.populateCustomerList();
        AppointmentDAO.populateAppointmentList();

        launch(args);
        DBConnect.closeConnection();
    }
}
