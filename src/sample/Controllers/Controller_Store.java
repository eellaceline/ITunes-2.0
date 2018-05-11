package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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

public class Controller_Store implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView logoView;

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<Song, String> columnSongName, columnArtist, columnGenre, columnDuration, columnAlbum;

    private TableColumn<Song, Integer> columnSongID;

    private ArrayList<Song> songList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.songList = Database.getInstance().getStore();

        final ObservableList<Song> data = FXCollections.observableArrayList();
        for (Song song: songList) {
            data.add(song);
        }
        columnSongID = new TableColumn<>();
        tableView.getColumns().add(columnSongID);

        columnSongID.setCellValueFactory(new PropertyValueFactory<Song, Integer>("songID"));
        columnSongName.setCellValueFactory(new PropertyValueFactory<Song, String>("songName"));
        columnArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artistNames"));
        //columnGenre.setCellValueFactory(new PropertyValueFactory<Song, String>("genre"));
        columnDuration.setCellValueFactory(new PropertyValueFactory<Song, String>("length"));

        //columnGenre.visibleProperty().setValue(false);
        columnSongID.visibleProperty().setValue(false);

        tableView.setItems(data);
    }

    @FXML
    void handleHelp(ActionEvent event) {
        Handler_Alert.information(
                "Help",
                "I will show you what to do here ↓",
                "This is where you buy your desired songs. You can sort by the lowest " +
                        "\n and highest price. Mark the song or songs that you want and " +
                        "n click on the cart-button.",
                false
        );
    }

    @FXML
    void AddFunds(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_AddFunds.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in AddFunds");
        }
    }

    @FXML
    void handleSort(ActionEvent event) {

    }

    @FXML
    void paneChangeToLibrary(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Library.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in paneChangeToLibrary");
        }
    }
    @FXML
    void handleLogOut () {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Login.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in handleLogOut");
        }
    }
    @FXML
    void addToCart () {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Cart.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in addToCart");
        }
    }

}
