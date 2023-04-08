package com.schoolproject.javafxmoviesapp.Controllers.Auth;

import com.schoolproject.javafxmoviesapp.Views.AuthView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Register {

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordAgainField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void handleSignup(MouseEvent event) {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String passwordagain = passwordAgainField.getText().trim();

        if(name.isEmpty()){
            alertError.setContentText("Name is must not be empty");
            alertError.showAndWait();
            return;
        }
        if (email.isEmpty()){
            alertError.setContentText("Email is must not empty");
            alertError.showAndWait();
            return;
        }
        //        checked email is false => alert : email is not exists
        if(password.isEmpty()){
            alertError.setContentText("Password is must not is empty");
            alertError.showAndWait();
            return;
        }

        if (passwordagain.isEmpty()){
            alertError.setContentText("Password again is must not is empty");
            alertError.showAndWait();
            return;
        }
        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !passwordagain.isEmpty()){
            alertInfo.setContentText("You registered successes");
            alertInfo.showAndWait();
            return;
        }
    }

    @FXML
    void switchToLogin(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        AuthView.getInstance().switchToLogin(stage);
    }

}