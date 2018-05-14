package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import sample.Models.Order;
import sample.Models.User;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_Cart implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField totalPriceField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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


       try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_Verification.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println("IOException found in handleLogOut");
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
