package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.NotificationDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import com.schoolproject.javafxmoviesapp.Entity.Notification;
import com.schoolproject.javafxmoviesapp.Utils.AppSessionUtil;
import com.schoolproject.javafxmoviesapp.Utils.JDBCUtil;
import com.schoolproject.javafxmoviesapp.Views.AdminView;
import com.schoolproject.javafxmoviesapp.Views.AuthView;
import com.schoolproject.javafxmoviesapp.Views.ClientView;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;


public class HeaderController implements Initializable {

    @FXML
    private ImageView avatarImageView;

    @FXML
    private MenuButton nameMenuButton;

    @FXML
    private MenuItem adminMenuItem;

    @FXML
    private MenuButton notifiMenuButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // load info user
        loadUserInfo();
    }

    @FXML
    void handleGotoAdmin(ActionEvent event) throws IOException {
        Stage adminStage = AppSessionUtil.getInstance().getAdminStage();
        if (adminStage.getScene() == null) {
            AdminView.getInstance().showDashboard(adminStage);
        } else {
            if (!adminStage.isShowing()) adminStage.show();
            adminStage.requestFocus();
        }
    }

    @FXML
    void handleGotoProfile(ActionEvent event) throws IOException {
        Stage stage = (Stage) avatarImageView.getScene().getWindow();
        ClientView.getInstance().switchToProfile(stage);
    }

    @FXML
    void handleGotoAuth(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage newStage = new Stage();
        newStage.initOwner(primaryStage);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setResizable(false);
        AuthView.getInstance().goToLogin(newStage);

        if (AppSessionUtil.getInstance().getUser() != null) {
            loadUserInfo();
        }
    }

    @FXML
    void handleLogout(ActionEvent event) {
        AppSessionUtil.getInstance().clear();
        AppSessionUtil.getInstance().getAdminStage().close();
        AppSessionUtil.getInstance().setAdminStage(null);
        adminMenuItem.setVisible(false);
        loadUserInfo();
    }

    private void loadUserInfo() {
        if (AppSessionUtil.getInstance().getUser() != null) {
            loginButton.setVisible(false);
            loginButton.setManaged(false);
            nameMenuButton.setVisible(true);
            nameMenuButton.setManaged(true);
            notifiMenuButton.setVisible(true);
            notifiMenuButton.setManaged(true);

            nameMenuButton.setText(AppSessionUtil.getInstance().getUser().getName());
            Task<Image> imageTask = new Task<Image>() {
                @Override
                protected Image call() throws Exception {
                    return new Image(AppSessionUtil.getInstance().getUser().getAvatar());
                }
            };
            imageTask.setOnSucceeded(event -> {
                avatarImageView.setImage(imageTask.getValue());
            });
            new Thread(imageTask).start();

            // if user is admin -> user can goto admin
            if (AppSessionUtil.getInstance().getRole() != null)
                if (AppSessionUtil.getInstance().getRole().getPermissions().size() > 0)
                    adminMenuItem.setVisible(true);

            // load notifications
            try {
                loadNotifications();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            loginButton.setVisible(true);
            loginButton.setManaged(true);
            nameMenuButton.setVisible(false);
            nameMenuButton.setManaged(false);
            notifiMenuButton.setVisible(false);
            notifiMenuButton.setManaged(false);
        }
    }

    private void loadNotifications() throws SQLException, IOException {
        // clear old item
        notifiMenuButton.getItems().clear();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        List<Notification> notificationList = NotificationDAOImpl.getInstance().selectByUserId(AppSessionUtil.getInstance().getUser().getId());

        for (Notification notification : notificationList) {
            Label labelNotifyName = new Label(notification.getTitle());
            labelNotifyName.getStyleClass().add("fs-base");
            Label labelNotifyDate = new Label(dateFormat.format(notification.getDate()));
            VBox vBox = new VBox(labelNotifyName, labelNotifyDate);
            CustomMenuItem customMenuItem = new CustomMenuItem(vBox);
            customMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        Stage stage = (Stage) avatarImageView.getScene().getWindow();
                        Film film = FilmDAOImpl.getInstance().findById(notification.getFilmId());
                        ClientView.getInstance().switchToFilmDetailInfo(stage, film);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            notifiMenuButton.getItems().add(customMenuItem);

        }


    }

    @FXML
    void handleSearch(MouseEvent event) throws IOException {
        searchAction();
    }

    @FXML
    void handleKeyPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) searchAction();
    }

    private void searchAction() throws IOException {
        String keywords = searchTextField.getText();
        if (!keywords.isEmpty()) {
            Stage stage = (Stage) avatarImageView.getScene().getWindow();
            ClientView.getInstance().switchToSearchCatalogue(stage, keywords);
        }
    }


}
