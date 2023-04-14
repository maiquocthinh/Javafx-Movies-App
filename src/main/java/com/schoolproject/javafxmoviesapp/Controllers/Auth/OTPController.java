package com.schoolproject.javafxmoviesapp.Controllers.Auth;

import com.schoolproject.javafxmoviesapp.Utils.OTPUtil;
import com.schoolproject.javafxmoviesapp.Views.AuthView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class OTPController {

    @FXML
    private TextField OTPCodeTextField;

    private String email;
    private boolean isVerify = false;
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isVerify() {
        return isVerify;
    }

    @FXML
    void handleVerifyOTP(MouseEvent event) throws IOException, SQLException {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
//        get value from ui
        String OTPcode = OTPCodeTextField.getText().trim();
//        validate
        if(OTPcode.isEmpty()){
            alertError.setContentText("OTP code is not be empty!");
            alertError.showAndWait();
            return;
        }
//        checked otp code
        isVerify= OTPUtil.getInstance().checkOTP(email,OTPcode);

        if(isVerify){
            alertInfo.setContentText("Email verify success.");
            alertInfo.showAndWait();
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
            return;
        }else {
            alertError.setContentText("Email verify fail.");
            alertError.showAndWait();
            return;
        }
    }
    @FXML
    void backToForgotPassword(MouseEvent event) throws IOException{
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        AuthView.getInstance().backToForgotPassword(stage);
    }
}
