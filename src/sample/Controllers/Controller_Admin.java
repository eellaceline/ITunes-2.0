package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_HelpCancel;

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

    }
}
