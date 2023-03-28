package com.schoolproject.javafxmoviesapp.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private Button btnFogotPassword;

    @FXML
    private Button btnGotoSignup;

    @FXML
    private TextField emailField;
    @FXML
    private Label errorEmail;

    @FXML
    private Label errorPassword;
    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    void forgotPassword(MouseEvent event) {

    }

    @FXML
    void handleLogin(MouseEvent event) throws IOException {
        errorEmail.setVisible(false);
        errorPassword.setVisible(false);
//        do login
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
            if(email.isEmpty() || password.isEmpty()){
                if(email.isEmpty()){
                        errorEmail.setVisible(true);
                }
                if(password.isEmpty()){
                    errorPassword.setVisible(true);
                }
            }else{
                errorEmail.setVisible(false);
                errorPassword.setVisible(false);
                Alert alert= new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Ban da dang nhap thanh cong =))");
                alert.show();
            }
    }


    @FXML
    void switchSignup(MouseEvent event)throws  IOException {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
