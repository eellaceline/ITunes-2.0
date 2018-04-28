package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import sample.Handlers.Handler_HelpCancel;
import sample.Handlers.Handler_Password;
import sample.Models.Singletons.Database;
import sample.Models.Singletons.LoggedInUser;
import sample.Models.User;

public class Controller_Login implements Initializable, Handler_HelpCancel {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button userButton;

    @FXML
    private Button adminButton;

    @FXML
    private ImageView logoView;

    @FXML
    private TextField userNameTextField, passwordTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

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


    public void paneChangeToUserAdmin() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_UserAdmin.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in paneChangeToUserAdmin");
        }
    }

    public void paneChangeToLibrary() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Library.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in paneChangeToLibrary");
        }
    }

    public void handleHelp() {

    }

    public void handleCancel() {

    }

}
