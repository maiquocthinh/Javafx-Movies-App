package com.schoolproject.javafxmoviesapp.Controllers.Auth;

import com.schoolproject.javafxmoviesapp.Views.AuthView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgotPassword {

    @FXML
    private TextField emailField;

    @FXML
    void handleSubmit(MouseEvent event) throws IOException {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        String email = emailField.getText().trim();
//        check email
        if(email.isEmpty()){
            alertError.setContentText("Email must not be empty!");
            alertError.showAndWait();
            return;
        }else if(!email.isEmpty()){
//            checked email is true => switch to input OTP
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            AuthView.getInstance().handleSubmit(stage);
        } //checked email is false => alert email is not exist


    }


}