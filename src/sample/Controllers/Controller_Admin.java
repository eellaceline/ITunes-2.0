package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller_Admin implements Initializable{

    @FXML
    private AnchorPane rootPane;

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

    @FXML
    private ImageView logoView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public boolean removeSongs() {
        boolean success = false;

        return success;
    }

    public void addSongs() {

    }

    public boolean removeUser() {
        boolean success = false;

        return success;
    }

    public boolean changePrice() {
        boolean success = false;

        return success;
    }
}
