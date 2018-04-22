package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import sample.Models.*;

public class Controller implements Initializable{


    // this class will is most likely not of us.
    // its still left in the project for compiling reasons that might not be a problem any more


    @FXML private AnchorPane rootPane;

    @FXML private Button userButton;

    @FXML private Button adminButton;

    @FXML private ImageView logoView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image image = new Image(getClass().getResourceAsStream("../photos/logo.png"));
        logoView.setImage(image);
    }

    public void changeUserScene() throws Exception {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("GUI_LoginRegister.fxml"));
        rootPane.getChildren().setAll(pane);
    }

}