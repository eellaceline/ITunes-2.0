package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_HelpCancel;
//import sample.Models.Singletons.Database;
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
    private TableColumn<Song, String> columnSongName, columnArtist, columnGenre, columnDuration;

    private ArrayList<Song> songList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      /*  User user = LoggedInUser.getInstance().getUser();
        this.songList = Database.getInstance().fetchLibraryForUser(user.getUserName());

        System.out.println(songList.get(0));

        final ObservableList<Song> data = FXCollections.observableArrayList();
        for (Song song: songList) {
            data.add(song);
        }
        columnSongName.setCellValueFactory(new PropertyValueFactory<Song, String>("songName"));
        columnArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artistNames"));
        columnGenre.setCellValueFactory(new PropertyValueFactory<Song, String>("genre"));
        columnDuration.setCellValueFactory(new PropertyValueFactory<Song, String>("length"));

        tableView.setItems(data);
    }*/
    }
    public void handleHelp () {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Help");
        alert.setHeaderText("I will show you what to do here ↓");
        alert.setContentText("This is your library. You have the option to go to store, settings, account and log out");
        alert.showAndWait();

    }

    public void paneChangeToStore () {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Store.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println("IOException found in paneChangeToStore");
        }

    }

    public void handleLogOut () {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Login.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println("IOException found in handleLogOut");
        }

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

