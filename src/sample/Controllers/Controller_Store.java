package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_Alert;

import sample.Models.Singletons.Cart;
import sample.Models.Singletons.Database;
import sample.Models.Singletons.LoggedInUser;
import sample.Models.Song;

import sample.Models.Singletons.LoggedInUser;
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
    private TableView<Song> tableView;

    @FXML
    private TableColumn<Song, String> columnSongName, columnArtist, columnDuration, columnAlbum, columnPrice;

    private ArrayList<Song> songList;
    private ArrayList<Song> songCart = new ArrayList<>();

    @FXML
    private TextField userBalanceField, searchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        final Tooltip userBalanceTooltip = new Tooltip();
        userBalanceTooltip.setText("This is your current balance");
        userBalanceField.setTooltip(userBalanceTooltip);

        userBalance();
                
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){

                if (!searchCartForExistingValue(tableView.getSelectionModel().getSelectedItem().getSongID())) {
                    songCart.add(tableView.getSelectionModel().getSelectedItem());
                    System.out.println("added song to cart");
                }

                else
                    Handler_Alert.alert(
                            "Error",
                            "Found duplicate",
                            "Cant add same song twice to the cart",
                            false
                    );
            }
        });

        this.songList = Database.getInstance().getStore();
        updateTable(songList);
    }

    public void updateTable(ArrayList<Song> songList) {
        final ObservableList<Song> data = FXCollections.observableArrayList();
        data.addAll(songList);

        columnSongName.setCellValueFactory(new PropertyValueFactory<Song, String>("songName"));
        columnArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artistNames"));
        columnAlbum.setCellValueFactory(new PropertyValueFactory<Song, String>("album"));
        columnDuration.setCellValueFactory(new PropertyValueFactory<Song, String>("length"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<Song, String>("price"));

        tableView.setItems(data);
    }

    public void searchForSongs() {

        ArrayList<Song> searchedSongList = new ArrayList<>();

        // searches if the searchField isn't empty
        if (!searchField.getText().equals("")) {
            for (Song song : songList) {
                if (song.getSongName().toLowerCase().startsWith(searchField.getText().toLowerCase())) {
                    searchedSongList.add(song);
                }
            }
            updateTable(searchedSongList);
        }
        // if it is empty it switches back to the original list
        else {
            updateTable(songList);
        }
    }

    // returns true if value exists, false if it doesnt exist
    public boolean searchCartForExistingValue(int songID) {
        if (songCart.size() != 0) {
            for (int i=0; i<songCart.size(); i++) {
                if (songCart.get(i).getSongID() == songID)
                    return true;
            }
        }
        else
            return false;

        return false;
    }

    @FXML
    void handleHelp(ActionEvent event) {
        Handler_Alert.information(
                "Help",
                "I will show you what to do here ↓",
                "This is where you buy your desired songs. " +
                        "\nMark the song or songs that you want and click on " +
                        "\nthe cart-button.",
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
            LoggedInUser.getInstance().setUser(null);
            Cart.getInstance().setSongList(null);
        }
        catch (IOException ex) {
            System.out.println("IOException found in handleLogOut");
        }
    }
    @FXML
    void addToCart () {
        try {
            Cart.getInstance().setSongList(songCart);
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Cart.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in addToCart");
            ex.getStackTrace();
        }
    }

    @FXML
    void userBalance(){
        userBalanceField.setText(Integer.toString(LoggedInUser.getInstance().getUser().getBalance()));
    }

}

