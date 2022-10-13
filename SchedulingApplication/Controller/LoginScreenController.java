package SchedulingApplication.Controller;

import SchedulingApplication.DAO.UserDAO;
import SchedulingApplication.Model.User;
import SchedulingApplication.util.LanguageUtility;
import SchedulingApplication.util.TimeUtility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/** The controller class for the login screen. Contains FXML ID attributes and event handlers for every field, label,
 * and button.
 * */
public class LoginScreenController {

    @FXML
    private Label companyLabel;

    @FXML
    private Label serviceLabel;

    @FXML
    private Label userLoginHeader;

    @FXML
    private Label userIDLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField textUserIDField;

    @FXML
    private TextField textPasswordField;

    @FXML
    private Button buttonLogin;

    @FXML
    private Label regionLabel;

    @FXML
    private Label labelDetectedRegion;

    /** Initializes the GUI by setting all attributes to their respective values. This initialization method also
     * utilizes localization through resourceBundles to translate all labels, text, and error control messages if
     * the system locale is in french.
     * */
    @FXML
    public void initialize() {
        labelDetectedRegion.setText(Locale.getDefault().getCountry());
        
        if (Locale.getDefault().getLanguage().equals("fr")) {
            ResourceBundle rb = LanguageUtility.getRb();

            companyLabel.setText(rb.getString("company"));
            serviceLabel.setText(rb.getString("service"));
            serviceLabel.setLayoutX(92);
            userLoginHeader.setText(rb.getString("loginHeader"));
            userLoginHeader.setLayoutX(68);
            userIDLabel.setText(rb.getString("userID"));
            passwordLabel.setText(rb.getString("password"));
            regionLabel.setText(rb.getString("region"));
            buttonLogin.setText(rb.getString("login"));
            buttonLogin.setLayoutX(170);
            textUserIDField.setPromptText(rb.getString("userPrompt"));
            textPasswordField.setPromptText(rb.getString("passwordPrompt"));
        }
    }

    /** Collects user input from text fields and determines if this is a valid login attempt.
     * First records any users attempt at a login into a text document saved to the root of the application, including
     * whether it was valid and the time. Method will then either proceed to the MainCalender screen or produce an error
     * dialog box if the login information was incorrect. If the login information is correct, after switching screens
     * the method will call TimeUtility.detectUpcomingAppointments and show an information alert immediately.
     * @param event the button is clicked
     * @throws IOException if an input or output exception has occurred
     * */
    @FXML
    public void attemptUserLogin(MouseEvent event) throws IOException {
        String username = "not-registered";
        String password = null;
        int currentUser = 0;
        String textUsername = textUserIDField.getText().trim();
        String textPassword = textPasswordField.getText().trim();

        for (User item : UserDAO.getUserList()) {
            if (item.getUserName().equals(textUsername)) {
                username = item.getUserName();
                password = item.getPassword();
                currentUser = item.getUserID();
            }
        }
        boolean validLogin = textUsername.equals(username) && textPassword.equals(password);

        FileWriter fileLogger = new FileWriter("login_activity.txt", true);
        String loginValidity = " gave invalid log-in at ";
        if (validLogin) {
            loginValidity = " successfully logged in at ";
        }
        String currentTime = TimeUtility.getCurrentTimestamp().toString();
        String currentTZ = ZoneId.of(TimeZone.getDefault().getID()).toString();
        fileLogger.write("User " + textUsername + loginValidity + currentTime + " " + currentTZ + "\n");
        fileLogger.close();

        if (validLogin) {
            Parent root = FXMLLoader.load(getClass().getResource("/SchedulingApplication/View/MainCalender.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

            TimeUtility.detectUpcomingAppointments(currentUser);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);

            if (Locale.getDefault().getLanguage().equals("fr")) {
                ResourceBundle rb = LanguageUtility.getRb();

                alert.setTitle(rb.getString("warning"));
                alert.setHeaderText(rb.getString("headerText"));
                alert.setContentText(rb.getString("contentText"));
                Button okButton = (Button) alert.getDialogPane().lookupButton( ButtonType.OK );
                okButton.setText(rb.getString("ok"));
            } else {
                alert.setTitle("Warning");
                alert.setHeaderText("Incorrect Login Information!");
                alert.setContentText("The username or password is incorrect.");
            }
            alert.showAndWait();
        }
    }

}
