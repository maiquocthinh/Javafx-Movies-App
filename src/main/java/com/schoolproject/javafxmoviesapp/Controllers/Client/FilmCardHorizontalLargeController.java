package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import com.schoolproject.javafxmoviesapp.Views.ClientView;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

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
    private Film film;


    @FXML
    void handleGotoFilmDetail(ActionEvent event) throws IOException {
        // goto Film Detail
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        ClientView.getInstance().switchToFilmDetailInfo(stage, film.getId());
    }

    public void setData(Film film) {
        this.film = film;
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
