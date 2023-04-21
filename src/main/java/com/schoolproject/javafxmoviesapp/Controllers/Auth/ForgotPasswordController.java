package com.schoolproject.javafxmoviesapp.Controllers.Auth;

import com.schoolproject.javafxmoviesapp.Utils.OTPUtil;
import com.schoolproject.javafxmoviesapp.Utils.ValidateUtil;
import com.schoolproject.javafxmoviesapp.Views.AuthView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

public class ForgotPasswordController {

    @FXML
    private TextField emailTextField;

    @FXML
    void handleForgotPassword(MouseEvent event) throws IOException, SQLException, MessagingException, GeneralSecurityException {
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        String email = emailTextField.getText().trim();
        // check email
        if (email.isEmpty()) {
            alertError.setContentText("Email must not be empty!");
            alertError.showAndWait();
            return;
        }
        if (!ValidateUtil.isEmail(email)) {
            alertError.setContentText("Email is invalid!");
            alertError.showAndWait();
            return;
        }


        // handle forgot
        // generate otp, send to email
        OTPUtil.getInstance().generateAndSendEmailOTP(email);

        // open otp dialog
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Auth/OTP.fxml"));
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(stage);
        dialogStage.setScene(new Scene(fxmlLoader.load()));
        dialogStage.setTitle("Verification email");
        OTPController otpController = fxmlLoader.getController();
        otpController.setEmail(email);
        dialogStage.showAndWait();

        // check email verified?
        if (otpController.isVerify()) {
            // switch to change password
            AuthView.getInstance().switchToChangePassword(stage, email);
        }
    }

    @FXML
    void backToLogin(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AuthView.getInstance().backToLogin(stage);
    }
}