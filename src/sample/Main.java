package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.Handlers.Handler_SendEmail;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI/GUI_LoginRegister.fxml"));
        primaryStage.setTitle("Soundwave");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("sample/photos/soundwave_music_wave_sound_audio-512.png"));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
