package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.*;
import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Entity.Episode;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import com.schoolproject.javafxmoviesapp.Utils.AppSessionUtil;
import com.schoolproject.javafxmoviesapp.Utils.URLUtil;
import com.schoolproject.javafxmoviesapp.Views.ClientView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FilmDetailInfoController implements Initializable {

    @FXML
    private BorderPane detailFIlmBorderPane;

    @FXML
    private VBox mainVBox;

    @FXML
    private Label filmContentLabel;

    @FXML
    private ScrollPane filmContentScrollPane;

    @FXML
    private Label filmCountryLabel;

    @FXML
    private Label filmGenresLabel;

    @FXML
    private Label filmNameLabel;

    @FXML
    private ImageView filmPosterImageView;

    @FXML
    private Label filmRatingLabel;

    @FXML
    private Label filmReleaseLabel;

    @FXML
    private Label filmRuntimeLabel;

    @FXML
    private Label filmStatusLabel;

    @FXML
    private Label totalCommentLabel;

    @FXML
    private Label totalFollowLabel;

    @FXML
    private Label totalViewedLabel;

    @FXML
    private Label followLabel;

    @FXML
    private Rating rating;


    private IntegerProperty filmId = new SimpleIntegerProperty();
    private Film film = null;

    private FontIcon plusIcon = new FontIcon("fas-plus");
    private FontIcon checkIcon = new FontIcon("fas-check");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set color for icons
        plusIcon.setIconColor(Color.WHITE);
        checkIcon.setIconColor(Color.WHITE);

        //listen filmId change
        filmId.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (oldValue != newValue) {
                    film = FilmDAOImpl.getInstance().findById(filmId.get());
                    int totalFollow = FilmDAOImpl.getInstance().getTotalFollow(filmId.get());
                    int totalComment = CommentDAOImpl.getInstance().countByCondition("WHERE `filmId`=" + filmId.get());

                    // load few info
                    filmNameLabel.setText(film.getName());
                    filmStatusLabel.setText(film.getStatus());
                    filmReleaseLabel.setText(String.valueOf(film.getRelease()));
                    filmRuntimeLabel.setText(film.getRuntime());
                    filmContentLabel.setText(film.getContent());
                    filmRatingLabel.setText(String.valueOf(film.getRating()));
                    totalViewedLabel.setText(String.valueOf(film.getViewed()));
                    totalFollowLabel.setText(String.valueOf(totalFollow));
                    totalCommentLabel.setText(String.valueOf(totalComment));
                    rating.setRating(film.getRating() / 2);

                    // load genres
                    List<Genre> genres = GenreDAOImpl.getInstance().selectByFilmId(film.getId());
                    StringBuffer genresStringBuffer = new StringBuffer();
                    for (Genre genre : genres) {
                        genresStringBuffer.append(genre.getName() + ", ");
                    }
                    filmGenresLabel.setText(genresStringBuffer.toString());

                    // load countries
                    List<Country> countries = CountryDAOImpl.getInstance().selectByFilmId(film.getId());
                    StringBuffer countriesStringBuffer = new StringBuffer();
                    for (Country country : countries) {
                        countriesStringBuffer.append(country.getName() + ", ");
                    }
                    filmCountryLabel.setText(countriesStringBuffer.toString());


                    // load film poster
                    Task<Image> imageTask = new Task<Image>() {
                        @Override
                        protected Image call() throws Exception {
                            return new Image(film.getPoster());
                        }
                    };
                    imageTask.setOnSucceeded(event -> {
                        filmPosterImageView.setImage(imageTask.getValue());
                    });
                    new Thread(imageTask).start();

                    // load comments
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/Comments.fxml"));
                        mainVBox.getChildren().add(mainVBox.getChildren().size(), fxmlLoader.load());
                        CommentsController controller = fxmlLoader.getController();
                        controller.setFilmId(filmId.get());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    // follow label
                    if (AppSessionUtil.getInstance().getUser() != null) {
                        boolean isFollowed = FilmDAOImpl.getInstance().isFollowed(filmId.get(), AppSessionUtil.getInstance().getUser().getId());
                        if (isFollowed) {
                            followLabel.setText("Followed");
                            followLabel.setGraphic(checkIcon);
                        } else {
                            followLabel.setText("Follow");
                            followLabel.setGraphic(plusIcon);
                        }
                    }

                    // listen rating change
                    rating.ratingProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            if (AppSessionUtil.getInstance().getUser() == null) {
                                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                                alertWarning.setContentText("Please login to rating this film!");
                                alertWarning.showAndWait();
                                return;
                            }
                            if (oldValue != newValue) {
                                // calc rating
                                float averageRating = FilmDAOImpl.getInstance().getRating(film);
                                averageRating = averageRating == 0 ? newValue.floatValue() * 2 : (averageRating + newValue.floatValue() * 2) / 2;
                                averageRating = Math.round(averageRating * 10) / (float) 10;
                                film.setRating(averageRating);
                                // update rating
                                FilmDAOImpl.getInstance().updateRating(film);
                                // update rating to ui
                                filmRatingLabel.setText(String.valueOf(averageRating));
                            }
                        }
                    });

                }
            }
        });


    }

    @FXML
    void handleFollowClick(MouseEvent event) {
        if (AppSessionUtil.getInstance().getUser() == null) {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setContentText("Please login to follow this film!");
            alertWarning.showAndWait();
            return;
        }
        boolean isFollowed = FilmDAOImpl.getInstance().isFollowed(filmId.get(), AppSessionUtil.getInstance().getUser().getId());
        if (isFollowed) {
            // unfollow film
            FilmDAOImpl.getInstance().unfollowFilm(filmId.get(), AppSessionUtil.getInstance().getUser().getId());
            // change followLabel
            followLabel.setText("Follow");
            followLabel.setGraphic(plusIcon);
        } else {
            // follow film
            FilmDAOImpl.getInstance().followFilm(filmId.get(), AppSessionUtil.getInstance().getUser().getId());
            // change followLabel
            followLabel.setText("Followed");
            followLabel.setGraphic(checkIcon);
        }
    }

    @FXML
    void handleGotoWatch(ActionEvent event) throws IOException {
        // check film has any episode
        int numOfEpisode = EpidodeDAOImpl.getInstance().countByCondition("WHERE `filmId` = " + filmId.get());
        if (numOfEpisode == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Film has no Episode!");
            alert.showAndWait();
            return;
        }
        // update view
        FilmDAOImpl.getInstance().updateView(film);
        // switch to film watch
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ClientView.getInstance().switchToFilmWatch(stage, filmId.get());
    }

    @FXML
    void handleWatchTrailer(ActionEvent event) {
        if (film.getTrailer().isEmpty()) {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setContentText("Film has not trailer!");
            alertWarning.showAndWait();
            return;
        }

        String YOUTUBE_EMBED_URL_TRAILER = URLUtil.convertToYoutubeEmbedLink(film.getTrailer());
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        WebView webView = new WebView();
        webView.getEngine().load(YOUTUBE_EMBED_URL_TRAILER);
        webView.setPrefHeight(350);
        webView.setPrefWidth(610);

        stage.setScene(new Scene(webView));
        stage.setResizable(false);
        stage.setTitle("Trailer");
        stage.setOnCloseRequest(e -> {
            webView.getEngine().load(null);
        });
        stage.showAndWait();

    }

    public void setFilmId(int filmId) {
        this.filmId.setValue(filmId);
    }

}
