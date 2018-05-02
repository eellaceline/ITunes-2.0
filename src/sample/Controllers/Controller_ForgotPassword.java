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

public class Controller_ForgotPassword implements Initializable{

    @FXML private AnchorPane rootPane;


    @FXML void handleCancel(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Login.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in handleCancel");
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void handleHelp(ActionEvent event) {
        Handler_Alert.information(
                "Help",
                "I will show you what to do here ↓",
                "Here you can reviece a new password if you have forgotten " +
                        "\nyour old password. " +
                        "Enter your email address " +
                        "\nthat you used when your registered your account and we will" +
                        "\nsend you a new one. ",
                false
        );
    }

    @FXML
    void loginVerification(ActionEvent event) {

    }


}
