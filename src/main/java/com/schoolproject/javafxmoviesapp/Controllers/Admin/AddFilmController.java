package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.Utils.ValidateUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Entity.Genre;

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
    private CheckBox isHotCheckBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // inti navbar menu
        navbarController.getFilmsTitledPane().setExpanded(true);
        navbarController.getNavAddFilm().getStyleClass().add("activeNav");

        // init data
        initData();
        // set data for genresTilePane & countriesTilePane
        genresTilePane.setPrefColumns(Math.ceilDiv(listGenre.size(), 5));
        countriesTilePane.setPrefColumns(Math.ceilDiv(listCountry.size(), 5));
        for (Genre genre : listGenre) {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(genre.getName());
            checkBox.setUserData(genre);
            genresTilePane.getChildren().add(checkBox);
        }
        for (Country country : listCountry) {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(country.getName());
            checkBox.setUserData(country);
            countriesTilePane.getChildren().add(checkBox);
        }
    }

    @FXML
    void handleCreateFilm(MouseEvent event) {
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
        boolean isHot = isHotCheckBox.isSelected();
        List<Genre> genres = getGenreSelected();
        List<Country> countries = getCountrySelected();

        // validate
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

        // create film here

        // clear all fields
        clearAllFields();

    }

    private void clearAllFields(){
        nameTextField.setText("");
        posterTextField.setText("");
        backdropTextField.setText("");
        releaseTextField.setText("");
        runtimeTextField.setText("");
        qualityTextField.setText("");
        trailerTextField.setText("");
        contentTextArea.setText("");
        contentTextArea.setText("");
        isHotCheckBox.setSelected(false);
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


    private List<Genre> listGenre = new ArrayList<>();
    private List<Country> listCountry = new ArrayList<>();

    private void initData() {
        // inti data for listGenre & listCountry
        listGenre.add(new Genre(1, "Action"));
        listGenre.add(new Genre(1, "Adventure"));
        listGenre.add(new Genre(1, "Comedy"));
        listGenre.add(new Genre(1, "Documentary"));
        listGenre.add(new Genre(1, "Drama"));
        listGenre.add(new Genre(1, "Family"));
        listGenre.add(new Genre(1, "Fantasy"));
        listGenre.add(new Genre(1, "History"));
        listGenre.add(new Genre(1, "Horror"));
        listGenre.add(new Genre(1, "Mystery"));
        listGenre.add(new Genre(1, "Romance"));
        listGenre.add(new Genre(1, "Sci"));
        listGenre.add(new Genre(1, "Sport"));
        listGenre.add(new Genre(1, "Thriller"));
        listGenre.add(new Genre(1, "War"));
        listGenre.add(new Genre(1, "Western"));

        listCountry.add(new Country(1, "America"));
        listCountry.add(new Country(1, "China"));
        listCountry.add(new Country(1, "Korea"));
        listCountry.add(new Country(1, "Japan"));
        listCountry.add(new Country(1, "ThaiLand"));
        listCountry.add(new Country(1, "Taiwan"));
        listCountry.add(new Country(1, "Brazil"));
        listCountry.add(new Country(1, "Italy"));
        listCountry.add(new Country(1, "Germany"));
        listCountry.add(new Country(1, "England"));
        listCountry.add(new Country(1, "Mexico"));
        listCountry.add(new Country(1, "Spain"));
        listCountry.add(new Country(1, "France"));
        listCountry.add(new Country(1, "India"));
        listCountry.add(new Country(1, "Russia"));
        listCountry.add(new Country(1, "VietNam"));
    }

}


