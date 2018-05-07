package sample.Controllers;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Handler;

public class Controller_AccountSettings implements Initializable{

    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML   
    void changeUsernameField(){

    }

    @FXML
    void changePasswordField(ActionEvent event) {

        try {

        } catch (Exception ex) {
            Handler_Alert.alert(
                    "Error!",
                    "Error in changing password",
                    "Choose a password between 1-8.",
                    false
            );

        }
    }

    @FXML
    void confirmPasswordField(ActionEvent event) {
        try {

        } catch (Exception ex) {
            Handler_Alert.alert(
                    "Error!",
                    "Error in confirming password",
                    "Your passwords does not match",
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
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Help");
        alert.setHeaderText("I will show you what to do here ↓");
        alert.setContentText("This is where you can change your username and password. REMEMBER: You need to confirm your password.");
        alert.showAndWait();

    }

    @FXML
    void saveChanges(ActionEvent event) {
        try {

        }catch (Exception ex){
            Handler_Alert.alert(
                    "Error!",
                    "Error in saving settings",
                    "No changes have been done.",
                    false
            );
        }
    }

}
