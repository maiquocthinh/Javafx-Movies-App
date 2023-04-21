package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.Utils.AppSessionUtil;
import com.schoolproject.javafxmoviesapp.Utils.JDBCUtil;
import com.schoolproject.javafxmoviesapp.Views.AdminView;
import com.schoolproject.javafxmoviesapp.Views.ClientView;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // load info user
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


    }

    @FXML
    void handleGotoAdmin(ActionEvent event) throws IOException {
        Stage adminStage = AppSessionUtil.getInstance().getAdminStage();
        if (adminStage.getScene() == null) AdminView.getInstance().switchToDashboard(adminStage);
        else adminStage.requestFocus();
    }

    @FXML
    void handleGotoProfile(ActionEvent event) throws IOException {
        Stage stage = (Stage) avatarImageView.getScene().getWindow();
        ClientView.getInstance().switchToProfile(stage);
    }

    @FXML
    void handleLogout(ActionEvent event) {

    }

    private void loadNotifications() throws SQLException, IOException {
        // clear old item
        notifiMenuButton.getItems().clear();

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Connection connection = JDBCUtil.getConnecttion();

        String sql = "SELECT `notifications`.`title`, `notifications`.`date` FROM `user_notification` "
                + "INNER JOIN `notifications` ON `user_notification`.`notificationId` = `notifications`.`id` "
                + "WHERE `user_notification`.`userId` = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, AppSessionUtil.getInstance().getUser().getId());

        ResultSet res = preparedStatement.executeQuery();

        while (res.next()) {
            String title = res.getString("title");
            Date date = res.getDate("date");

            Label labelNotifyName = new Label(title);
            labelNotifyName.getStyleClass().add("fs-base");
            Label labelNotifyDate = new Label(dateFormat.format(date));
            VBox vBox = new VBox(labelNotifyName, labelNotifyDate);
            CustomMenuItem customMenuItem = new CustomMenuItem(vBox);
            notifiMenuButton.getItems().add(customMenuItem);
        }

        preparedStatement.close();
        connection.close();

    }

    @FXML
    void handleSearch(ActionEvent event) throws IOException {
        searchAction();
    }

    @FXML
    void handleKeyPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) searchAction();
    }

    private void searchAction() throws IOException {
        String keywords = searchTextField.getText();
        Stage stage = (Stage) avatarImageView.getScene().getWindow();
        ClientView.getInstance().switchToSearchCatalogue(stage, keywords);
    }


}
