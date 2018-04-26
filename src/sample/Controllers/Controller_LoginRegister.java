package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Models.*;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_LoginRegister implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView logoView;

    @FXML
    private Button signInButton;

    @FXML
    private Button createAccButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button helpButton;





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Image image = new Image(getClass().getResourceAsStream("../photos/logo.png"));
        //logoView.setImage(image);
    }

    public void paneChangeToLogin() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Login.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println("IOException found in paneChangeToLogin");
        }
    }

        public void paneChangeToRegister(){
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Register.fxml"));
                rootPane.getChildren().setAll(pane);
            } catch (IOException ex) {
                System.out.println("IOException found in paneChangeToRegister");
            }

    }


    @FXML
    void handleCancel(ActionEvent event) {

    }

    @FXML
    void handleHelp(ActionEvent event) {

    }

}