package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ListFilmController implements Initializable {
    @FXML
    private ScrollPane navbar;
    @FXML
    private NavbarController navbarController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navbarController.getFilmsTitledPane().setExpanded(true);
        navbarController.getNavListFilms().getStyleClass().add("activeNav");
    }
}
