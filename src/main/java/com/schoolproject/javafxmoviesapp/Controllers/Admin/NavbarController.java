package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.Utils.AppSessionUtil;
import com.schoolproject.javafxmoviesapp.Views.AdminView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class NavbarController {

    @FXML
    private TitledPane authTitledPane;

    @FXML
    private TitledPane filmsTitledPane;

    @FXML
    private Button navAddFilm;

    @FXML
    private Button navCountries;

    @FXML
    private Button navDashboard;

    @FXML
    private Button navGenres;

    public TitledPane getAuthTitledPane() {
        return authTitledPane;
    }

    public TitledPane getFilmsTitledPane() {
        return filmsTitledPane;
    }

    public Button getNavAddFilm() {
        return navAddFilm;
    }

    public Button getNavCountries() {
        return navCountries;
    }

    public Button getNavDashboard() {
        return navDashboard;
    }

    public Button getNavGenres() {
        return navGenres;
    }

    public Button getNavListFilms() {
        return navListFilms;
    }

    public Button getNavRoles() {
        return navRoles;
    }

    public Button getNavUsers() {
        return navUsers;
    }

    @FXML
    private Button navListFilms;

    @FXML
    private Button navRoles;

    @FXML
    private Button navUsers;

    @FXML
    void handleLogout(MouseEvent event) {
        AppSessionUtil.getInstance().clear();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void navToAddFilm(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminView.getInstance().switchToAddFilm(stage);
    }

    @FXML
    void navToCountries(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminView.getInstance().switchToCountries(stage);
    }

    @FXML
    void navToDashboard(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminView.getInstance().switchToDashboard(stage);
    }

    @FXML
    void navToGenres(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminView.getInstance().switchToGenres(stage);
    }

    @FXML
    void navToListFilms(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminView.getInstance().switchToListFilm(stage);
    }

    @FXML
    void navToRoles(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminView.getInstance().switchToRoles(stage);
    }

    @FXML
    void navToUsers(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminView.getInstance().switchToUsers(stage);
    }

}
