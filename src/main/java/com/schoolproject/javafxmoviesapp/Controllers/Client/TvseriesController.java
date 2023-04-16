package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.Entity.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TvseriesController implements Initializable {
    @FXML
    private Button button_Series;

    @FXML
    private Button button_home;

    @FXML
    private Button button_movies;

    @FXML
    private FlowPane newFimlLayoutSeries;

    @FXML
    void Event_Movies(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client-Movie.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Movies");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void Event_Tvseries(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client-Tvseries.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Tv series");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Event_home(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client-app.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Home");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        List<Movie> recentlyAdded = new ArrayList<>(recentlyAdded());
        {

            // Tạo ExecutorService với số lượng thread tùy chỉnh
            ExecutorService executorService = Executors.newFixedThreadPool(recentlyAdded.size());
            // Tạo danh sách Future để chứa kết quả từ các FXML Loader đã được load
            List<Future<Node>> futures = new ArrayList<>();

            // Vòng lặp for để tải các FXML Loader
            for (int i = 0; i < recentlyAdded.size(); i++) {
                int finalI = i;
                // Khởi tạo FXML Loader bất đồng bộ và thêm nó vào danh sách Future
                Future<Node> future = executorService.submit(() -> {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/MoviesCardVertical.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    MoviesCardController moviesCardController = fxmlLoader.getController();
                    moviesCardController.setData(recentlyAdded.get(finalI));
                    return anchorPane;
                });
                futures.add(future);
            }

            // Đợi cho tất cả các FXML Loader được load hoàn thành
            List<Node> nodes = new ArrayList<>();
            for (Future<Node> future : futures) {
                try {
                    Node node = future.get();
                    nodes.add(node);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            newFimlLayoutSeries.getChildren().addAll(nodes);
        }
    }

    {

    }

    public List<Movie> recentlyAdded() {
        List<Movie> listMovies = new ArrayList<>();


        listMovies.add(new Movie(
                "The New Scooby-Doo Movies (Season 1)",
                "https://i1.wp.com/img.ophim1.cc/uploads/movies/the-new-scooby-doo-movies-phan-1-thumb.jpg",
                6.5f,
                23,
                32
        ));
        listMovies.add(new Movie(
                "The New Scooby-Doo Movies (Season 1)",
                "https://i1.wp.com/img.ophim1.cc/uploads/movies/the-new-scooby-doo-movies-phan-1-thumb.jpg",
                6.5f,
                23,
                32
        ));
        listMovies.add(new Movie(
                "The New Scooby-Doo Movies (Season 1)",
                "https://i1.wp.com/img.ophim1.cc/uploads/movies/the-new-scooby-doo-movies-phan-1-thumb.jpg",
                6.5f,
                23,
                32
        ));
        listMovies.add(new Movie(
                "The New Scooby-Doo Movies (Season 1)",
                "https://i1.wp.com/img.ophim1.cc/uploads/movies/the-new-scooby-doo-movies-phan-1-thumb.jpg",
                6.5f,
                23,
                32
        ));
        listMovies.add(new Movie(
                "The New Scooby-Doo Movies (Season 1)",
                "https://i1.wp.com/img.ophim1.cc/uploads/movies/the-new-scooby-doo-movies-phan-1-thumb.jpg",
                6.5f,
                23,
                32

        ));
        return listMovies;
    }
}

