package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import com.schoolproject.javafxmoviesapp.Utils.CheckPermissionUtil;
import com.schoolproject.javafxmoviesapp.Utils.JDBCUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

public class ListFilmController implements Initializable {
    @FXML
    private ScrollPane navbar;
    @FXML
    private NavbarController navbarController;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<Film> filmTable;
    @FXML
    private TableColumn<Film, Integer> idColumn;
    @FXML
    private TableColumn<Film, ImageView> posterColumn;
    @FXML
    private TableColumn<Film, String> nameColumn;
    @FXML
    private TableColumn<Film, String> genresColumn;
    @FXML
    private TableColumn<Film, String> statusColumn;
    @FXML
    private TableColumn<Film, Integer> episodesColumn;
    @FXML
    private TableColumn<Film, Void> actionColumn;
    @FXML
    private Pagination pagination;
    private Map<String, ImageView> imageViewCache = new HashMap<String, ImageView>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navbarController.getFilmsTitledPane().setExpanded(true);
        navbarController.getNavListFilms().getStyleClass().add("activeNav");

        idColumn.setStyle("-fx-alignment: CENTER;");
        posterColumn.setStyle("-fx-alignment: CENTER;");
        nameColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        genresColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        statusColumn.setStyle("-fx-alignment: CENTER;");
        episodesColumn.setStyle("-fx-alignment: CENTER;");
        actionColumn.setStyle("-fx-alignment: CENTER;");

        idColumn.setCellValueFactory(new PropertyValueFactory<Film, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Film, String>("name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Film, String>("status"));
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
                    String sql = "SELECT `name` FROM `film_genre` INNER JOIN `genres` ON film_genre.genreId = genres.id WHERE `filmId` = ?";
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
        episodesColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Film, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Film, Integer> param) {
                try {
                    // Get Connection
                    Connection connection = JDBCUtil.getConnecttion();

                    // Create Statement
                    String sql = "SELECT COUNT(*) AS episodes FROM `episodes` WHERE `filmId`=?;";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, param.getValue().getId());

                    // Execute SQL
                    ResultSet res = preparedStatement.executeQuery();

                    int episodes = 0;
                    if (res.next()) episodes = res.getInt("episodes");

                    // Close Connection
                    res.close();
                    connection.close();

                    return new SimpleObjectProperty<Integer>(episodes);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        actionColumn.setCellFactory(new Callback<TableColumn<Film, Void>, TableCell<Film, Void>>() {
            @Override
            public TableCell<Film, Void> call(TableColumn<Film, Void> param) {
                return new TableCell<Film, Void>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");

                    {
                        editButton.setOnAction((ActionEvent event) -> {
                            // check permission edit film
                            if(!CheckPermissionUtil.getInstance().check("Update Film")){
                                Alert alertError = new Alert(Alert.AlertType.ERROR);
                                alertError.setContentText("You don't have permission to edit film!!!");
                                alertError.showAndWait();
                                return;
                            }
                            Film film = getTableView().getItems().get(getIndex());
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            try {
                                openDialogEditGenre(film, stage);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            getTableView().refresh();
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            // check permission delete film
                            if(!CheckPermissionUtil.getInstance().check("Delete Film")){
                                Alert alertError = new Alert(Alert.AlertType.ERROR);
                                alertError.setContentText("You don't have permission to delete film!!!");
                                alertError.showAndWait();
                                return;
                            }
                            Film film = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("Are you sure you want to delete this film?");
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.OK) {
                                // delete on ui
                                getTableView().getItems().remove(film);
                                // delete on database
                                FilmDAOImpl.getInstance().delete(film);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox();
                            buttons.getChildren().addAll(editButton, deleteButton);
                            buttons.setAlignment(Pos.CENTER);
                            buttons.setSpacing(8);
                            setGraphic(buttons);
                        }
                    }
                };
            }
        });

        // init info pagination
        pagination.setCurrentPageIndex(0);
        pagination.setPageCount(Math.ceilDiv(FilmDAOImpl.getInstance().countAll(), RecordPerPage));
        pagination.setMaxPageIndicatorCount(pagination.getPageCount());
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer currentPageIndex) {
                List<Film> films = FilmDAOImpl.getInstance().selectByCondition(generatePaginationSQL(currentPageIndex));
                filmTable.getItems().clear();
                filmTable.getItems().addAll(films);
                return filmTable;
            }
        });
    }

    private void openDialogEditGenre(Film film, Stage stage) throws IOException, SQLException {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/EditFilm.fxml"));
        Scene dialogScene = new Scene(fxmlLoader.load());
        EditFilmController editFilmController = fxmlLoader.getController();
        editFilmController.setData(film);
        dialog.setTitle("Edit Film");
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.showAndWait();
    }

    @FXML
    void handleButtonSearchClick(MouseEvent event) {
        handleSearch();
    }

    @FXML
    void handleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) handleSearch();
    }

    // pagination
    private int RecordPerPage = 15;

    // SQL String conditions
    private String searchSQL;

    private String generatePaginationSQL() {
        return "ORDER BY `id` DESC LIMIT " + RecordPerPage + " OFFSET " + pagination.getCurrentPageIndex() * RecordPerPage;
    }

    private String generatePaginationSQL(int currentPageIndex) {
        return "ORDER BY `id` DESC LIMIT " + RecordPerPage + " OFFSET " + currentPageIndex * RecordPerPage;
    }

    private void handleSearch() {
        String searchKey = searchTextField.getText();
        searchSQL = searchKey.isEmpty() ? "" : "WHERE LOWER(`name`) LIKE '%" + searchKey.toLowerCase() + "%'";
        int totalRecord = searchKey.isEmpty() ? FilmDAOImpl.getInstance().countAll() : FilmDAOImpl.getInstance().countByCondition(searchSQL);
        // pagination
        pagination.setCurrentPageIndex(0);
        pagination.setPageCount(Math.ceilDiv(totalRecord, RecordPerPage));
        pagination.setMaxPageIndicatorCount(pagination.getPageCount());
        // Load films and add into filmTable
        List<Film> films = FilmDAOImpl.getInstance().selectByCondition(searchSQL + " " + generatePaginationSQL());
        filmTable.getItems().clear();
        filmTable.getItems().addAll(films);
    }

}
