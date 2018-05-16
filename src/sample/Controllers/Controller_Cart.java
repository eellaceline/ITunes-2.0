package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Models.Singletons.Cart;
import sample.Models.Song;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller_Cart implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ListView<Song> listView;

    @FXML
    private TextField totalPriceField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        int totalPrice = 0;
        final ObservableList<Song> data = FXCollections.observableArrayList();

        for (Song song: Cart.getInstance().getSongList()) {
            data.add(song);
            totalPrice += song.getPrice();
        }

        listView.setCellFactory(param -> new ListCell<Song>() {
            @Override
            protected void updateItem(Song item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getSongName() == null) {
                    setText(null);
                }
                else {
                    setText(item.getPrice() + "$" + "\t\t" + item.getSongName() + "\t\t" + item.getArtistNames() + "\t\t" + item.getAlbum() );
                }
            }
        });

        listView.setItems(data);
        totalPriceField.setText(Integer.toString(totalPrice));
    }

    @FXML
    void buy(ActionEvent event) {

        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Verification.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in handleLogOut");
            ex.getStackTrace();
        }

    }

}
