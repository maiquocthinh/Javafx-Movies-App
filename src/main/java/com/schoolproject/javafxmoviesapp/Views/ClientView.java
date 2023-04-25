package com.schoolproject.javafxmoviesapp.Views;

import com.schoolproject.javafxmoviesapp.Controllers.Client.FilmCatalogueController;
import com.schoolproject.javafxmoviesapp.Controllers.Client.FilmDetailInfoController;
import com.schoolproject.javafxmoviesapp.Controllers.Client.FilmWatchController;
import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientView {
    private static ClientView instance = null;

    public static ClientView getInstance() {
        if (instance == null) instance = new ClientView();
        return instance;
    }


    public void switchToHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/Home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Home");
        if (!stage.isShowing()) stage.show();
    }

    public void switchToProfile(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/Profile.fxml"));
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.setTitle("Profile");
        if (!stage.isShowing()) stage.show();
    }

    public void switchToCatalogueFilmsPopular(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCatalogue.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        FilmCatalogueController controller = fxmlLoader.getController();
        controller.filterFilmPopular();
        stage.setScene(scene);
        stage.setTitle("Catalogue: Films Popular");
        if (!stage.isShowing()) stage.show();
    }

    public void switchToCatalogueNewFilms(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCatalogue.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        FilmCatalogueController controller = fxmlLoader.getController();
        controller.filterFilmNew();
        stage.setScene(scene);
        stage.setTitle("Catalogue: Films New");
        if (!stage.isShowing()) stage.show();
    }

    public void switchToCatalogueByGenre(Stage stage, Genre genre) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCatalogue.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        FilmCatalogueController controller = fxmlLoader.getController();
        controller.filterFilmByGenre(genre);
        stage.setScene(scene);
        stage.setTitle("Catalogue: Films By Genre");
        if (!stage.isShowing()) stage.show();
    }

    public void switchToCatalogueByCountry(Stage stage, Country country) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCatalogue.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        FilmCatalogueController controller = fxmlLoader.getController();
        controller.filterFilmByCountry(country);
        stage.setScene(scene);
        stage.setTitle("Catalog: Films By Country");
        if (!stage.isShowing()) stage.show();
    }

    public void switchToCatalogueByYear(Stage stage, int year) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCatalogue.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        FilmCatalogueController controller = fxmlLoader.getController();
        controller.filterFilmByYear(year);
        stage.setScene(scene);
        stage.setTitle("Catalog: Films By Year");
        if (!stage.isShowing()) stage.show();
    }


    public void switchToCatalogueByFilmType(Stage stage, String typeFilm) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCatalogue.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        FilmCatalogueController controller = fxmlLoader.getController();
        controller.filterFilmByType(typeFilm);
        stage.setScene(scene);
        stage.setTitle("Catalog: Films By Type");
        if (!stage.isShowing()) stage.show();
    }


    public void switchToSearchCatalogue(Stage stage, String keywords) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCatalogue.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        FilmCatalogueController controller = fxmlLoader.getController();
        controller.filterFilmByKeywords(keywords);
        stage.setScene(scene);
            stage.setTitle("Catalog: Films By Result Search");
        if (!stage.isShowing()) stage.show();
    }


    public void switchToFilmDetailInfo(Stage stage, int filmId) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmDetailInfo.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        FilmDetailInfoController controller = fxmlLoader.getController();
        controller.setFilmId(filmId);
        stage.setScene(scene);
        stage.setTitle("Film Detail Info");
        if (!stage.isShowing()) stage.show();
    }

    public void switchToFilmWatch(Stage stage, int filmId) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmWatch.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        FilmWatchController controller = fxmlLoader.getController();
        controller.setFilmId(filmId);
        stage.setScene(scene);
        stage.setTitle("Film Watch");
        if (!stage.isShowing()) stage.show();
    }

}
