package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sample.Handlers.Handler_Alert;
import sample.Handlers.Handler_Password;
import sample.Models.Singletons.Database;
import sample.Models.Singletons.LoggedInUser;
import sample.Models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class Controller_Login implements Initializable {

    @FXML private AnchorPane rootPane;

    @FXML private ImageView logoView;

    @FXML private TextField userNameTextField;

    @FXML private PasswordField passwordTextField;

    @FXML private Button signInButton;

    @FXML private Button helpButton;

    @FXML private Button cancelButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void handleCancel(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_LoginRegister.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in handleCancel");
        }

    }

    @FXML
    void handleHelp(ActionEvent event) {
        Handler_Alert.information("Help",
                "I will show you what to do here ↓",
                "This is where you log in with your created account. \n If you do not remember your password, click on \n ''forgot password?'' :)",
                false);
    }

    @FXML
    public void loginVerification() {
        System.out.println("logging in...");
        long startTime = System.nanoTime();

        String compare1 = "";
        String compare2 = "";
        try {
            compare1 = Handler_Password.decryption(Database.getInstance().getPassword(userNameTextField.getText()));
            compare2 = passwordTextField.getText();
        }
        catch(NullPointerException ex) {
            Handler_Alert.alert(
                    "Error",
                    "NullPointerException",
                    "Must enter a value to sign in",
                    false);
        }

        // checks if the textfields and values from database are matching
        if (compare1.equals(compare2)) {
            System.out.println("login success");

            User user = null;
            try {
                user = new User(Database.getInstance().getUser(userNameTextField.getText()));
            }
            catch (NullPointerException ex) {

            }

            if (user != null) {
                LoggedInUser.getInstance().setUser(user);

                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                System.out.println("login time(ms): "+duration/1000000);

                if (Database.getInstance().isAdmin(userNameTextField.getText())) {
                    System.out.println("is admin");
                    paneChangeToUserOrAdmin();
                }
                else {
                    System.out.println("not admin");
                    paneChangeToLibrary();
                }
            }
            else {
                Handler_Alert.alert(
                        "Error",
                        "NullPointerException",
                        "cant leave empty values",
                        false);
            }
        }
        else {
            Handler_Alert.alert(
                    "Error",
                    "Login failed",
                    "Login failed",
                    false);
        }
    }

    @FXML
    public void forgotPassword (){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_ForgotPassword.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in forgotPassword");
        }


    }
    public void paneChangeToLibrary (){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Library.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in paneChangeToLibrary");
            ex.getStackTrace();
        }
    }

    public void paneChangeToUserOrAdmin() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_UserOrAdmin.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in paneChangeToLibrary");
        }
    }
}
