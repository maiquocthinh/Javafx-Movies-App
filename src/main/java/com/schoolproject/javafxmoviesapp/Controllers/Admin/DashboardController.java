package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.CommentDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.EpidodeDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.UserDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import com.schoolproject.javafxmoviesapp.Utils.JDBCUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        amountFilmLabel.setText(String.valueOf(FilmDAOImpl.getInstance().countAll()));
        amountEpisodeLabel.setText(String.valueOf(EpidodeDAOImpl.getInstance().countAll()));
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
                SimpleObjectProperty simpleObjectProperty = new SimpleObjectProperty<>();
                int id = param.getValue().getId();
                String poster = param.getValue().getPoster();
                // check in cache
                if (imageViewCache.containsKey(String.valueOf(id))) {
                    // if exits in cache -> get this & set for simpleObjectProperty
                    simpleObjectProperty.set(imageViewCache.get(String.valueOf(id)));
                } else {
                    // if not exits in cache -> load by thread -> store into cache ->  set for simpleObjectProperty
                    Task<ImageView> task = new Task<ImageView>() {
                        @Override
                        protected ImageView call() throws Exception {
                            return new ImageView(new Image(poster));
                        }
                    };
                    task.setOnSucceeded(event -> {
                        ImageView imageView = task.getValue();
                        imageView.setFitHeight(100);
                        imageView.setFitWidth(67);
                        imageViewCache.put(String.valueOf(id), imageView);
                        simpleObjectProperty.set(imageView);
                    });
                    new Thread(task).start();
                }
                return simpleObjectProperty;
            }
        });
        genresColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Film, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Film, String> param) {
                try {
                    // Get Connection
                    Connection connection = JDBCUtil.getConnecttion();

                    // Create Statement
                    String sql = "SELECT `name` FROM `film_genre` INNER JOIN `genres` ON film_genre.genreId = genres.id WHERE `filmId`=?;";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, param.getValue().getId());

                    // Execute SQL
                    ResultSet res = preparedStatement.executeQuery();

                    StringBuilder genresSB = new StringBuilder();
                    while (res.next()) genresSB.append(res.getString("name") + ", ");

                    // Close Connection
                    res.close();
                    connection.close();

                    return new SimpleStringProperty(genresSB.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // load 10 new film to tableview
        List<Film> filmsNew = FilmDAOImpl.getInstance().selectByCondition("ORDER BY `id` DESC LIMIT 10");
        newFilmTableView.getItems().addAll(filmsNew);

    }
}
