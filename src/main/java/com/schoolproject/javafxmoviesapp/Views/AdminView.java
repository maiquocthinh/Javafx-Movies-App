package com.schoolproject.javafxmoviesapp.Views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminView {

    public static AdminView adminView = null;

    public static AdminView getInstance(){
        if(adminView != null) return adminView;
        adminView = new AdminView();
        return adminView;
    }

    public void showDashboard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Admin ::> Dashboard");
        if(!stage.isShowing()) stage.show();
    }
    public void switchToDashboard(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Admin ::> Dashboard");
        if(!stage.isShowing()) stage.show();
    }
    public void switchToAddFilm(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/AddFilm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Admin ::> Add Film");
        if(!stage.isShowing()) stage.show();
    }

    public void switchToListFilm(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/ListFilm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Admin ::> List Film");
        if(!stage.isShowing()) stage.show();
    }

    public void switchToGenres(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Genres.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Admin ::> Genres");
        if(!stage.isShowing()) stage.show();
    }

    public void switchToCountries(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Countries.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Admin ::> Countries");
        if(!stage.isShowing()) stage.show();
    }

    public void switchToUsers(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Users.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Admin ::> Users");
        if(!stage.isShowing()) stage.show();
    }

    public void switchToRoles(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Roles.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Admin ::> Roles");
        if(!stage.isShowing()) stage.show();
    }

    public void switchToProfile(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Profile.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Admin ::> Profile");
        if(!stage.isShowing()) stage.show();
    }

}

