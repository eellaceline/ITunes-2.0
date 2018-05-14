package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_Alert;
import sample.Handlers.Handler_HelpCancel;
import sample.Models.Singletons.Cart;
import sample.Models.Singletons.LoggedInUser;

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

    @Override
    public void handleHelp() {
        Handler_Alert.information(
                "Help",
                "I will show you what to do here ↓",
                "This is for admins. Here you can remove and add songs, \n remove and add users and change price.",
                false
        );
    }

    @Override
    public void handleCancel() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Login.fxml"));
            rootPane.getChildren().setAll(pane);
            LoggedInUser.getInstance().setUser(null);
            Cart.getInstance().setSongList(null);
        }
        catch (IOException ex) {
            System.out.println("IOException found in handleCancel");
        }

    }
}
