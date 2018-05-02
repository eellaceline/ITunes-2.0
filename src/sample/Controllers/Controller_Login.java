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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import sample.Models.*;

public class Controller_Login implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView logoView;

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button signInButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button cancelButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Image image = new Image(getClass().getResourceAsStream("photos/logo.png"));
        //logoView.setImage(image);
    }

    // missing encryption
    public void loginVerification() {
        String compare1 = Database.getInstance().getPassword(userNameTextField.getText());
        String compare2 = passwordTextField.getText();

        // checks if the textfields and values from database are matching
        // will use decryption before
        if (compare1.equals(compare2)) {
            System.out.println("login success");
            if (Database.getInstance().isAdmin(userNameTextField.getText())) {
                System.out.println("is admin");
                paneChangeToUserAdmin();
            }
            else {
                System.out.println("not admin");
                paneChangeToUserLibrary();
            }
        }
        else {
            System.out.println("login failed");
        }
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

    public void paneChangeToUserLibrary() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Library.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in paneChangeToUserAdmin");
        }
    }

    @FXML
    void handleCancel(ActionEvent event) {

    }

    @FXML
    void handleHelp(ActionEvent event) {

    }

    @FXML
    void loginVerification(ActionEvent event) {

    }

}
