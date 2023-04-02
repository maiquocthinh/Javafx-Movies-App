package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.CountryDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Country;
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

public class CountriesController implements Initializable {
    @FXML
    private ScrollPane navbar;
    @FXML
    private NavbarController navbarController;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<Country> countryTable;
    @FXML
    private TableColumn<Country, Integer> idColumn;
    @FXML
    private TableColumn<Country, String> nameColumn;
    @FXML
    private TableColumn<Country, Void> actionColumn;
    @FXML
    private Pagination pagination;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navbarController.getNavCountries().getStyleClass().add("activeNav");

        idColumn.setStyle("-fx-alignment: CENTER;");
        nameColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        actionColumn.setStyle("-fx-alignment: CENTER;");

        idColumn.setCellValueFactory(new PropertyValueFactory<Country, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Country, String>("name"));
        actionColumn.setCellFactory(new Callback<TableColumn<Country, Void>, TableCell<Country, Void>>() {
            @Override
            public TableCell<Country, Void> call(TableColumn<Country, Void> param) {
                return new TableCell<Country, Void>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");

                    {
                        editButton.setOnAction((ActionEvent event) -> {
                            Country country = getTableView().getItems().get(getIndex());
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            // show editCountry dialog
                            try {
                                openDialogEditCountry(country, stage);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            getTableView().refresh();
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            Country country = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("Are you sure you want to delete this genre?");
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.OK) {
                                // delete on ui
                                getTableView().getItems().remove(country);
                                // delete on database
                                CountryDAOImpl.getInstance().delete(country);
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
        pagination.setPageCount(Math.ceilDiv(CountryDAOImpl.getInstance().countAll(), RecordPerPage));
        pagination.setMaxPageIndicatorCount(pagination.getPageCount());
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer currentPageIndex) {
                List<Country> countries = CountryDAOImpl.getInstance().selectByCondition(generatePaginationSQL(currentPageIndex));
                countryTable.getItems().clear();
                countryTable.getItems().addAll(countries);
                return countryTable;
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
    void openDialogCreateCountry(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/AddCountry.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        AddCountryController addCountryController = fxmlLoader.getController();
        Scene dialogScene = new Scene(anchorPane);
        dialog.setTitle("Create Country");
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.showAndWait();

        if (addCountryController.getCountry() != null) {
            // pagination
            pagination.setCurrentPageIndex(0);
            pagination.setPageCount(Math.ceilDiv(CountryDAOImpl.getInstance().countAll(), RecordPerPage));
            pagination.setMaxPageIndicatorCount(pagination.getPageCount());
            // Load countries and add into countryTable
            List<Country> countries = CountryDAOImpl.getInstance().selectByCondition(generatePaginationSQL());
            countryTable.getItems().clear();
            countryTable.getItems().addAll(countries);
        }
    }

    private void openDialogEditCountry(Country country, Stage stage) throws IOException {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/EditCounty.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        EditCountyController editCountyController = fxmlLoader.getController();
        editCountyController.setDate(country);
        Scene dialogScene = new Scene(anchorPane);
        dialog.setTitle("Edit Country");
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
        int totalRecord = searchKey.isEmpty() ? CountryDAOImpl.getInstance().countAll() : CountryDAOImpl.getInstance().countByCondition(searchSQL);
        // pagination
        pagination.setCurrentPageIndex(0);
        pagination.setPageCount(Math.ceilDiv(totalRecord, RecordPerPage));
        pagination.setMaxPageIndicatorCount(pagination.getPageCount());
        // Load countries and add into countryTable
        List<Country> countries = CountryDAOImpl.getInstance().selectByCondition(searchSQL + " " + generatePaginationSQL());
        countryTable.getItems().clear();
        countryTable.getItems().addAll(countries);
    }

}
