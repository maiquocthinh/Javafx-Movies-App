package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.CommentDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.EpidodeDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.UserDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private ScrollPane navbar;
    @FXML
    private NavbarController navbarController;
    @FXML
    private Label amountFilmLabel;
    @FXML
    private Label amountEpisodeLabel;
    @FXML
    private Label amountUserLabel;
    @FXML
    private Label amountCommentLabel;
    @FXML
    private TableView<Film> newFilmTableView;
    @FXML
    private TableColumn<Film, Integer> idColumn;
    @FXML
    private TableColumn<Film, ImageView> posterColumn;
    @FXML
    private TableColumn<Film, String> nameColumn;
    @FXML
    private TableColumn<Film, String> genresColumn;
    @FXML
    private TableColumn<Film, Integer> viewedColumn;
    private Map<String, ImageView> imageViewCache = new HashMap<String, ImageView>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navbarController.getNavDashboard().getStyleClass().add("activeNav");

        // set amount film, episode, user, comment
        amountFilmLabel.setText(String.valueOf(FilmDAOImpl.getInstance().count()));
        amountEpisodeLabel.setText(String.valueOf(EpidodeDAOImpl.getInstance().count()));
        amountUserLabel.setText(String.valueOf(UserDAOImpl.getInstance().count()));
        amountCommentLabel.setText(String.valueOf(CommentDAOImpl.getInstance().count()));

        // set alignment for each column
        idColumn.setStyle("-fx-alignment: CENTER;");
        posterColumn.setStyle("-fx-alignment: CENTER;");
        nameColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        genresColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        viewedColumn.setStyle("-fx-alignment: CENTER;");

        idColumn.setCellValueFactory(new PropertyValueFactory<Film, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Film, String>("name"));
        viewedColumn.setCellValueFactory(new PropertyValueFactory<Film, Integer>("viewed"));
        posterColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Film, ImageView>, ObservableValue<ImageView>>() {
            @Override
            public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Film, ImageView> param) {
                String poster = param.getValue().getPoster();
                // check in cache
                if (imageViewCache.containsKey(poster)) {
                    // if exits in cache -> return this
                    return new SimpleObjectProperty<>(imageViewCache.get(poster));
                } else {
                    // if not exits in cache then create new -> store into cache -> return
                    ImageView imageView = new ImageView(new Image(poster));
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(67);
                    imageViewCache.put(poster, imageView);
                    return new SimpleObjectProperty<>(imageView);
                }
            }
        });
//        genresColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Film, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Film, String> param) {
//                List<Genre> genres = GenreDAOImpl.getInstance().selectByCondition("WHERE `filmId`=" + param.getValue().getId());
//                return new SimpleStringProperty("");
//            }
//        });

        // load 10 new film to tableview
        List<Film> filmsNew = FilmDAOImpl.getInstance().selectByCondition("ORDER BY `id` DESC LIMIT 3");
        for (Film film : filmsNew) newFilmTableView.getItems().add(film);

    }
}
