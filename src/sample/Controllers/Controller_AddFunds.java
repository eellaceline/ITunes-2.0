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

public class Controller_AddFunds implements Initializable {

    @FXML
    private AnchorPane rootPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void handleAdd15Dollar(ActionEvent event) {

    }

    @FXML
    void handleAdd25Dollar(ActionEvent event) {

    }

    @FXML
    void handleAdd5Dollar(ActionEvent event) {

    }

    @FXML
    void handleBackToStore(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Store.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in handleBackToStore");
        }
    }

    @FXML
    void handleHelp(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Help");
        alert.setHeaderText("I will show you what to do here ↓");
        alert.setContentText("This is where you add funds to your account. You can of course not buy any songs without funding. Choose between 5$, 15$ and 25$. ");
        alert.showAndWait();

    }

}
