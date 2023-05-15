package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.CommentDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import com.schoolproject.javafxmoviesapp.Views.ClientView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FilmCardHorizontalController implements Initializable {

    @FXML
    private Label commentLabel;

    @FXML
    private ImageView filmPosterImageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label ratingLabel;

    @FXML
    private Label viewedLabel;


    private ObjectProperty<Film> filmObjectProperty = new SimpleObjectProperty<Film>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filmObjectProperty.addListener(new ChangeListener<Film>() {
            @Override
            public void changed(ObservableValue<? extends Film> observable, Film oldValue, Film newValue) {
                // set data film
                Film film = filmObjectProperty.get();

                nameLabel.setText(film.getName());
                viewedLabel.setText(String.valueOf(film.getViewed()));
                ratingLabel.setText(String.valueOf(film.getRating()));
                commentLabel.setText(String.valueOf(film.getTotalComment()));
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
            }
        });
    }

    @FXML
    void handleGotoFilmDetail(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        ClientView.getInstance().switchToFilmDetailInfo(stage, filmObjectProperty.get());
    }

    public void setData(Film film) {
        filmObjectProperty.set(film);
    }
}
