package sample.Controllers;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_Alert;
import sample.Models.Singletons.Database;
import sample.Models.Singletons.LoggedInUser;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_AccountSettings implements Initializable{

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField conFirmPassword;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void confirmPasswordField(ActionEvent event) {

        try {

            if (!conFirmPassword.getText().equals(passwordField.getText())) {

                System.out.println("Password confirmed");
                saveChanges();
            }
        }catch (Exception ex){
            Handler_Alert.alert(
                    "Error!",
                    "Wrong password",
                    "The passwords you have entered are not the same.",
                    false
            );
        }

    }


    @FXML
    void handleCancel(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Library.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in handleLogOut");
        }

    }

    @FXML
    void handleHelp(ActionEvent event) {
        Handler_Alert.information(
                "Help",
                "I will show you what to do here ↓",
                "This is where you can change your username and password. \nREMEMBER: You need to confirm your password.",
                false
        );
    }

    @FXML
    void saveChanges() {
        try {
           if ((Database.getInstance().changeUsername(LoggedInUser.getInstance().getUser().getUserName(), usernameField.getText()) &&
                   Database.getInstance().changePassword(LoggedInUser.getInstance().getUser().getUserName(), passwordField.getText()))) {
               System.out.println("error");
               clearChanges();
           }
        }catch (Exception ex){
            Handler_Alert.alert(
                    "Error!",
                    "Error in saving",
                    "You have not any changes to be saved.",
                    false
            );
        }

    }

    private void clearChanges(){
        usernameField.setText("");
        passwordField.setText("");
        conFirmPassword.setText("");
    }

}
