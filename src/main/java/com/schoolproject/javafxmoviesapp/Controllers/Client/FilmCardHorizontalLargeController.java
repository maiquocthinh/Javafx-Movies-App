package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.Entity.Film;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FilmCardHorizontalLargeController {

    @FXML
    private ImageView backdropImageView;

    @FXML
    private Label commentLabel;

    @FXML
    private Label contentLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label ratingLabel;

    @FXML
    private Label viewedLabel;


    @FXML
    void handleGotoFilm(ActionEvent event) {

    }

    public void setData(Film film) {
        nameLabel.setText(film.getName());
        ratingLabel.setText(String.valueOf(film.getRating()));
        viewedLabel.setText(String.valueOf(film.getViewed()));
        commentLabel.setText("0");
        contentLabel.setText(film.getContent());
        Task<Image> imageTask = new Task<Image>() {
            @Override
            protected Image call() throws Exception {
                return new Image(film.getBackdrop());
            }
        };
        imageTask.setOnSucceeded(event -> backdropImageView.setImage(imageTask.getValue()));

        new Thread(imageTask).start();
    }

}
