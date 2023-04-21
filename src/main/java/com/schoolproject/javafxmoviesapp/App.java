package com.schoolproject.javafxmoviesapp;

import com.schoolproject.javafxmoviesapp.Views.AuthView;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        AuthView.getInstance().switchToLogin(primaryStage);
    }
}
