package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.CountryDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.GenreDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import com.schoolproject.javafxmoviesapp.Utils.ValidateUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.schoolproject.javafxmoviesapp.Utils.SQLQueryUtil.setGenreAndCountryForFilm;

public class AddFilmController implements Initializable {
    @FXML
    private ScrollPane navbar;
    @FXML
    private NavbarController navbarController;
    @FXML
    private TextField backdropTextField;

    @FXML
    private TextArea contentTextArea;

    @FXML
    private TilePane countriesTilePane;

    @FXML
    private TilePane genresTilePane;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField posterTextField;

    @FXML
    private TextField qualityTextField;

    @FXML
    private TextField releaseTextField;

    @FXML
    private TextField runtimeTextField;

    @FXML
    private ChoiceBox<String> statusChoiceBox;

    @FXML
    private TextField trailerTextField;

    @FXML
    private ChoiceBox<String> typeChoiceBox;

    @FXML
    private CheckBox isPopularCheckBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // inti navbar menu
        navbarController.getFilmsTitledPane().setExpanded(true);
        navbarController.getNavAddFilm().getStyleClass().add("activeNav");

        // get from DB then set data for genresTilePane & countriesTilePane
        List<Genre> allGenres = GenreDAOImpl.getInstance().selectAll();
        List<Country> allCountries = CountryDAOImpl.getInstance().selectAll();
        genresTilePane.setPrefColumns(Math.ceilDiv(allGenres.size(), 5));
        countriesTilePane.setPrefColumns(Math.ceilDiv(allCountries.size(), 5));
        for (Genre genre : allGenres) {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(genre.getName());
            checkBox.setUserData(genre);
            genresTilePane.getChildren().add(checkBox);
        }
        for (Country country : allCountries) {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(country.getName());
            checkBox.setUserData(country);
            countriesTilePane.getChildren().add(checkBox);
        }
    }

    @FXML
    void handleCreateFilm(MouseEvent event) throws SQLException, IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        // get values
        String name = nameTextField.getText();
        String poster = posterTextField.getText();
        String backdrop = backdropTextField.getText();
        int release;
        String runtime = runtimeTextField.getText();
        String quality = qualityTextField.getText();
        String trailer = trailerTextField.getText();
        String content = contentTextArea.getText();
        String type = typeChoiceBox.getValue();
        String status = statusChoiceBox.getValue();
        boolean isPopular = isPopularCheckBox.isSelected();
        List<Genre> genres = getGenreSelected();
        List<Country> countries = getCountrySelected();
        // validate
        {
            if (name.isEmpty()) {
                alert.setContentText("Name must not be empty!");
                alert.showAndWait();
                return;
            }
            if (poster.isEmpty()) {
                alert.setContentText("Poster must not be empty!");
                alert.showAndWait();
                return;
            }
            if (!ValidateUtil.isURL(poster)) {
                alert.setContentText("Poster must be url!");
                alert.showAndWait();
                return;
            }
            if (backdrop.isEmpty()) {
                alert.setContentText("Backdrop must not be empty!");
                alert.showAndWait();
                return;
            }
            if (!ValidateUtil.isURL(backdrop)) {
                alert.setContentText("Backdrop must be url!");
                alert.showAndWait();
                return;
            }
            try {
                release = Integer.parseInt(releaseTextField.getText());
                if (release < 1000 || release > Year.now().getValue()) {
                    alert.setContentText("Release is not proper!");
                    alert.showAndWait();
                    return;
                }
            } catch (NumberFormatException e) {
                alert.setContentText("Release must be numeric values only!");
                alert.showAndWait();
                return;
            }
            if (!trailer.isEmpty() && !ValidateUtil.isURL(trailer)) {
                alert.setContentText("Trailer must be url!");
                alert.showAndWait();
                return;
            }
            if (content.isEmpty()) {
                alert.setContentText("Content must not be empty!");
                alert.showAndWait();
                return;
            }
            if (type.isEmpty()) {
                alert.setContentText("Type must not be empty!");
                alert.showAndWait();
                return;
            }
            if (status.isEmpty()) {
                alert.setContentText("Status must not be empty!");
                alert.showAndWait();
                return;
            }
            if (genres.size() <= 0) {
                alert.setContentText("Must choose at least 1 Genre");
                alert.showAndWait();
                return;
            }
            if (countries.size() <= 0) {
                alert.setContentText("Must choose at least 1 Country");
                alert.showAndWait();
                return;
            }
        }

        // create film here
        Film film = new Film(name, poster, backdrop, trailer, content, release, type, status, runtime, quality, 0.0f, 0, isPopular);
        int filmId = FilmDAOImpl.getInstance().insert(film);

        setGenreAndCountryForFilm(filmId, genres, countries);

        // clear all fields
        clearAllFields();
    }

    private void clearAllFields() {
        nameTextField.setText("");
        posterTextField.setText("");
        backdropTextField.setText("");
        releaseTextField.setText("");
        runtimeTextField.setText("");
        qualityTextField.setText("");
        trailerTextField.setText("");
        contentTextArea.setText("");
        contentTextArea.setText("");
        isPopularCheckBox.setSelected(false);
        {
            ObservableList<Node> nodes = genresTilePane.getChildren();
            for (Node node : nodes) {
                if (node instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) node;
                    checkBox.setSelected(false);
                }
            }
        }
        {
            ObservableList<Node> nodes = countriesTilePane.getChildren();
            for (Node node : nodes) {
                if (node instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) node;
                    checkBox.setSelected(false);
                }
            }
        }
    }

    private List<Genre> getGenreSelected() {
        List<Genre> genreSelected = new ArrayList<>();
        ObservableList<Node> nodes = genresTilePane.getChildren();
        for (Node node : nodes) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) genreSelected.add((Genre) checkBox.getUserData());
            }
        }
        return genreSelected;
    }

    private List<Country> getCountrySelected() {
        List<Country> genreSelected = new ArrayList<>();
        ObservableList<Node> nodes = countriesTilePane.getChildren();
        for (Node node : nodes) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) genreSelected.add((Country) checkBox.getUserData());
            }
        }
        return genreSelected;
    }

}


