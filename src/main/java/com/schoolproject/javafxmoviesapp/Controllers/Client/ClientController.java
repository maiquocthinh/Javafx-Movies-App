package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.Entity.Movie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private FlowPane moviesLayout;

    @FXML
    private FlowPane newFilmLayout;

    @FXML
    private FlowPane tvSeriesLayout;

    @FXML
    private VBox topViewDayLayout;

    @FXML
    private VBox topViewMonthLayout;

    @FXML
    private VBox topViewWeekLayout;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Movie> recentlyAdded = new ArrayList<>(recentlyAdded());

        // load movie card to newFilmLayout
        for (Movie movie : recentlyAdded) {
            try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/MoviesCardVertical.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                MoviesCardController moviesCardController = fxmlLoader.getController();
                moviesCardController.setData(movie);
                newFilmLayout.getChildren().add(anchorPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // load movie card to topViewDayLayout
        for (Movie movie : recentlyAdded) {
            try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/MoviesCardHorizontal.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                MoviesCardController moviesCardController = fxmlLoader.getController();
                moviesCardController.setData(movie);
                topViewDayLayout.getChildren().add(anchorPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Movie> recentlyAdded() {
        List<Movie> listMovies = new ArrayList<>();

        listMovies.add(new Movie(
                "The New Scooby-Doo Movies (Season 1)",
                "https://i1.wp.com/img.ophim1.cc/uploads/movies/the-new-scooby-doo-movies-phan-1-thumb.jpg",
                6.5f,
                23,
                32
        ));
        listMovies.add(new Movie(
                "The New Scooby-Doo Movies (Season 1)",
                "https://i1.wp.com/img.ophim1.cc/uploads/movies/the-new-scooby-doo-movies-phan-1-thumb.jpg",
                6.5f,
                23,
                32
        ));
        listMovies.add(new Movie(
                "The New Scooby-Doo Movies (Season 1)",
                "https://i1.wp.com/img.ophim1.cc/uploads/movies/the-new-scooby-doo-movies-phan-1-thumb.jpg",
                6.5f,
                23,
                32
        ));
        listMovies.add(new Movie(
                "The New Scooby-Doo Movies (Season 1)",
                "https://i1.wp.com/img.ophim1.cc/uploads/movies/the-new-scooby-doo-movies-phan-1-thumb.jpg",
                6.5f,
                23,
                32
        ));
        listMovies.add(new Movie(
                "The New Scooby-Doo Movies (Season 1)",
                "https://i1.wp.com/img.ophim1.cc/uploads/movies/the-new-scooby-doo-movies-phan-1-thumb.jpg",
                6.5f,
                23,
                32
        ));

        return listMovies;
    }

}
