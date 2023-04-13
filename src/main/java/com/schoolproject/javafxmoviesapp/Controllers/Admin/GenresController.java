package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.GenreDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import com.schoolproject.javafxmoviesapp.Utils.CheckPermissionUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GenresController implements Initializable {
    @FXML
    private ScrollPane navbar;
    @FXML
    private NavbarController navbarController;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<Genre> genreTable;
    @FXML
    private TableColumn<Genre, Integer> idColumn;
    @FXML
    private TableColumn<Genre, String> nameColumn;
    @FXML
    private TableColumn<Genre, Void> actionColumn;
    @FXML
    private Pagination pagination;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navbarController.getNavGenres().getStyleClass().add("activeNav");

        idColumn.setStyle("-fx-alignment: CENTER;");
        nameColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        actionColumn.setStyle("-fx-alignment: CENTER;");

        idColumn.setCellValueFactory(new PropertyValueFactory<Genre, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Genre, String>("name"));
        actionColumn.setCellFactory(new Callback<TableColumn<Genre, Void>, TableCell<Genre, Void>>() {
            @Override
            public TableCell<Genre, Void> call(TableColumn<Genre, Void> param) {
                return new TableCell<Genre, Void>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");

                    {
                        editButton.setOnAction((ActionEvent event) -> {
                            // check permission edit genre
                            if (!CheckPermissionUtil.getInstance().check("Update Genre")) {
                                Alert alertError = new Alert(Alert.AlertType.ERROR);
                                alertError.setContentText("You don't have permission to edit genre!!!");
                                alertError.showAndWait();
                                return;
                            }
                            Genre genre = getTableView().getItems().get(getIndex());
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            // show editGenre dialog
                            try {
                                openDialogEditGenre(genre, stage);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            getTableView().refresh();
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            // check permission delete genre
                            if (!CheckPermissionUtil.getInstance().check("Delete Genre")) {
                                Alert alertError = new Alert(Alert.AlertType.ERROR);
                                alertError.setContentText("You don't have permission to delete genre!!!");
                                alertError.showAndWait();
                                return;
                            }
                            Genre genre = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("Are you sure you want to delete this genre?");
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.OK) {
                                // delete on ui
                                getTableView().getItems().remove(genre);
                                // delete on database
                                GenreDAOImpl.getInstance().delete(genre);
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
        pagination.setPageCount(Math.ceilDiv(GenreDAOImpl.getInstance().countAll(), RecordPerPage));
        pagination.setMaxPageIndicatorCount(pagination.getPageCount());
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer currentPageIndex) {
                List<Genre> genres = GenreDAOImpl.getInstance().selectByCondition(generatePaginationSQL(currentPageIndex));
                genreTable.getItems().clear();
                genreTable.getItems().addAll(genres);
                return genreTable;
            }
        });
    }

    @FXML
    void handleButtonSearchClick(MouseEvent event) {
        handleSearch();
    }

    @FXML
    void handleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) handleSearch();
    }

    @FXML
    void openDialogCreateGenre(MouseEvent event) throws IOException {
        // check permission create genre
        if (!CheckPermissionUtil.getInstance().check("Create Genre")) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setContentText("You don't have permission to create new genre!!!");
            alertError.showAndWait();
            return;
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/AddGenre.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        AddGenreController addGenreController = fxmlLoader.getController();
        Scene dialogScene = new Scene(anchorPane);
        dialog.setTitle("Create Genre");
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.showAndWait();

        if (addGenreController.getGenre() != null) {
            // pagination
            pagination.setCurrentPageIndex(0);
            pagination.setPageCount(Math.ceilDiv(GenreDAOImpl.getInstance().countAll(), RecordPerPage));
            pagination.setMaxPageIndicatorCount(pagination.getPageCount());
            // Load genres and add into genreTable
            List<Genre> genres = GenreDAOImpl.getInstance().selectByCondition(generatePaginationSQL());
            genreTable.getItems().clear();
            genreTable.getItems().addAll(genres);
        }
    }

    private void openDialogEditGenre(Genre genre, Stage stage) throws IOException {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/EditGenres.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        EditGenreController editGenreController = fxmlLoader.getController();
        editGenreController.setData(genre);
        Scene dialogScene = new Scene(anchorPane);
        dialog.setTitle("Edit Genre");
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.showAndWait();
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
        int totalRecord = searchKey.isEmpty() ? GenreDAOImpl.getInstance().countAll() : GenreDAOImpl.getInstance().countByCondition(searchSQL);
        // pagination
        pagination.setCurrentPageIndex(0);
        pagination.setPageCount(Math.ceilDiv(totalRecord, RecordPerPage));
        pagination.setMaxPageIndicatorCount(pagination.getPageCount());
        // Load genres and add into genreTable
        List<Genre> genres = GenreDAOImpl.getInstance().selectByCondition(searchSQL + " " + generatePaginationSQL());
        genreTable.getItems().clear();
        genreTable.getItems().addAll(genres);
    }

}
