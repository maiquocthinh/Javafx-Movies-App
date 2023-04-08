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

public class Login {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    void handleLogin(MouseEvent event) throws IOException {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        // get values from ui
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        // validate
        if (email.isEmpty()) {
            alertError.setContentText("Email must not be empty!");
            alertError.showAndWait();
            return;
        }
        // if(email is not email format) cái này bổ sung sau
        if (password.isEmpty()) {
            alertError.setContentText("Password must not be empty!");
            alertError.showAndWait();
            return;
        }

        // check login ...
        // ở đây gọi lên db check xem user có tồn tại ko và pass có đúng ko

        // if login success alert message success
        if(!email.isEmpty() &&!password.isEmpty()){
            alertInfo.setContentText("Login success!!!");
            alertInfo.showAndWait();
        }
        // else alert message fail
//        alertError.setContentText("Login fail, please check email or password");
//        alertError.showAndWait();
    }


    @FXML
    void switchToForgotPassword(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        AuthView.getInstance().switchToForgotPassword(stage);
    }

    @FXML
    void switchToSignup(MouseEvent event) throws  IOException{
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        AuthView.getInstance().switchToSignup(stage);
    }
}
