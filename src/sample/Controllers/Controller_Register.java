package sample.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import sample.Models.Database;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_Register implements Initializable {

    String regexUserName = "[A-Za-z0-9]+";
    String regexEmail = "[^ ;]{1,}[@]{1}[^@ ;]{1,}[.]{1}[A-Za-z]{1,}";
    String regexPassword = "[^ ;]{1,8}";

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
    @FXML
    private Label userNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label confirmPasswordLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //This will request focus on the pane so the labels will update since they are updating the moment they lose focus
        rootPane.setOnMouseClicked(event -> rootPane.requestFocus());
        //TODO Change all lines where it says (nameOfLabel.setText("X"); into the copy pasted emoji
        //TODO Do not forget to replace the ".setTextFill(Color.RED);" with nothing, as it will serve no purpose once the emoji is there
        //userName
        userNameTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    //set label with error since regex will not match
                    if (!userNameTextField.equals(regexUserName)) {
                        userNameLabel.setStyle("-fx-text-fill: red");
                        userNameTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                    } else { //set label as correct, since the regex matches
                        userNameLabel.setText("");
                        userNameTextField.setStyle("-fx-border-color: red; -fx-border-width: 0px");
                    }
                } else {

                }
            }
        });
        //email
        emailField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    //set label with error since regex will not match
                    if (!emailField.getText().matches(regexEmail)) {
                        //emailLabel.setText("❌");
                        //emailLabel.setTextFill(Color.RED);
                        //emailLabel.setStyle("-fx-text-fill: red");
                        //emailField.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                        emailField.setStyle(".wrongTextField");
                    } else { //set label as correct, since the regex matches
                        emailLabel.setText("");
                        emailField.setStyle("-fx-border-color: red; -fx-border-width: 0px");
                    }
                } else {
                }
            }
        });
        //password
        passwordField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    //set label with error since regex will not match
                    if (!passwordField.getText().matches(regexPassword)) {
                        //passwordLabel.setText("❌");
                        passwordLabel.setStyle("-fx-text-fill: red");
                        passwordField.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                    } else { //set label as correct, since the regex matches
                        passwordLabel.setText("");
                        passwordField.setStyle("-fx-border-color: red; -fx-border-width: 0px");
                    }
                } else {
                }
            }
        });
        //passwordConfirm
        confirmField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    //set label with error since the password does not match
                    if (!confirmField.getText().equals(passwordField)) {
                        //confirmPasswordLabel.setText("❌");
                        confirmPasswordLabel.setStyle("-fx-text-fill: red");
                        confirmField.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                    } else { //set label as correct, since the regex matches
                        confirmPasswordLabel.setText("");
                        confirmField.setStyle("-fx-border-color: red; -fx-border-width: 0px");
                    }
                } else {
                }
            }
        });


    }

    @FXML
    void handleCancel(ActionEvent event) {

    }

    @FXML
    void handleHelp(ActionEvent event) {

    }

    @FXML
    void registerAccount(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR!");
        if (!userNameLabel.equals("") && !emailLabel.equals("")) {
            alert.setContentText("The username and email is in wrong format!");
            alert.show();
        } else if (!userNameLabel.equals("")) {
            alert.setContentText("The username should consist of only alphabets and/or numbers!");
            alert.show();
        } else if (!emailLabel.equals("")) {
            alert.setContentText("The email is in wrong format!");
            alert.show();
        } else if (!passwordLabel.equals("")) {
            alert.setContentText("The password must contain at least one character, and no spaces.");
            alert.show();
        } else if (!confirmPasswordLabel.equals("")) {
            alert.setContentText("The repeated password does not match your first password.");
            alert.show();
        } else {

        }
    }
}
