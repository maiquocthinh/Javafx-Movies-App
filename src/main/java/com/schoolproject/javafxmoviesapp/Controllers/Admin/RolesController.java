package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.RoleDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Role;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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

public class RolesController implements Initializable {
    @FXML
    private ScrollPane navbar;
    @FXML
    private NavbarController navbarController;
    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<Role> rolesTable;

    @FXML
    private TableColumn<Role, String> nameColumn;

    @FXML
    private TableColumn<Role, String> permissionsColumn;

    @FXML
    private TableColumn<Role, Void> actionsColumn;


    @FXML
    private Pagination pagination;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navbarController.getAuthTitledPane().setExpanded(true);
        navbarController.getNavRoles().getStyleClass().add("activeNav");


        nameColumn.setStyle("-fx-alignment: CENTER;");
        permissionsColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        actionsColumn.setStyle("-fx-alignment: CENTER;");

        nameColumn.setCellValueFactory(new PropertyValueFactory<Role, String>("name"));
        permissionsColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Role, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Role, String> param) {
                return new SimpleStringProperty(String.join(", ", param.getValue().getPermissions()));
            }
        });
        actionsColumn.setCellFactory(new Callback<TableColumn<Role, Void>, TableCell<Role, Void>>() {
            @Override
            public TableCell<Role, Void> call(TableColumn<Role, Void> param) {
                return new TableCell<Role, Void>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");

                    {
                        editButton.setOnAction((ActionEvent event) -> {
                            Role role = getTableView().getItems().get(getIndex());
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            // show editGenre dialog
                            try {
                                openDialogEditRole(role, stage);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            getTableView().refresh();
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            Role role = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("Are you sure you want to delete this genre?");
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.OK) {
                                // delete on ui
                                getTableView().getItems().remove(role);
                                // delete on database
                                RoleDAOImpl.getInstance().delete(role);
                            }
                        });
                    }

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

        // inti info pagination
        pagination.setCurrentPageIndex(0);
        pagination.setPageCount(Math.ceilDiv(RoleDAOImpl.getInstance().countAll(), RecordPerPage));
        pagination.setMaxPageIndicatorCount(pagination.getPageCount());
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer currentPageIndex) {
                List<Role> roles = RoleDAOImpl.getInstance().selectByCondition(generatePaginationSQL(currentPageIndex));
                rolesTable.getItems().clear();
                rolesTable.getItems().addAll(roles);
                return rolesTable;
            }
        });
    }

    @FXML
    void handleButtonSearchClick(MouseEvent event) {
        handleSearch();
    }

    @FXML
    void handleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER))  handleSearch();
    }

    @FXML
    void openDialogCreateRole(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/AddRole.fxml"));
        Scene dialogScene = new Scene(fxmlLoader.load());
        dialog.setTitle("Create Role");
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.showAndWait();

        AddRoleController addRoleController = fxmlLoader.getController();
        if(addRoleController.getRole() != null){
            // pagination
            pagination.setCurrentPageIndex(0);
            pagination.setPageCount(Math.ceilDiv(RoleDAOImpl.getInstance().countAll(), RecordPerPage));
            pagination.setMaxPageIndicatorCount(pagination.getPageCount());
            // Load roles and add into rolesTable
            List<Role> roles = RoleDAOImpl.getInstance().selectByCondition(generatePaginationSQL());
            rolesTable.getItems().clear();
            rolesTable.getItems().addAll(roles);
        }
    }

    private void openDialogEditRole(Role role, Stage stage) throws IOException {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/EditRole.fxml"));
        Scene dialogScene = new Scene(fxmlLoader.load());
        EditRoleController editRoleController = fxmlLoader.getController();
        editRoleController.setData(role);
        dialog.setTitle("Edit Role");
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
        int totalRecord = searchKey.isEmpty() ? RoleDAOImpl.getInstance().countAll() : RoleDAOImpl.getInstance().countByCondition(searchSQL);
        // pagination
        pagination.setCurrentPageIndex(0);
        pagination.setPageCount(Math.ceilDiv(totalRecord, RecordPerPage));
        pagination.setMaxPageIndicatorCount(pagination.getPageCount());
        // Load roles and add into rolesTable
        List<Role> roles = RoleDAOImpl.getInstance().selectByCondition(searchSQL + " " + generatePaginationSQL());
        rolesTable.getItems().clear();
        rolesTable.getItems().addAll(roles);
    }
}
