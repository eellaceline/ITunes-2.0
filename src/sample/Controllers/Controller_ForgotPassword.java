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
import sample.Handlers.Handler_Password;
import sample.Handlers.Handler_SendEmail;
import sample.Models.Singletons.Database;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller_ForgotPassword implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField emailField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void handleCancel(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Login.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println("IOException found in handleCancel");
        }
    }

    @FXML
    void handleHelp(ActionEvent event) {
        Handler_Alert.information(
                "Help",
                "I will show you what to do here ↓",
                "Here you can receive a new password if you have forgotten " +
                        "\nyour old password. " +
                        "Enter your email address " +
                        "\nthat you used when your registered your account and we will" +
                        "\nsend you a new password. ",
                false
        );
    }


    @FXML
    void sendEmail(ActionEvent event) {
        String newPW = Handler_Password.generateRandomPassword();
        try {
            Handler_SendEmail.sendMail(
                    "Forgot password",
                    "Dear " + emailField.getText() +
                            "\n" +
                            "\nYou have requested a new password that you need in order to log in. The password in this email is " +
                            "\nrandom a random generated that you might want to change when you log in." +
                            "\n\nYour new password is: " + newPW +
                            "\n\nHave a good day!"
            );
            Handler_Alert.information(
                    "Information",
                    "Dear customer",
                    "You have requested a new password." +
                            "\nCheck your email for the new password.",
                    false
            );

            Database.getInstance().updatePassword(emailField.getText(), newPW);

        } catch (NullPointerException ex) {
            ex.printStackTrace();

            Handler_Alert.alert(
                    "Error!",
                    "NullPointerException",
                    "You cant leave this field empty",
                    false
            );
        }
    }
}



