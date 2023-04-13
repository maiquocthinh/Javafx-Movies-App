package com.schoolproject.javafxmoviesapp.Controllers.Auth;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.UserDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.User;
import com.schoolproject.javafxmoviesapp.Utils.OTPUtil;
import com.schoolproject.javafxmoviesapp.Utils.ValidateUtil;
import com.schoolproject.javafxmoviesapp.Views.AuthView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private PasswordField confirmPasswordTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    void handleRegister(MouseEvent event) throws SQLException, MessagingException, GeneralSecurityException, IOException{
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);


        String name = nameTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String password = passwordTextField.getText().trim();
        String confirmPassword = confirmPasswordTextField.getText().trim();

//        validate
        if(name.isEmpty()){
            alertError.setContentText("Name is must not be empty!");
            alertError.showAndWait();
            return;
        }
        if (email.isEmpty()){
            alertError.setContentText("Email is must not empty!");
            alertError.showAndWait();
            return;
        }
        // checked email is not format => alert : email is not exists
        if(!ValidateUtil.isEmail(email)){
            alertError.setContentText("Email is invalid!");
            alertError.showAndWait();
            return;
        }
        if(password.isEmpty()){
            alertError.setContentText("Password is must not is empty");
            alertError.showAndWait();
            return;
        }
        if (confirmPassword.isEmpty()){
            alertError.setContentText("Password again is must not is empty");
            alertError.showAndWait();
            return;
        }
        if(password.equals(confirmPassword)==false){
            alertError.setContentText("Password and confirm password must be the same");
            alertError.showAndWait();
            return;
        }
        // Check user is registered?
        User user = UserDAOImpl.getInstance().findByEmail(email);
        if (user != null) {
            alertError.setContentText("This email registered already on system");
            alertError.showAndWait();
            return;
        }

        // handle register
        // generate otp code, send to email
        OTPUtil.getInstance().generateAndSendEmailOTP(email);
        // open otp dialog
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Auth/OTP.fxml"));
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(stage);
        dialogStage.setScene(new Scene(fxmlLoader.load()));
        dialogStage.setTitle("Verification email");
        OTPController inputOTP = fxmlLoader.getController();
        inputOTP.setEmail(email);
        dialogStage.showAndWait();

        // check email verified?
        if (inputOTP.isVerify()) {
            // insert user to db
//            User user = new User(name, email, "", password, 0);
            User user1 = new User(name, email, "", password, 0);
            UserDAOImpl.getInstance().insert(user);
            // show alert register success
            alertInfo.setContentText("Register success!");
            alertInfo.showAndWait();
        }
    }

    @FXML
    void switchToLogin(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        AuthView.getInstance().switchToLogin(stage);
    }

}