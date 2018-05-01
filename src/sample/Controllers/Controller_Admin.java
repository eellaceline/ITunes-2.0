package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_HelpCancel;

import java.io.IOException;

public class Controller_Admin implements Handler_HelpCancel {

    @FXML
    private AnchorPane rootPane;

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
