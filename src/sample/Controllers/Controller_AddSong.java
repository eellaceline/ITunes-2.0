package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_Alert;

import java.io.IOException;
import java.util.logging.Handler;

public class Controller_AddSong {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView logoView;

    @FXML
    private TextField titleField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField durationField;

    @FXML
    private TextField albumField;

    @FXML
    private TextField artistField;

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
                "Here you can add songs. Enter title, artist, album, duration" +
                        "\nand price then click on save.",
                false
        );

    }

    @FXML
    void saveChanges(ActionEvent event) {

    }

}
