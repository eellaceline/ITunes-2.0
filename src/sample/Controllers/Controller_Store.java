package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_Store implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView logoView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void handleHelp(ActionEvent event) { Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Help");
        alert.setHeaderText("I will show you what to do here ↓");
        alert.setContentText("This is where you buy your desired songs. You can sort by lowest and highest price. Mark the song or songs that you want and then click on the cart-button.");
        alert.showAndWait();

    }

    @FXML
    void AddFunds(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_AddFunds.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in AddFunds");
        }
    }

    @FXML
    void handleSort(ActionEvent event) {


    }

    @FXML
    void paneChangeToLibrary(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Library.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in paneChangeToLibrary");
        }
    }
    @FXML
    void handleLogOut () {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Login.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in handleLogOut");
        }
    }
    @FXML
    void addToCart () {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Cart.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in addToCart");
        }
    }

}
