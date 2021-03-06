package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_Alert;
import sample.Models.Singletons.Database;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_ChangePrice implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView logoView;

    @FXML
    private TextField changePriceField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField artistField;

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
    void searchForSongs(){

    }

    @FXML
    void saveChanges(ActionEvent event) {
        try {
            System.out.println("*** Button Clicked ***");
            if (Database.getInstance().updatePrice(titleField.getText(), artistField.getText(), Integer.parseInt(changePriceField.getText()))) {
                resetChoice();
            }
        }catch (Exception ex){
            Handler_Alert.alert(
                    "Error!",
                    "Error in saving",
                    "You have not any changes to be saved.",
                    false
            );
        }
    }

    private void resetChoice() {
        titleField.setText("");
        artistField.setText("");
        changePriceField.setText("");
    }
}