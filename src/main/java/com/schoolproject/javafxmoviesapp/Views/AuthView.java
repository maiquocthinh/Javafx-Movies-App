package com.schoolproject.javafxmoviesapp.Views;

import com.schoolproject.javafxmoviesapp.Controllers.Auth.ChangePasswordController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthView {
    private static AuthView instance = null;
    public static AuthView getInstance(){
        if(instance == null) instance = new AuthView();
        return instance;
    }
    public void switchToLogin(Stage stage ) throws  IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Auth/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Login");
        if (!stage.isShowing()) stage.show();
    }

    public void switchToSignup(Stage stage) throws  IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Auth/Register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Register");
        if(!stage.isShowing()) stage.show();
    }

    public void switchToForgotPassword(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Auth/ForgetPassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Forgot Password");
        if(!stage.isShowing()) stage.show();
    }

    public void switchToChangePassword(Stage stage, String email) throws  IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Auth/ChangePassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Change Password");
        ChangePasswordController changePasswordController = fxmlLoader.getController();
        changePasswordController.setEmail(email);
        if(!stage.isShowing()) stage.show();
    }


    public void backToLogin(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Auth/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Login");
        if(!stage.isShowing()) stage.show();
    }

    public void backToForgotPassword(Stage stage)throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Auth/ForgetPassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Forgot Password");
        if(!stage.isShowing()) stage.show();
    }

    public void backToInputOTP(Stage stage)throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Auth/OTP.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Input OTP");
        if(!stage.isShowing()) stage.show();
    }
}
