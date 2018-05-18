package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class Controller_UserOrAdmin {
    @FXML
    private AnchorPane rootPane;

    public void paneChangeToAdmin() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Admin.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in paneChangeAdmin");
        }
    }

    public void paneChangeToLibrary() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Library.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in paneChangeToLibrary");
        }
    }
}
