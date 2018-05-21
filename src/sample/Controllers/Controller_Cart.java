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
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_Alert;
import sample.Models.Singletons.Cart;
import sample.Models.Singletons.Database;
import sample.Models.Singletons.LoggedInUser;
import sample.Models.Song;

import sample.Models.Order;
import sample.Models.User;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

        final Tooltip totalPriceTooltip = new Tooltip();
        totalPriceTooltip.setText("This is the total price of the songs");
        totalPriceField.setTooltip(totalPriceTooltip);

    }

    @FXML
    void buy(ActionEvent event) {

       /* try {
            PDF pdf = new PDF();
            PDFPage page = pdf.newPage("A4");

            PDFStyle pdfStyle = new PDFStyle();
            pdfStyle.setFont(new StandardFont(StandardFont.HELVETICA), 20);
            pdfStyle.setFillColor(Color.PINK);

            page.setStyle(pdfStyle);
            page.drawText("This is your receipt", 100, page.getHeight()-100);
            page.drawText("test", 300, page.getHeight()-100);

            OutputStream fOut = new FileOutputStream("Reciept.pdf");
            pdf.render(fOut);
            fOut.close();

        }catch (IOException ex){
            ex.printStackTrace();
        }
        */

       if (LoggedInUser.getInstance().getUser().getBalance() >= Integer.parseInt(totalPriceField.getText())) {
           ArrayList<Integer> songID = new ArrayList<>();

           for(Song song: Cart.getInstance().getSongList()) {
               songID.add(song.getSongID());
           }

           Database.getInstance().addSongsForUser(songID);
           LoggedInUser.getInstance().getUser().reduceBalance(Integer.parseInt(totalPriceField.getText()));
           Database.getInstance().changeBalance();

           try {
               AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Verification.fxml"));
               rootPane.getChildren().setAll(pane);
           }
           catch (IOException ex) {
               System.out.println("IOException found in handleLogOut");
               ex.getStackTrace();
           }
       }
       else {
           Handler_Alert.alert(
                   "Error",
                   "Balance",
                   "Not enough funds",
                   true);
       }


    }

    @FXML
    void handleCancel() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Store.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println("IOException found in handleCancel");
        }

    }
}
