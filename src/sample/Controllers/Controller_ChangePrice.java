package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;
import sample.Handlers.Handler_Alert;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller_ChangePrice implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField searchField;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

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
    void searchForSongs(){

        //TODO Arraylist<Song> for the search-function
        String[] testSongs = {"Fix you", "Scientist", "First", "fecond", "fhird"};
        TextFields.bindAutoCompletion(searchField, testSongs);


    }



    @FXML
    void handleHelp(ActionEvent event) {
        Handler_Alert.information(
                "Help",
                "I will show you what to do here ↓",
                "Here you can change price of a song. Enter the title followed" +
                        "\nwith artist and the desired price.",
                false
        );

    }

    @FXML
    void saveChanges(ActionEvent event) {

    }

}
