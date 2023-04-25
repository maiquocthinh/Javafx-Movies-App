package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.EpidodeDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Episode;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import com.schoolproject.javafxmoviesapp.Views.ClientView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class FilmWatchController implements Initializable {
    @FXML
    private VBox mainVBox;

    @FXML
    private FlowPane listEpisodeFlowPane;

    @FXML
    private Label filmNameLabel;

    @FXML
    private Label filmEpNameLabel;

    @FXML
    private Button prevEpButton;

    @FXML
    private Button nextEpButton;

    @FXML
    private TextField searchEpTextField;

    @FXML
    private AnchorPane videoPlayer;

    @FXML
    VideoPlayerController videoPlayerController;

    private IntegerProperty filmId = new SimpleIntegerProperty();
    private Film film = null;
    List<Episode> episodes = new ArrayList<>();

    public void initialize(URL location, ResourceBundle resources) {

        filmId.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (oldValue != newValue) {
                    film = FilmDAOImpl.getInstance().findById(filmId.get());
                    episodes = EpidodeDAOImpl.getInstance().selectByCondition("WHERE `filmId`=" + filmId.get());

                    // load film name
                    filmNameLabel.setText(film.getName());

                    // load all episodes of film
                    for (Episode episode : episodes) {
                        // create episode btn & add to listEpisodeFlowPane
                        Button episodeButton = new Button("Ep " + episode.getName());
                        episodeButton.getStyleClass().add("episode-btn");
                        episodeButton.setUserData(episode);
                        // set on action -> switch ep
                        episodeButton.setOnAction(event -> {
                            switchEpisode(episode);
                        });
                        listEpisodeFlowPane.getChildren().add(episodeButton);
                    }

                    // init default episode video
                    initDefaultEpisodeVideo();

                    // load comments
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/Comments.fxml"));
                        mainVBox.getChildren().add(mainVBox.getChildren().size(), fxmlLoader.load());
                        CommentsController controller = fxmlLoader.getController();
                        controller.setFilmId(filmId.get());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    @FXML
    void handleInfoClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ClientView.getInstance().switchToFilmDetailInfo(stage, filmId.get());
    }

    @FXML
    void handleSearchEp(KeyEvent event) {
        if (searchEpTextField.getText().isEmpty()) {
            for (Node child : listEpisodeFlowPane.getChildren()) {
                if (child instanceof Button) {
                    Button episodeButton = (Button) child;
                    episodeButton.setVisible(true);
                    episodeButton.setManaged(true);
                }
            }

        } else {
            for (Node child : listEpisodeFlowPane.getChildren()) {
                if (child instanceof Button) {
                    Button episodeButton = (Button) child;
                    Episode episode = (Episode) episodeButton.getUserData();
                    if (("Ep " + episode.getName()).contains(searchEpTextField.getText())) {
                        episodeButton.setVisible(true);
                        episodeButton.setManaged(true);
                    } else {
                        episodeButton.setVisible(false);
                        episodeButton.setManaged(false);
                    }
                }
            }
        }
    }

    private void initDefaultEpisodeVideo() {
        Episode defaultEpisode = episodes.get(0);
        videoPlayerController.setData(defaultEpisode.getLink());
        filmEpNameLabel.setText("Episode " + defaultEpisode.getName());
        // active episode btn
        for (Node child : listEpisodeFlowPane.getChildren()) {
            if (child instanceof Button) {
                Button episodeButton = (Button) child;
                Episode episode = (Episode) episodeButton.getUserData();
                if (defaultEpisode.equals(episode)) {
                    episodeButton.getStyleClass().add("active");
                    break;
                }
            }
        }
        videoPlayer.requestFocus();

        // disable prevEpButton
        prevEpButton.setDisable(true);

        // setup PreEpBtn & NextEpBtn
        setupPreEpBtnAndNextEpBtn(defaultEpisode);
    }

    private void switchEpisode(Episode episode) {
        videoPlayerController.setData(episode.getLink());
        filmEpNameLabel.setText("Episode " + episode.getName());
        // active episode btn & remove action of old episode btn
        for (Node child : listEpisodeFlowPane.getChildren()) {
            if (child instanceof Button) {
                Button episodeButton = (Button) child;
                Episode _episode = (Episode) episodeButton.getUserData();
                if (episode.equals(_episode)) {
                    episodeButton.getStyleClass().add("active");
                } else {
                    episodeButton.getStyleClass().remove("active");
                }
            }
        }
        videoPlayer.requestFocus();

        // setup PreEpBtn & NextEpBtn
        setupPreEpBtnAndNextEpBtn(episode);
    }

    private void setupPreEpBtnAndNextEpBtn(Episode episode) {
        // check position of episode in episodes to...
        int index = episodes.indexOf(episode);
        if (index != -1) {
            prevEpButton.setDisable(false);
            nextEpButton.setDisable(false);

            // ...to disable/enable prevEpButton & nextEpButton
            if (index == 0) prevEpButton.setDisable(true);
            if (index == episodes.size() - 1) nextEpButton.setDisable(true);

            // ...to setOnAction switchEpisode for prevEpButton & nextEpButton
            if (index > 0) {
                prevEpButton.setOnAction(event -> {
                    switchEpisode(episodes.get(index - 1));
                });
            }
            if (index < episodes.size() - 2) {
                nextEpButton.setOnAction(event -> {
                    switchEpisode(episodes.get(index + 1));
                });
            }
        }
    }

    public void setFilmId(int filmId) {
        this.filmId.set(filmId);
    }
}
