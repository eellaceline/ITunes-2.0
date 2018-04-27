package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_ForgotPassword implements Initializable{

    @FXML private AnchorPane rootPane;

    @FXML private ImageView logoView;

    @FXML private Button signInButton;

    @FXML private Button helpButton;

    @FXML private Button cancelButton;

    @FXML private TextField emailField;

    @FXML void handleCancel(ActionEvent event) {

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void handleHelp(ActionEvent event) {

    }

    @FXML
    void loginVerification(ActionEvent event) {

    }


}
