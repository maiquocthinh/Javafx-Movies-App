package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FilmCatalogueController implements Initializable {

    private FlowPane filmsFlowPane = new FlowPane();
    private ScrollPane scrollPane = new ScrollPane(filmsFlowPane);

    @FXML
    private Pagination pagination;

    @FXML
    private Label titleLabel;

    private int RecordPerPage = 20;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filmsFlowPane.setHgap(24);
        filmsFlowPane.setVgap(24);
        filmsFlowPane.setAlignment(Pos.TOP_CENTER);
        filmsFlowPane.setStyle("-fx-background-color: #1d3042");

        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(24));
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setStyle("-fx-background-color: transparent");
    }


    private void loadFilmsToFilmsFlowPane(String conditions) {
        // clear FilmsFlowPane
        filmsFlowPane.getChildren().clear();

        // load Films to FilmsFlowPane
        List<Film> listFilms = FilmDAOImpl.getInstance().selectByCondition(conditions);
        try {
            for (int i = listFilms.size() - 1; i >= 0; i--) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCardVertical.fxml"));
                filmsFlowPane.getChildren().add(fxmlLoader.load());
                FilmCardVerticalController controller = fxmlLoader.getController();
                controller.setData(listFilms.get(i));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generatePaginationSQL(String conditions, int currentPageIndex) {
        return conditions + " LIMIT " + RecordPerPage + " OFFSET " + currentPageIndex * RecordPerPage;
    }


    private void initPagination(String conditions) {
        pagination.setCurrentPageIndex(0);
        pagination.setPageCount(Math.ceilDiv(FilmDAOImpl.getInstance().countByCondition(conditions), RecordPerPage));
        pagination.setMaxPageIndicatorCount(pagination.getPageCount());
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer currentPageIndex) {
                loadFilmsToFilmsFlowPane(generatePaginationSQL(conditions, currentPageIndex));
                return scrollPane;
            }
        });
    }


    public void filterFilmPopular() {
        titleLabel.setText("FILMS POPULAR");

        String conditions = "WHERE `popular` = true ORDER BY `id` DESC";

        // init pagination
        initPagination(conditions);
    }

    public void filterFilmNew() {
        titleLabel.setText("FILMS NEWS");

        String conditions = "ORDER BY `id` DESC";

        // init pagination
        initPagination(conditions);
    }

    public void filterFilmByGenre(Genre genre) {
        titleLabel.setText("GENRE: " + genre.getName().toUpperCase());

        String conditions = "INNER JOIN `film_genre` ON `film_genre`.`filmId` = `films`.`id` " +
                "WHERE `film_genre`.`genreId` = " + genre.getId() + " " +
                "ORDER BY `films`.`id` DESC";

        // init pagination
        initPagination(conditions);
    }

    public void filterFilmByCountry(Country country) {
        titleLabel.setText("COUNTRY: " + country.getName().toUpperCase());

        String conditions = "INNER JOIN `film_country` ON `film_country`.`filmId` = `films`.`id` " +
                "WHERE `film_country`.`countryId` = " + country.getId() + " " +
                "ORDER BY `films`.`id` DESC";

        // init pagination
        initPagination(conditions);
    }

    public void filterFilmByYear(int year) {
        titleLabel.setText("YEAR: " + year);

        String conditions = "WHERE `release`='" + year + "' ORDER BY `id` DESC";

        // init pagination
        initPagination(conditions);
    }

    public void filterFilmByType(String type) {
        titleLabel.setText("TYPE: " + type.toUpperCase());

        String conditions = "WHERE `type`='" + type + "' ORDER BY `id` DESC";

        // init pagination
        initPagination(conditions);
    }

    public void filterFilmByKeywords(String keywords) {
        titleLabel.setText("SEARCH RESULTS: " + keywords.toUpperCase());

        String conditions = "WHERE LOWER(`name`) LIKE '%" + keywords.toLowerCase() + "%' ORDER BY `id` DESC";

        // init pagination
        initPagination(conditions);
    }

}
