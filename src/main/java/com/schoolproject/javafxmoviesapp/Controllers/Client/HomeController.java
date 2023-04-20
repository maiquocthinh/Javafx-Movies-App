package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import com.schoolproject.javafxmoviesapp.Entity.Movie;
import com.schoolproject.javafxmoviesapp.Views.ClientView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
//import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

    @FXML
    VideoPlayerController videoPlayerController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        videoPlayerController.setData("https://kd.hd-bophim.com/20230314/33694_75653a82/index.m3u8");


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

    @FXML
    void handleMoreMovies(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        ClientView.getInstance().switchToCatalogueByFilmType(stage, "Movie");
    }

    @FXML
    void handleMoreTvSeries(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        ClientView.getInstance().switchToCatalogueByFilmType(stage, "TV Series");
    }
}
