package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML private AnchorPane rootPane;

    @FXML private ImageView logoView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image image = new Image(getClass().getResourceAsStream("photos/logo.png"));
        logoView.setImage(image);
        Database database = new Database();
        database.getAdminID();
    }

    public void changeUserScene() throws Exception {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("GUI_LoginRegister.fxml"));
        rootPane.getChildren().addAll(pane);


    }

}