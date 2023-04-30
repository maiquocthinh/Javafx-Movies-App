package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class CarouselController implements Initializable {
    @FXML
    private Pagination pagination;

    private HashMap<Integer, AnchorPane> FilmCardHorizontalLargeCache = new HashMap<Integer, AnchorPane>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // get film popular
        List<Film> filmsPopular = FilmDAOImpl.getInstance().selectByCondition("WHERE `popular`=true");

        pagination.setPageCount(filmsPopular.size());
        pagination.setMaxPageIndicatorCount(pagination.getPageCount());

        pagination.setPageFactory(pageIndex -> {
            int filmId = filmsPopular.get(pageIndex).getId();
            if (FilmCardHorizontalLargeCache.containsKey(filmId)) {
                return FilmCardHorizontalLargeCache.get(filmId);
            } else {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCardHorizontalLarge.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    FilmCardHorizontalLargeController controller = fxmlLoader.getController();
                    controller.setData((filmsPopular.get(pageIndex)));
                    FilmCardHorizontalLargeCache.put(filmId, anchorPane);
                    return anchorPane;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            int currentIndex = pagination.getCurrentPageIndex();
            int nextIndex = (currentIndex + 1) % pagination.getPageCount();
            pagination.setCurrentPageIndex(nextIndex);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
