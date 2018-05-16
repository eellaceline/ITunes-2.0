package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_Alert;
import sample.Handlers.Handler_HelpCancel;
import sample.Models.Library;
import sample.Models.Singletons.Cart;
import sample.Models.Singletons.Database;
import sample.Models.Singletons.LoggedInUser;
import sample.Models.Song;
import sample.Models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller_Library implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<Song> tableView;

    @FXML
    private TableColumn<Song, String> songNameColumn, artistColumn, albumColumn , durationColumn;

    private ArrayList<Song> songList;

    private TextField searchSongField = new TextField();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.songList = Database.getInstance().getLibraryForUser();
        System.out.println(songList);
        final ObservableList<Song> data = FXCollections.observableArrayList();
        for (Song song: songList) {
            data.add(song);
        }

        songNameColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("songName"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("artistNames"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("album"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("length"));


        tableView.setItems(data);
    }

    public void handleHelp () {
        Handler_Alert.information(
                "Help",
                "I will show you what to do here ↓",
                "This is you library. You can either go to store, account " +
                            "\nor log out from the application. ",
                false
        );
    }

    public void paneChangeToStore () {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Store.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println("IOException found in paneChangeToStore");
            ex.printStackTrace();
        }

    }

    public void handleLogOut () {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Login.fxml"));
            rootPane.getChildren().setAll(pane);
            LoggedInUser.getInstance().setUser(null);
            Cart.getInstance().setSongList(null);
        }
        catch (IOException ex) {
            System.out.println("IOException found in handleLogOut");
        }

    }
    public void searchForSongs(){

    }

    public void handleAccountSettings () {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_AccountSettings.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println("IOException found in handleAccountSettings");
        }

    }

}
