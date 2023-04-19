package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.List;

public class FilmCatalogueController {

    @FXML
    private FlowPane FilmsFlowPane;

    @FXML
    private Pagination pagination;

    @FXML
    private Label titleLabel;
    
    public void filterFilmByGenre(Genre genre) {
        titleLabel.setText("GENRE "+ genre.getName().toUpperCase());

        List<Film> listFilms = FilmDAOImpl.getInstance().selectByCondition(
                "inner join `film_genre` on `film_genre`.`filmId` = `films`.`id` " +
                "where `film_genre`.`genreId` = " + genre.getId() + " " +
                "order by `films`.`id` desc"
        );
        try {
            for (Film film : listFilms) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCardVertical.fxml"));
                FilmsFlowPane.getChildren().add(fxmlLoader.load());
                FilmCardVerticalController controller = fxmlLoader.getController();
                controller.setData(film);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void filterFilmByCountry(Country country) {
        titleLabel.setText("COUNTRY "+ country.getName().toUpperCase());
        List<Film> listFilms = FilmDAOImpl.getInstance().selectByCondition(
                "inner join `film_country` on `film_country`.`filmId` = `films`.`id` " +
                        "where `film_country`.`countryId` = " + country.getId() + " " +
                        "order by `films`.`id` desc"
        );
        try {
            for (Film film : listFilms) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCardVertical.fxml"));
                FilmsFlowPane.getChildren().add(fxmlLoader.load());
                FilmCardVerticalController controller = fxmlLoader.getController();
                controller.setData(film);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void filterFilmByYear(int year) {
        titleLabel.setText("YEAR "+ year);
        List<Film> listFilms = FilmDAOImpl.getInstance().selectByCondition("WHERE `release`='"+year+"'");
        try {
            for (Film film : listFilms) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCardVertical.fxml"));
                FilmsFlowPane.getChildren().add(fxmlLoader.load());
                FilmCardVerticalController controller = fxmlLoader.getController();
                controller.setData(film);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void filterFilmByType(String type) {
        titleLabel.setText("TYPE " + type.toUpperCase());

        List<Film> listFilms = FilmDAOImpl.getInstance().selectByCondition("WHERE `type`='"+type+"'");
        try {
            for (Film film : listFilms) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCardVertical.fxml"));
                FilmsFlowPane.getChildren().add(fxmlLoader.load());
                FilmCardVerticalController controller = fxmlLoader.getController();
                controller.setData(film);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void filterFilmByKeywords(String keywords) {
        List<Film> listFilms = FilmDAOImpl.getInstance().selectByCondition("WHERE LOWER(`name`) LIKE '%" + keywords.toLowerCase() + "%'");
        try {
            for (Film film : listFilms) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCardVertical.fxml"));
                FilmsFlowPane.getChildren().add(fxmlLoader.load());
                FilmCardVerticalController controller = fxmlLoader.getController();
                controller.setData(film);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
