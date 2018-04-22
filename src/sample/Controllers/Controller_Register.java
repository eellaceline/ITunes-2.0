package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_Register implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button adminButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button helpButton;

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmField;

    @FXML
    private ImageView logoView;

    @FXML
    void handleCancel(ActionEvent event) {

    }

    @FXML
    void handleHelp(ActionEvent event) {

    }

    @FXML
    void registerAccount(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
