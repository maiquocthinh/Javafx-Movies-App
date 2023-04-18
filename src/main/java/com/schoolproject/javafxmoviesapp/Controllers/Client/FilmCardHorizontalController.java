package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.CommentDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
                int totalComment = CommentDAOImpl.getInstance().countByCondition("WHERE `filmId`=" + film.getId());

                nameLabel.setText(film.getName());
                viewedLabel.setText(String.valueOf(film.getViewed()));
                ratingLabel.setText(String.valueOf(film.getRating()));
                commentLabel.setText(String.valueOf(totalComment));
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

    public void setData(Film film) {
        filmObjectProperty.set(film);
    }
}
