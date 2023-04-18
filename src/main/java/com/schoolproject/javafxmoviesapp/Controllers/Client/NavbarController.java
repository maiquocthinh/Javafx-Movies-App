package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.CountryDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.GenreDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import com.schoolproject.javafxmoviesapp.Views.ClientView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.ResourceBundle;

public class NavbarController implements Initializable {

    @FXML
    private VBox genresVbox;
    @FXML
    private VBox countriesVbox;
    @FXML
    private VBox yearVbox;

    @FXML
    void Event_Home(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        ClientView.getInstance().switchToHome(stage);
    }

    @FXML
    void Event_Movies(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        ClientView.getInstance().switchToCatalogueByFilmType(stage, "Movie");
    }

    @FXML
    void Event_Tvseries(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        ClientView.getInstance().switchToCatalogueByFilmType(stage, "TV Series");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // goi len db lay danh sach the loai (genre)
        List<Genre> genres = GenreDAOImpl.getInstance().selectAll();
        // load tat ca vao genresVbox
        for (Genre genre : genres) {
            Button button = new Button(genre.getName());
            FontIcon fontIcon = new FontIcon("fas-angle-double-right");
            fontIcon.setIconColor(Color.WHITE);
            button.setGraphic(fontIcon);
            button.setMaxWidth(9999999);
            button.getStyleClass().add("bg--color__button");
            button.setTextFill(Color.WHITE);
            button.setContentDisplay(ContentDisplay.RIGHT);
            button.setAlignment(Pos.CENTER_RIGHT);

            // set su kien nhan nut
            button.setOnAction(event -> {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                try {
                    ClientView.getInstance().switchToCatalogueByGenre(stage, genre);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            genresVbox.getChildren().add(button);
        }

        // countries
        List<Country> countries = CountryDAOImpl.getInstance().selectAll();
        // load tat ca vao countriesVbox
        for (Country country : countries) {
            Button button = new Button(country.getName());
            FontIcon fontIcon = new FontIcon("fas-angle-double-right");
            fontIcon.setIconColor(Color.WHITE);
            button.setGraphic(fontIcon);
            button.setMaxWidth(9999999);
            button.getStyleClass().add("bg--color__button");
            button.setTextFill(Color.WHITE);
            button.setContentDisplay(ContentDisplay.RIGHT);
            button.setAlignment(Pos.CENTER_RIGHT);

            // set su kien nhan nut
            button.setOnAction(event -> {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                try {
                    ClientView.getInstance().switchToCatalogueByCountry(stage,country);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            countriesVbox.getChildren().add(button);
        }


        // year
        // load tat ca vao yearVbox
        // get ra nam hien tai . vd: 2023, lấy 15 năm từ 2023 trở về sau
        int currentYear = Year.now().getValue();
        for (int i = currentYear; i > currentYear - 15 ; i--) {
            Button button = new Button(String.valueOf(i));
            FontIcon fontIcon = new FontIcon("fas-angle-double-right");
            fontIcon.setIconColor(Color.WHITE);
            button.setGraphic(fontIcon);
            button.setMaxWidth(9999999);
            button.getStyleClass().add("bg--color__button");
            button.setTextFill(Color.WHITE);
            button.setContentDisplay(ContentDisplay.RIGHT);
            button.setAlignment(Pos.CENTER_RIGHT);

            // set su kien nhan nut
            int year = i;
            button.setOnAction(event -> {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                try {
                    ClientView.getInstance().switchToCatalogueByYear(stage, year);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            yearVbox.getChildren().add(button);
        }
    }
}
