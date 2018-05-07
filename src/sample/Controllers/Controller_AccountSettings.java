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

public class Controller_AccountSettings implements Initializable{

    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    void changeUsernameField(ActionEvent event){

    }

    @FXML
    void changePasswordField(ActionEvent event) {


    }

    @FXML
    void confirmPasswordField(ActionEvent event) {

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
                "This is where you can chang your username and password. \n REMEMBER: You need to confirm your password.",
                false
        );
    }

    @FXML
    void saveChanges(ActionEvent event) {

    }

}
