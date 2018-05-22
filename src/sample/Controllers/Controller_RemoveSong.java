package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_Alert;
import sample.Models.Singletons.Database;
import sample.Models.Singletons.LoggedInUser;
import sample.Models.Song;
import sample.Models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller_RemoveSong implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView logoView;

    @FXML
    private TableView<Song> tableView;

    @FXML
    private Button removeSongButton;

    @FXML
    private TableColumn<Song, String> columnSongName, columnArtist, columnAlbum, columnDuration, columnPrice;

    private TableColumn<Song, Integer> columnSongID;

    private ArrayList<Song> songlist;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.songlist = Database.getInstance().getAllSong();

        final ObservableList<Song> data = FXCollections.observableArrayList();
        for (Song song: songlist) {
            data.add(song);
        }

        columnSongName.setCellValueFactory(new PropertyValueFactory<Song, String>("songName"));
        columnArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artistNames"));
        columnAlbum.setCellValueFactory(new PropertyValueFactory<Song, String>("album"));
        columnDuration.setCellValueFactory(new PropertyValueFactory<Song, String>("length"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<Song, String>("price"));

        tableView.setItems(data);
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
                "Here you can remove songs. Mark which one you would like to" +
                        "\nremove and click on ''remove''. ",
                false
        );

    }

    @FXML
    void removeSong(ActionEvent event) {
        try {
            Database.getInstance().deleteSong(tableView.getSelectionModel().getSelectedItem().getSongID());
            tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());

            Handler_Alert.information(
                    "Successful Remove",
                    "Song removed",
                    "You have removed a song",
                    false
            );
        }catch (Exception ex){
            Handler_Alert.alert(
                    "Error!",
                    "Error in removing songs",
                    "Unable to comply remove",
                    false
            );
        }
    }
}