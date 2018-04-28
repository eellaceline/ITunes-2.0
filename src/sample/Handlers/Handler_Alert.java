package sample.Handlers;

import javafx.scene.control.Alert;

public class Handler_Alert {

    public static void alert(String title, String headerText, String contentText, boolean showAndWait) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        if (showAndWait)
            alert.showAndWait();

        else
            alert.show();
    }
}
