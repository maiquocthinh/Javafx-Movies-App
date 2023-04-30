package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.CommentDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import com.schoolproject.javafxmoviesapp.Utils.SQLQueryUtil;
import com.schoolproject.javafxmoviesapp.Views.ClientView;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FilmCardVerticalController implements Initializable {

    @FXML
    private Label commentLabel;

    @FXML
    private Label deleteLabel;

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
        Platform.runLater(() -> {
            Scene scene = (Scene) nameLabel.getScene();
            if (scene.getRoot().getId() != null)
                if(scene.getRoot().getId().equals("profileBorderPane")){
                deleteLabel.setVisible(true);
                deleteLabel.setDisable(false);
            }
        });

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

    @FXML
    void handleRemoveFromFollowed(MouseEvent event) throws SQLException, IOException {
        // delete form db
        SQLQueryUtil.removeFromFollowed(filmObjectProperty.get().getId());
        // delete form ui
        Parent _this = ((Node) event.getSource()).getParent();
        Scene scene = ((Node) event.getSource()).getScene();
        TilePane filmsFollowedTilePane = (TilePane) scene.lookup("#filmsFollowedTilePane");
        filmsFollowedTilePane.getChildren().remove(_this);
        // prevent the click event from being propagated to the parent
        event.consume();
    }

    @FXML
    void handleGotoFilmDetail(MouseEvent event) throws IOException {
        // goto Film Detail
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        ClientView.getInstance().switchToFilmDetailInfo(stage, filmObjectProperty.get().getId());
    }

    public void setData(Film film) {
        filmObjectProperty.set(film);
    }

}
