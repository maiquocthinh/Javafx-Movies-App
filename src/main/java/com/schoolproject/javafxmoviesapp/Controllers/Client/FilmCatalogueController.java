package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.FlowPane;

public class FilmCatalogueController {

    @FXML
    private FlowPane FilmsFlowPane;

    @FXML
    private Pagination pagination;

    @FXML
    private Label titleLabel;
    
    public void filterFilmByGenre(Genre genre) {
    }

    public void filterFilmByCountry(Country country) {
    }

    public void filterFilmByYear(int year) {
    }

    public void filterFilmByType(String type) {
    }

    public void filterFilmByKeywords(String type) {
    }
}
