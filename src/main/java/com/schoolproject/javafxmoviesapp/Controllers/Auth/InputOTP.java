package com.schoolproject.javafxmoviesapp.Controllers.Auth;

import com.schoolproject.javafxmoviesapp.Views.AuthView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class InputOTP {

    @FXML
    private TextField valueFive;

    @FXML
    private TextField valueFour;

    @FXML
    private TextField valueOne;

    @FXML
    private TextField valueSix;

    @FXML
    private TextField valueThree;

    @FXML
    private TextField valueTwo;

    @FXML
    void handleSubmitOTP(MouseEvent event) throws IOException {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        String valueone = valueOne.getText().trim();
        String valuetwo = valueTwo.getText().trim();
        String valuethree = valueThree.getText().trim();
        String valuefour = valueFour.getText().trim();
        String valuefive = valueFive.getText().trim();
        String valuesix = valueSix.getText().trim();

//        check otp code
        if(valueone.isEmpty() || valuetwo.isEmpty() || valuethree.isEmpty()
         || valuefour.isEmpty() || valuefive.isEmpty() || valuesix.isEmpty()){
            alertError.setContentText("OTP code is not be empty!");
            alertError.showAndWait();
            return;
        }
//        checked otp code is false => alert: otp code is not exist
        else {
//            checked otp code is true => switch to resetpassword
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            AuthView.getInstance().handleSubmitOTP(stage);
        }
    }
    @FXML
    void backToForgotPassword(MouseEvent event) throws IOException{
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        AuthView.getInstance().backToForgotPassword(stage);
    }
}
