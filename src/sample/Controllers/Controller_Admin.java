package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_HelpCancel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_Admin implements  Handler_HelpCancel, Initializable {

    @FXML
    private AnchorPane rootPane;

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

    public void handleSettings() {

    }

    @Override
    public void handleHelp() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Help");
        alert.setHeaderText("I will show you what to do here ↓");
        alert.setContentText("This scene is for admin. You can in this scene remove and add songs, remove and add users and change price of a song.");
        alert.showAndWait();
    }

    @Override
    public void handleCancel() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Login.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in handleCancel");
        }

    }
}
