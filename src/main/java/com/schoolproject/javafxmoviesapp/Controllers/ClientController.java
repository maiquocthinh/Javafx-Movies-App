package com.schoolproject.javafxmoviesapp.Controllers;

import com.schoolproject.javafxmoviesapp.Entity.Movie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private HBox card_layout;
    private List<Movie> recentlyAdded;
    @Override

    public void initialize(URL location, ResourceBundle resourceBundle) {
        List<Movie> recentlyAdded = new ArrayList<>(recentlyAdded());
        try{
            for(int i = 0; i< recentlyAdded.size(); i++){
                FXMLLoader fxmlLoader =new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Card.fxml"));
                HBox cardBox =fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(recentlyAdded.get(i));
                card_layout.getChildren().add(cardBox);
            }
        }catch (IOException e){
            e.printStackTrace();

        }
    }
    public List<Movie> recentlyAdded(){
        List<Movie> mv = new ArrayList<>();
        Movie movie =new Movie();
        movie.setName("ANIMEHOT");
        movie.setImageSrc("Images/anh-anime.jpg");
        movie.setPublishing("Nhà xuất bản");
        mv.add(movie);

        movie =new Movie();
        movie.setName("ANIMEHOT");
        movie.setImageSrc("Images/anh-anime.jpg");
        movie.setPublishing("Nhà xuất bản");
        mv.add(movie);

        movie =new Movie();
        movie.setName("ANIMEHOT");
        movie.setImageSrc("Images/anh-icon.jpg");
        movie.setPublishing("Nhà xuất bản");
        mv.add(movie);
        return mv;
    }

}
