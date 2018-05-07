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

        //Image image = new Image(getClass().getResourceAsStream("photos/logo.png"));
        //logoView.setImage(image);
    }


    public void paneChangeToUserAdmin() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Admin.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in paneChangeToUserAdmin");
        }

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

    }

    @FXML
    public void loginVerification() {
        String compare1 = Handler_Password.decryption(Database.getInstance().getPassword(userNameTextField.getText()));
        String compare2 = passwordTextField.getText();

        // checks if the textfields and values from database are matching
        if (compare1.equals(compare2)) {
            System.out.println("login success");

            User user = new User(Database.getInstance().getUser(userNameTextField.getText()));
            LoggedInUser.getInstance().setUser(user);
            System.out.println(user.getUserName());

            if (Database.getInstance().isAdmin(userNameTextField.getText())) {
                System.out.println("is admin");
                paneChangeToUserAdmin();
            }
            else {
                System.out.println("not admin");
                paneChangeToLibrary();
            }
        }
        else {
            System.out.println("login failed");
        }


    }

    @FXML
    void forgotPassword (){
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
        }
    }
}
