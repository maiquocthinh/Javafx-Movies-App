package com.schoolproject.javafxmoviesapp.Controllers;

import com.schoolproject.javafxmoviesapp.Entity.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class CardController {
    @FXML
    private HBox box;

    @FXML
    private ImageView movie_image;

    @FXML
    private Label label_movie;

    @FXML
    private Label label_publishing;
    private String [] colors = {"#B9E5FF","BDB2FE","FB9AA8","FF5056"};
    public void setData(Movie movie){
        Image image = new Image(movie.getClass().getResourceAsStream(movie.getImageSrc()));
        movie_image.setImage(image);
        label_movie.setText(movie.getName());
        label_publishing.setText(movie.getPublishing());
        box.setStyle("-fx-background-color: "+ Color.web(colors[(int)(Math.random()*colors.length)]));
    }

}
