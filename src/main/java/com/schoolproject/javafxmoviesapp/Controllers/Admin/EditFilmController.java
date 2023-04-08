package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.CountryDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.GenreDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import com.schoolproject.javafxmoviesapp.Utils.CheckPermissionUtil;
import com.schoolproject.javafxmoviesapp.Utils.JDBCUtil;
import com.schoolproject.javafxmoviesapp.Utils.ValidateUtil;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.schoolproject.javafxmoviesapp.Utils.SQLQueryUtil.insertAndSendNotifi;
import static com.schoolproject.javafxmoviesapp.Utils.SQLQueryUtil.setGenreAndCountryForFilm;

public class EditFilmController implements Initializable {
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

    @FXML
    private Tab episodesTab;
    private EpisodesController episodesController;
    @FXML
    private CheckBox sendNotifiCheckBox;

    @FXML
    public TitledPane notifiTitledpane;

    @FXML
    private TextArea contentNotifiTextArea;

    @FXML
    private TextField titleNotifiTextField;

    private Film film = null;

    @FXML
    void handleSendNotifi(MouseEvent event) throws SQLException, IOException, MessagingException, GeneralSecurityException {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        // check permission send notification
        if (!CheckPermissionUtil.getInstance().check("Send Notification")) {
            alertError.setContentText("You don't have permission to send notification!!!");
            alertError.showAndWait();
            return;
        }
        if (notifiTitledpane.isExpanded()) {
            String title = titleNotifiTextField.getText();
            String content = contentNotifiTextArea.getText();
            if (title.isEmpty() || content.isEmpty()) {
                alertError.setContentText("Title or Content of Notification must not be empty!");
                alertError.showAndWait();
                return;
            }
            // insert notifications to db & send email to user
            insertAndSendNotifi(film.getId(), title, content);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        // load Episodes fxml $ set episodesController
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Episodes.fxml"));
        try {
            episodesTab.setContent(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        episodesController = fxmlLoader.getController();

        // send notifi checkbox
        sendNotifiCheckBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (sendNotifiCheckBox.isSelected()) {
                    notifiTitledpane.setExpanded(true);
                    notifiTitledpane.setDisable(false);
                } else {
                    notifiTitledpane.setExpanded(false);
                    notifiTitledpane.setDisable(true);
                }
            }
        });

    }

    @FXML
    void handleSaveFilm(MouseEvent event) throws SQLException, IOException {
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
        film.setName(name);
        film.setPoster(poster);
        film.setBackdrop(backdrop);
        film.setTrailer(trailer);
        film.setContent(content);
        film.setRelease(release);
        film.setType(type);
        film.setStatus(status);
        film.setRuntime(runtime);
        film.setQuality(quality);
        film.setPopular(isPopular);
        FilmDAOImpl.getInstance().update(film);

        setGenreAndCountryForFilm(film.getId(), genres, countries);

        // close this dialog
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
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

    public void setData(Film film) throws SQLException, IOException {

        episodesController.setFilmId(film.getId());

        this.film = film;
        nameTextField.setText(film.getName());
        posterTextField.setText(film.getPoster());
        backdropTextField.setText(film.getBackdrop());
        releaseTextField.setText(String.valueOf(film.getRelease()));
        runtimeTextField.setText(film.getRuntime());
        qualityTextField.setText(film.getQuality());
        trailerTextField.setText(film.getTrailer());
        contentTextArea.setText(film.getContent());
        typeChoiceBox.setValue(film.getType());
        statusChoiceBox.setValue(film.getStatus());
        isPopularCheckBox.setSelected(film.isPopular());
        // set genre & country
        List<Genre> genresSelected = new ArrayList<>();
        List<Country> countriesSelected = new ArrayList<>();
        // Get Connection
        Connection connection = JDBCUtil.getConnecttion();
        ResultSet res;

        {
            // Create Statement
            String sql = "SELECT `id`, `name` FROM `film_genre` INNER JOIN `genres` ON film_genre.genreId = genres.id WHERE `filmId`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, film.getId());

            // Execute SQL
            res = preparedStatement.executeQuery();

            while (res.next()) {
                genresSelected.add(new Genre(res.getInt("id"), res.getString("name")));
            }
        }

        {
            // Create Statement
            String sql = "SELECT `id`, `name` FROM `film_country` INNER JOIN `countries` ON film_country.countryId = countries.id WHERE `filmId`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, film.getId());

            // Execute SQL
            res = preparedStatement.executeQuery();

            while (res.next()) {
                countriesSelected.add(new Country(res.getInt("id"), res.getString("name")));
            }
        }

        // Close Connection
        res.close();
        connection.close();


        {
            ObservableList<Node> nodes = genresTilePane.getChildren();
            for (Node node : nodes) {
                if (node instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) node;
                    Genre genre = (Genre) checkBox.getUserData();
                    if (genresSelected.contains(genre)) checkBox.setSelected(true);
                }
            }
        }

        {
            ObservableList<Node> nodes = countriesTilePane.getChildren();
            for (Node node : nodes) {
                if (node instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) node;
                    Country country = (Country) checkBox.getUserData();
                    if (countriesSelected.contains(country)) checkBox.setSelected(true);
                }
            }
        }

    }
}


