package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_Alert;
import sample.Models.Singletons.Database;
import sample.Models.User;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller_Userlist implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<User> userlistTableView;

    @FXML
    private TableColumn<User, Integer> useridColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;


    private ArrayList<User> userlist;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userlist = Database.getInstance().getUsers();

        ObservableList<User> data = FXCollections.observableArrayList();
        for (User user : userlist){
            data.add(user);
        }

        useridColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("userID"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));

        userlistTableView.setItems(data);


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
