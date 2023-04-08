package com.schoolproject.javafxmoviesapp.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class AuthView {
    private static AuthView instance = null;
    public static AuthView getInstance(){
        if(instance == null) instance = new AuthView();
        return instance;
    }
    public void switchToForgotPassword(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Auth/ForgotPassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Forgot Password");
        if(!stage.isShowing()) stage.show();
    }
    public  void handleSubmit(Stage stage) throws  IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Auth/InputOTP.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Input OTP");
        if (!stage.isShowing()) stage.show();
    }
    public void handleSubmitOTP(Stage stage) throws  IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Auth/ResetPassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Reset Password");
        if(!stage.isShowing()) stage.show();
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
}
