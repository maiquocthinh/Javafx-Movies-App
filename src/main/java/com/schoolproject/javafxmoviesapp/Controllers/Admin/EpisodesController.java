package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.EpisodeDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Episode;
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
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EpisodesController implements Initializable {
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<Episode> episodesTable;

    @FXML
    private TableColumn<Episode, Integer> idColumn;

    @FXML
    private TableColumn<Episode, String> nameColumn;

    @FXML
    private TableColumn<Episode, String> linkColumn;

    @FXML
    private TableColumn<Episode, Void> actionsColumn;

    @FXML
    private Pagination pagination;
    private int filmId;

    public void setFilmId(int filmId) {
        this.filmId = filmId;
        // init info pagination when filmId init
        pagination.setCurrentPageIndex(0);
        pagination.setPageCount(Math.ceilDiv(EpisodeDAOImpl.getInstance().countByCondition("WHERE `filmId`=" + filmId), RecordPerPage));
        pagination.setMaxPageIndicatorCount(pagination.getPageCount());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setStyle("-fx-alignment: CENTER;");
        nameColumn.setStyle("-fx-alignment: CENTER;");
        linkColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        actionsColumn.setStyle("-fx-alignment: CENTER;");

        idColumn.setCellValueFactory(new PropertyValueFactory<Episode, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Episode, String>("name"));
        linkColumn.setCellValueFactory(new PropertyValueFactory<Episode, String>("link"));
        actionsColumn.setCellFactory(new Callback<TableColumn<Episode, Void>, TableCell<Episode, Void>>() {
            @Override
            public TableCell<Episode, Void> call(TableColumn<Episode, Void> param) {
                return new TableCell<Episode, Void>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");

                    {
                        editButton.setOnAction((ActionEvent event) -> {
                            // check permission edit episode
                            if (!CheckPermissionUtil.getInstance().check("Update Episode")) {
                                Alert alertError = new Alert(Alert.AlertType.ERROR);
                                alertError.setContentText("You don't have permission to edit episode!!!");
                                alertError.showAndWait();
                                return;
                            }
                            Episode episode = getTableView().getItems().get(getIndex());
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            // show editEpisode dialog
                            try {
                                openDialogEditEpisode(episode, stage);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            getTableView().refresh();
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            // check permission delete episode
                            if (!CheckPermissionUtil.getInstance().check("Delete Episode")) {
                                Alert alertError = new Alert(Alert.AlertType.ERROR);
                                alertError.setContentText("You don't have permission to delete episode!!!");
                                alertError.showAndWait();
                                return;
                            }
                            Episode episode = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("Are you sure you want to delete this episode?");
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.OK) {
                                // delete on ui
                                getTableView().getItems().remove(episode);
                                // delete on database
                                EpisodeDAOImpl.getInstance().delete(episode);
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

        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer currentPageIndex) {
                List<Episode> episodes = EpisodeDAOImpl.getInstance().selectByCondition("WHERE `filmId`=" + filmId + generatePaginationSQL(currentPageIndex));
                episodesTable.getItems().clear();
                episodesTable.getItems().addAll(episodes);
                return episodesTable;
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
    void openDialogCreateEpisode(MouseEvent event) throws IOException {
        // check permission create episode
        if (!CheckPermissionUtil.getInstance().check("Create Episode")) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setContentText("You don't have permission to create new episode!!!");
            alertError.showAndWait();
            return;
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/AddEpisode.fxml"));
        Scene dialogScene = new Scene(fxmlLoader.load());
        AddEpisodeController addEpisodeController = fxmlLoader.getController();
        addEpisodeController.setFilmId(filmId);
        dialog.setTitle("Create Episode");
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.showAndWait();

        List<Episode> episodes = EpisodeDAOImpl.getInstance().selectByCondition("WHERE `filmId`=" + filmId + generatePaginationSQL());
        episodesTable.getItems().clear();
        episodesTable.getItems().addAll(episodes);
    }

    private void openDialogEditEpisode(Episode episode, Stage stage) throws IOException {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/EditEpisode.fxml"));
        Scene dialogScene = new Scene(fxmlLoader.load());
        EditEpisodeController editEpisodeController = fxmlLoader.getController();
        editEpisodeController.setData(episode);
        dialog.setTitle("Edit Episode");
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.showAndWait();
    }

    // pagination
    private int RecordPerPage = 20;

    // SQL String conditions
    private String searchSQL;

    private String generatePaginationSQL() {
        return " ORDER BY `id` DESC LIMIT " + RecordPerPage + " OFFSET " + pagination.getCurrentPageIndex() * RecordPerPage;
    }

    private String generatePaginationSQL(int currentPageIndex) {
        return " ORDER BY `id` DESC LIMIT " + RecordPerPage + " OFFSET " + currentPageIndex * RecordPerPage;
    }

    private void handleSearch() {
        String searchKey = searchTextField.getText();
        searchSQL = searchKey.isEmpty() ? "" : " AND LOWER(`name`) LIKE '%" + searchKey.toLowerCase() + "%'";
        int totalRecord = searchKey.isEmpty() ? EpisodeDAOImpl.getInstance().countByCondition("WHERE `filmId`=" + filmId) : EpisodeDAOImpl.getInstance().countByCondition("WHERE `filmId`=" + filmId + searchSQL);
        // pagination
        pagination.setCurrentPageIndex(0);
        pagination.setPageCount(Math.ceilDiv(totalRecord, RecordPerPage));
        pagination.setMaxPageIndicatorCount(pagination.getPageCount());
        // Load episodes and add into episodesTable
        List<Episode> episodes = EpisodeDAOImpl.getInstance().selectByCondition("WHERE `filmId`=" + filmId + searchSQL + generatePaginationSQL());
        episodesTable.getItems().clear();
        episodesTable.getItems().addAll(episodes);
    }

}
