package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_Alert;
import sample.Models.Singletons.Database;
import sample.Models.Singletons.LoggedInUser;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_AddFunds implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML private Button fiveDollar, tenDollar, twentyDollar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    void handleAdd5Dollar(ActionEvent event) {
        LoggedInUser.getInstance().getUser().addBalance(5);
        System.out.println(LoggedInUser.getInstance().getUser().getBalance());
    }

    @FXML
    void handleAdd10Dollar(ActionEvent event) {
        LoggedInUser.getInstance().getUser().addBalance(10);
        System.out.println(LoggedInUser.getInstance().getUser().getBalance());
    }

    @FXML
    void handleAdd20Dollar(ActionEvent event) {
        LoggedInUser.getInstance().getUser().addBalance(20);
        System.out.println(LoggedInUser.getInstance().getUser().getBalance());
    }

    @FXML
    void handleBackToStore(ActionEvent event) {
        Database.getInstance().changeBalance();

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
        Handler_Alert.information(
                "Help",
                "I will show you what to do here ↓",
                "This is where you add funds to your account. \nChoose between 5$, 15$ and 25$.",
                false
        );

    }

}
