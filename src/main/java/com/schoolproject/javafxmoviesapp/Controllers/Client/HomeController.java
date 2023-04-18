package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import com.schoolproject.javafxmoviesapp.Entity.Movie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
//import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
//import java.io.IOException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private FlowPane filmsNewFlowPane;
    @FXML
    private FlowPane moviesFlowPane;
    @FXML
    private FlowPane tvSeriesFlowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // load films new (12 film)
        List<Film> filmsNew = FilmDAOImpl.getInstance().selectByCondition("ORDER BY `id` DESC LIMIT 12");
        try {
            for (Film film : filmsNew) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCardVertical.fxml"));
                filmsNewFlowPane.getChildren().add(fxmlLoader.load());
                FilmCardVerticalController controller = fxmlLoader.getController();
                controller.setData(film);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // load movies new (12 film)
        List<Film> moviesNew = FilmDAOImpl.getInstance().selectByCondition("WHERE type='Movie' ORDER BY `id` DESC LIMIT 12");
        try {
            for (Film film : moviesNew) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCardVertical.fxml"));
                moviesFlowPane.getChildren().add(fxmlLoader.load());
                FilmCardVerticalController controller = fxmlLoader.getController();
                controller.setData(film);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // load tv series new (12 film)
        List<Film> tvSeriesNew = FilmDAOImpl.getInstance().selectByCondition("WHERE type='TV Series' ORDER BY `id` DESC LIMIT 12");
        try {
            for (Film film : tvSeriesNew) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCardVertical.fxml"));
                tvSeriesFlowPane.getChildren().add(fxmlLoader.load());
                FilmCardVerticalController controller = fxmlLoader.getController();
                controller.setData(film);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
