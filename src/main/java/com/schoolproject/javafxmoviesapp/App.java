package com.schoolproject.javafxmoviesapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client-app.fxml"));
        Scene scene = new Scene(fxmlLoader.<Parent>load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello Word");
        primaryStage.getIcons().add(new Image(getClass().getResource("/Images/app-icon.png").openStream()));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
