package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.Entity.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MoviesCardController {


    @FXML
    private ImageView image;

    @FXML
    private Label name;

    @FXML
    private Label comment;

    @FXML
    private Label rating;

    @FXML
    private Label view;

    public void setData(Movie movie) {
        image.setImage(new Image(movie.getImage()));
        name.setText(movie.getName());
        comment.setText(String.valueOf(movie.getTotalComment()));
        view.setText(String.valueOf(movie.getTotalView()));
        rating.setText(String.format("%.1f", movie.getRating()));
    }

}
