package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.UserDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.User;
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

public class UsersController implements Initializable {
    @FXML
    private ScrollPane navbar;

    @FXML
    private NavbarController navbarController;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, ImageView> avatarColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableColumn<User, Void> actionsColumn;

    @FXML
    private Pagination pagination;

    private Map<String, ImageView> imageViewCache = new HashMap<String, ImageView>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navbarController.getAuthTitledPane().setExpanded(true);
        navbarController.getNavUsers().getStyleClass().add("activeNav");

        idColumn.setStyle("-fx-alignment: CENTER;");
        avatarColumn.setStyle("-fx-alignment: CENTER;");
        nameColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        roleColumn.setStyle("-fx-alignment: CENTER;");
        actionsColumn.setStyle("-fx-alignment: CENTER;");

        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        avatarColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, ImageView>, ObservableValue<ImageView>>() {
            @Override
            public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<User, ImageView> param) {
                SimpleObjectProperty simpleObjectProperty = new SimpleObjectProperty<>();
                int id = param.getValue().getId();
                String avatar = param.getValue().getAvatar();
                // check in cache
                if (imageViewCache.containsKey(String.valueOf(id)+avatar)) {
                    // if exits in cache -> get this & set for simpleObjectProperty
                    simpleObjectProperty.set(imageViewCache.get(String.valueOf(id)+avatar));
                } else {
                    // if not exits in cache -> load by thread -> store into cache ->  set for simpleObjectProperty
                    Task<ImageView> task = new Task<ImageView>() {
                        @Override
                        protected ImageView call() throws Exception {
                            return new ImageView(new Image(avatar));
                        }
                    };
                    task.setOnSucceeded(event -> {
                        ImageView imageView = task.getValue();
                        imageView.setFitHeight(50);
                        imageView.setFitWidth(50);
                        imageViewCache.put(String.valueOf(id)+avatar, imageView);
                        simpleObjectProperty.set(imageView);
                    });
                    new Thread(task).start();
                }
                return simpleObjectProperty;
            }
        });
        roleColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {
                try {
                    // Get Connection
                    Connection connection = JDBCUtil.getConnecttion();

                    // Create Statement
                    String sql = "SELECT `roles`.`name` FROM `users` INNER JOIN `roles` ON `users`.`roleId` = `roles`.`id` WHERE `users`.`id` = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, param.getValue().getId());

                    // Execute SQL
                    ResultSet res = preparedStatement.executeQuery();

                    String roleName = "";
                    if (res.next()) roleName = res.getString(1);

                    // Close Connection
                    res.close();
                    connection.close();

                    return new SimpleStringProperty(roleName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        actionsColumn.setCellFactory(new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(TableColumn<User, Void> param) {
                return new TableCell<User, Void>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");

                    {
                        editButton.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            try {
                                openDialogEditUser(user, stage);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            getTableView().refresh();
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("Are you sure you want to delete this user?");
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.OK) {
                                // delete on ui
                                getTableView().getItems().remove(user);
                                // delete on database
                                UserDAOImpl.getInstance().delete(user);
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
        pagination.setPageCount(Math.ceilDiv(UserDAOImpl.getInstance().countAll(), RecordPerPage));
        pagination.setMaxPageIndicatorCount(pagination.getPageCount());
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer currentPageIndex) {
                List<User> users = UserDAOImpl.getInstance().selectByCondition(generatePaginationSQL(currentPageIndex));
                usersTable.getItems().clear();
                usersTable.getItems().addAll(users);
                return usersTable;
            }
        });
    }

    @FXML
    void openDialogCreateUser(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/AddUser.fxml"));
        Scene dialogScene = new Scene(fxmlLoader.load());
        dialog.setTitle("Create User");
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.showAndWait();

        AddUserController addUserController = fxmlLoader.getController();
        if (addUserController.getUser() != null) {
            // pagination
            pagination.setCurrentPageIndex(0);
            pagination.setPageCount(Math.ceilDiv(UserDAOImpl.getInstance().countAll(), RecordPerPage));
            pagination.setMaxPageIndicatorCount(pagination.getPageCount());
            // Load users and add into usersTable
            List<User> users = UserDAOImpl.getInstance().selectByCondition(generatePaginationSQL());
            usersTable.getItems().clear();
            usersTable.getItems().addAll(users);
        }
    }

    private void openDialogEditUser(User user, Stage stage) throws IOException {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/EditUser.fxml"));
        Scene dialogScene = new Scene(fxmlLoader.load());
        EditUserController editUserController = fxmlLoader.getController();
        editUserController.setData(user);
        dialog.setTitle("Edit User");
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
    private int RecordPerPage = 20;

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
        int totalRecord = searchKey.isEmpty() ? UserDAOImpl.getInstance().countAll() : UserDAOImpl.getInstance().countByCondition(searchSQL);
        // pagination
        pagination.setCurrentPageIndex(0);
        pagination.setPageCount(Math.ceilDiv(totalRecord, RecordPerPage));
        pagination.setMaxPageIndicatorCount(pagination.getPageCount());
        // Load users and add into usersTable
        List<User> users = UserDAOImpl.getInstance().selectByCondition(searchSQL + " " + generatePaginationSQL());
        usersTable.getItems().clear();
        usersTable.getItems().addAll(users);
    }


}
