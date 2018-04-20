package sample2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_Admin implements Initializable {

    @FXML
    private Button addSongButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button removeUserButton;

    @FXML
    private Button changePriceButton;

    @FXML
    private TextField addSongField;

    @FXML
    private TextField removeUserField;

    @FXML
    private TextField changePriceField;

    @FXML
    private TextField removeSongField;

    @FXML
    private Button removeSongButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
