package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_Login implements Initializable {

    Database database = new Database();

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

        Image image = new Image(getClass().getResourceAsStream("photos/logo.png"));
        logoView.setImage(image);
        Database database = new Database();
        database.getAdminID();
    }

    public void loginVerification() {
        if (database.getPassword(userNameTextField.getText()).equals(passwordTextField.getText()));
        {
            System.out.println("login success");
        }
    }

}