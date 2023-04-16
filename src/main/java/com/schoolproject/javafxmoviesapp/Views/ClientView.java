package com.schoolproject.javafxmoviesapp.Views;

import com.schoolproject.javafxmoviesapp.Controllers.Client.ClientController;
import com.schoolproject.javafxmoviesapp.Controllers.Client.ClientMoviecontroller;
import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientView {
    private static ClientView instance = null;

    public static ClientView getInstance() {
        if (instance == null) instance = new ClientView();
        return instance;
    }


    public void switchToHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client-app.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Home");
        if (!stage.isShowing()) stage.show();
    }

    public void switchToDanhMuc(Stage stage, Genre genre) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client-Movie.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ClientMoviecontroller clientMoviecontroller = fxmlLoader.getController();
        clientMoviecontroller.setData(genre);
        stage.setScene(scene);
        stage.setTitle("Danh muc");
        if (!stage.isShowing()) stage.show();
    }

    public void switchToDanhMuc(Stage stage, Country country) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client-Movie.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ClientMoviecontroller clientMoviecontroller = fxmlLoader.getController();
        clientMoviecontroller.setData(country);
        stage.setScene(scene);
        stage.setTitle("Home");
        if (!stage.isShowing()) stage.show();
    }

    public void switchToDanhMuc(Stage stage, int year) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client-Movie.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ClientMoviecontroller clientMoviecontroller = fxmlLoader.getController();
        clientMoviecontroller.setData(year);
        stage.setScene(scene);
        stage.setTitle("Home");
        if (!stage.isShowing()) stage.show();
    }


    public void switchToDanhMuc(Stage stage, String type) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client-Movie.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ClientMoviecontroller clientMoviecontroller = fxmlLoader.getController();
        clientMoviecontroller.setData(type);
        stage.setScene(scene);
        stage.setTitle("Home");
        if (!stage.isShowing()) stage.show();
    }
}
