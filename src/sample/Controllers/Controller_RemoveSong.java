package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_Alert;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_RemoveSong {


    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView logoView;

    @FXML
    void handleCancel(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Admin.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in handleCancel");
        }
    }

    @FXML
    void handleHelp(ActionEvent event) {
        Handler_Alert.information(
                "Help",
                "I will show you what to do here ↓",
                "Here you can remove songs. Mark which one you would like to" +
                        "\nremove and click on ''remove''. ",
                false
        );

    }

    @FXML
    void removeSong(ActionEvent event) {
        try {

        }catch (Exception ex){
            Handler_Alert.alert(
                    "Error!",
                    "Error in removing songs",
                    "You can not remove this song.",
                    false
            );
        }
    }

}
