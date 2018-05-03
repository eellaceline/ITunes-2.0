package sample.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Handlers.Handler_Alert;
import sample.Models.Singletons.Database;
import sample.Models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_Register implements Initializable {

    String regexUserName = "[A-Za-z0-9]{1,8}";//1 to 8 characters, any letter and any number
    String regexEmail = "[^ ;]{1,}[@]{1}[^@ ;]{1,}[.]{1}[A-Za-z]{1,}";
    String regexPassword = "[^ ;]{1,8}";//NOT spaces, and only 1 to 8 characters
    //These booleans are added to work along with the button to check if everything is fine before doing its action
    boolean usernameBoolean = false;
    boolean emailBoolean = false;
    boolean passwordBoolean = false;
    boolean repeatPasswordBoolean = false;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button createAccountButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button helpButton;
    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmField;
    @FXML
    private ImageView logoView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //This will request focus on the pane so the fields will update since they are updating the moment they lose focus
        rootPane.setOnMouseClicked(event -> rootPane.requestFocus());
        //userName
        userNameTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                //!newValue means that the focus will NOT be on the field anymore, therefore we want to update if it's correct or not
                if (!newValue) {
                    //set boolean with error since regex will not match
                    if (!userNameTextField.getText().matches(regexUserName)) {
                        userNameTextField.setId("wrongTextField");
                        usernameBoolean = false;
                    } else { //set boolean as correct, since the regex matches
                        userNameTextField.setId("normalTextField");
                        usernameBoolean = true;
                    }
                } else {
                    //Do nothing specific when the focus actually goes to the TextField
                }
            }
        });
        //email
        emailField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    //set boolean with error since regex will not match
                    if (!emailField.getText().matches(regexEmail)) {
                        emailBoolean = false;
                        emailField.setId("wrongTextField");
                    } else { //set boolean as correct, since the regex matches
                        emailBoolean = true;
                        emailField.setId("normalTextField");
                    }
                } else {
                    //Do nothing specific when the focus actually goes to the TextField
                }
            }
        });
        //password
        passwordField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    //set boolean with error since regex will not match
                    if (!passwordField.getText().matches(regexPassword)) {
                        passwordField.setId("wrongTextField");
                    } else { //set boolean as correct, since the regex matches
                        passwordBoolean = true;
                        passwordField.setId("normalTextField");
                    }
                } else {
                    //Do nothing specific when the focus actually goes to the TextField
                }
            }
        });
        //passwordConfirm
        confirmField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    //if it does not match the password OR if it IS equals to nothing, considered as RED
                    if (!confirmField.getText().equals(passwordField.getText()) || confirmField.getText().equals("")) {
                        confirmField.setId("wrongTextField");
                        //set boolean with error since the password does not match
                        repeatPasswordBoolean = false;
                    } else { //set boolean as correct, since the regex matches
                        repeatPasswordBoolean = true;
                        confirmField.setId("normalTextField");
                    }
                } else {
                    //Do nothing specific when the focus actually goes to the TextField
                }
            }
        });


    }

    @FXML
    void handleCancel(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_LoginRegister.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println("IOException found in handleCancel");
        }
    }

    @FXML
    void handleHelp(ActionEvent event) {
        Handler_Alert.information(
                "Help",
                "I will show you what to do here ↓",
                "This is where you can create your own account." +
                        "\nEnter your desired username, your email address " +
                        "\nand your password." +
                        "\nREMEMBER: You need to confirm you password.",
                false
        );
    }

    @FXML
    void registerAccount(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setHeaderText("ERROR!");
        if (!usernameBoolean && !emailBoolean) {
            alert.setContentText("The username and email is in wrong format!");
            alert.show();
        } else if (!usernameBoolean) {
            alert.setContentText("The username should consist of only alphabets and/or numbers!");
            alert.show();
        } else if (!emailBoolean) {
            alert.setContentText("The email is in wrong format!");
            alert.show();
        } else if (!passwordBoolean) {
            alert.setContentText("The password must contain at least one character, and no spaces.");
            alert.show();
        } else if (!repeatPasswordBoolean) {
            alert.setContentText("The repeated password does not match your first password.");
            alert.show();
        } else {
            //TODO actual function of the button, so far only regex checks
            User user = Database.getInstance().getUser(userNameTextField.getText(), emailField.getText());
            System.out.println(user);
            if(user == null) {
                System.out.println("User available");
                Database.getInstance().saveAccount(userNameTextField.getText(),emailField.getText(),passwordField.getText());
                Handler_Alert.information(
                        "Succes",
                        "Gz",
                        "You have created an account",
                        false);
                paneChangeToLogin();
            }
            else
                System.out.println("user is taken");
        }
    }

    public void paneChangeToLogin() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/GUI_LoginRegister.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (IOException ex) {
            System.out.println("IOException found in paneChangeToLibrary");
        }
    }
}
