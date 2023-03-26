package com.schoolproject.javafxmoviesapp;

import com.schoolproject.javafxmoviesapp.Views.AdminView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/AddUser.fxml"));
//        Scene scene =  new Scene(fxmlLoader.load());
//        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResource("/Images/app-icon.png").openStream()));
//        primaryStage.show();
        AdminView.getInstance().switchToDashboard(primaryStage);
    }
}
