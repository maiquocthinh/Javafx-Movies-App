package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.CountryDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import com.schoolproject.javafxmoviesapp.Entity.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
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

public class ClientMoviecontroller implements Initializable {

    @FXML
    private FlowPane newFimlLayoutMovies;
    @FXML
    private Button button_Movies;

    @FXML
    private Button button_Series;

    @FXML
    private Button button_home;
    @FXML
    private Pagination pagination;

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

            newFimlLayoutMovies.getChildren().addAll(nodes);
        }




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

    public void setData(Genre genre){
        // lay ra danh sach phim thuoc thẻ loại genre
        // load danh sach do ra
    }

    public void setData(Country country){
        // lay ra danh sach phim thuoc quoc gia country
        // load danh sach do ra
    }

    public void setData(int year){
        // lay ra danh sach phim thuoc name year
        // load danh sach do ra
    }


    public void setData(String type){
        // lay ra danh sach phim thuoc loai type
        // load danh sach do ra
    }

}
