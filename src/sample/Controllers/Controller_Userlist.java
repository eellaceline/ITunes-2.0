package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_Alert;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_Userlist implements Initializable {

    @FXML
    private AnchorPane rootPane;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private void handleCancel(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Admin.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in handleCancel");
        }

    }
    @FXML
    private void handleHelp(){
        Handler_Alert.information(
                "Help",
                "I will show you what to do here ↓",
                "This is where you as admin can see a list of all the users.",
                false
                );
    }
}
