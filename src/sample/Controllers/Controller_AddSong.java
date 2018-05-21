package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_Alert;
import sample.Models.Singletons.Database;
import sample.Models.Song;
import sample.Models.User;

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
    private TextField genreField;

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
        Song song = Database.getInstance().getSong(titleField.getText(), artistField.getText(), genreField.getText());
        try {
            if(song == null) {
                Database.getInstance().addSong(titleField.getText(), durationField.getText(), Integer.parseInt(priceField.getText()) ,genreField.getText(), albumField.getText(), artistField.getText());
            }
            else {
                System.out.println("This song is already added");
            }
        }catch (Exception ex){
            Handler_Alert.alert(
                    "Error!",
                    "Error in saving ",
                    "You do not any changes to be saved.",
                    false
            );
        }
    }
}
