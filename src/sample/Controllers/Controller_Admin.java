package sample.Controllers;

import javafx.event.ActionEvent;
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

    @FXML
    void paneChangeToAddSong(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_AddSong.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println("IOException found in paneChangeToAddSong");
        }
    }

    @FXML
    void paneChangeToChangePrice(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_ChangePrice.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println("IOException found in paneChangeToChangePrice");
        }
    }

    @FXML
    void paneChangeToRemoveSong(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_RemoveSong.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println("IOException found in paneChangeToRemoveSong");
        }
    }

    @FXML
    void paneChangeToRemoveUser(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_RemoveUser.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println("IOException found in paneChangeToRemoveUser");
        }
    }

    @Override
    public void handleHelp() {
        Handler_Alert.information(
                "Help",
                "I will show you what to do here ↓",
                "This is for admins. Here you can remove and add songs, " +
                        "\nremove and add users and change price.",
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
