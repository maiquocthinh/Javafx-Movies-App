package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TopViewController implements Initializable {
    @FXML
    private VBox topDayVbox;

    @FXML
    private VBox topWeekVbox;

    @FXML
    private VBox topMonthVbox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // load top view of day
        List<Film> filmsTopDay = FilmDAOImpl.getInstance().selectTopViewByDay(6);
        try {
            for (Film film : filmsTopDay) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCardHorizontal.fxml"));
                topDayVbox.getChildren().add(fxmlLoader.load());
                FilmCardHorizontalController controller = fxmlLoader.getController();
                controller.setData(film);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // load top view of week
        List<Film> filmsTopYear = FilmDAOImpl.getInstance().selectTopViewByWeek(6);
        try {
            for (Film film : filmsTopYear) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCardHorizontal.fxml"));
                topWeekVbox.getChildren().add(fxmlLoader.load());
                FilmCardHorizontalController controller = fxmlLoader.getController();
                controller.setData(film);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // load top view of month
        List<Film> filmsTopMonth = FilmDAOImpl.getInstance().selectTopViewByMonth(6);
        try {
            for (Film film : filmsTopMonth) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCardHorizontal.fxml"));
                topMonthVbox.getChildren().add(fxmlLoader.load());
                FilmCardHorizontalController controller = fxmlLoader.getController();
                controller.setData(film);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
