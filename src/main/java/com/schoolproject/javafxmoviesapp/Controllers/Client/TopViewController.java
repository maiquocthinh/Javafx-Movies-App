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
    private VBox topMonthVbox;

    @FXML
    private VBox topYearVbox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // load top view of day (load mẫu thôi nha, sau này sẽ sửa lại)
        List<Film> filmsTopDay = FilmDAOImpl.getInstance().selectByCondition("ORDER BY `id` DESC LIMIT 6");
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
        // load top view of month (load mẫu thôi nha, sau này sẽ sửa lại)
        List<Film> filmsTopMonth = FilmDAOImpl.getInstance().selectByCondition("ORDER BY `id` ASC LIMIT 6");
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
        // load top view of year (load mẫu thôi nha, sau này sẽ sửa lại)
        List<Film> filmsTopYear = FilmDAOImpl.getInstance().selectByCondition("WHERE `release`%2=0  ORDER BY `id` DESC LIMIT 6");
        try {
            for (Film film : filmsTopYear) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCardHorizontal.fxml"));
                topYearVbox.getChildren().add(fxmlLoader.load());
                FilmCardHorizontalController controller = fxmlLoader.getController();
                controller.setData(film);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
