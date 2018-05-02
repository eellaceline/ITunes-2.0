package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_HelpCancel;
import sample.Models.Singletons.Database;
import sample.Models.Singletons.LoggedInUser;
import sample.Models.Song;
import sample.Models.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller_Library implements Initializable, Handler_HelpCancel {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<Song, String> columnSongName, columnArtist, columnGenre, columnDuration;

    private ArrayList<Song> songList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = LoggedInUser.getInstance().getUser();
        this.songList = Database.getInstance().getLibraryForUser(user.getUserName());

        final ObservableList<Song> data = FXCollections.observableArrayList();
        for (Song song: songList) {
            data.add(song);
        }

        columnSongName.setCellValueFactory(new PropertyValueFactory<Song, String>("songName"));
        columnArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artistNames"));
        columnGenre.setCellValueFactory(new PropertyValueFactory<Song, String>("genre"));
        columnDuration.setCellValueFactory(new PropertyValueFactory<Song, String>("length"));

        columnDuration.visibleProperty().setValue(false);

        tableView.setItems(data);
    }

    public void handleHelp() {

    }

    public void handleCancel() {

    }

    public void handleSettings() {

    }


}
