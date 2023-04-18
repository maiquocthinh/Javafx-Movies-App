package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.FilmDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.UserDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import com.schoolproject.javafxmoviesapp.Entity.Role;
import com.schoolproject.javafxmoviesapp.Entity.User;
import com.schoolproject.javafxmoviesapp.Utils.AppSessionUtil;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    private ImageView avatarImageView;

    @FXML
    private TextField avatarTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TilePane filmsFollowedTilePane;

    @FXML
    private Label roleNameLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    void handleUpdateProfile(ActionEvent event) {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        String name = nameTextField.getText();
        String avatar = avatarTextField.getText();
        String password = passwordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();
        // validate
        if (name.isEmpty()) {
            alertError.setContentText("Name must not be empty!");
            alertError.showAndWait();
            return;
        }
        if (avatar.isEmpty()) {
            alertError.setContentText("Avatar must not be empty!");
            alertError.showAndWait();
            return;
        }
        if (!password.isEmpty() && !confirmPassword.equals(password)) {
            alertError.setContentText("Password & Confirm Password is not match");
            alertError.showAndWait();
            return;
        }

        // update profile
        User user = AppSessionUtil.getInstance().getUser();
        user.setName(name);
        user.setAvatar(avatar);
        user.setPassword(password);
        UserDAOImpl.getInstance().update(user);
        user.setPassword("");

        // show update success
        alertInfo.setContentText("Update profile success!");
        alertInfo.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = AppSessionUtil.getInstance().getUser();
        Role role = AppSessionUtil.getInstance().getRole();
        nameLabel.setText(user.getName());
        roleNameLabel.setText(role.getName());
        nameTextField.setText(user.getName());
        emailTextField.setText(user.getEmail());
        avatarTextField.setText(user.getAvatar());

        Task<Image> imageTask = new Task<Image>() {
            @Override
            protected Image call() throws Exception {
                return new Image(user.getAvatar());
            }
        };
        imageTask.setOnSucceeded(event -> {
            avatarImageView.setImage(imageTask.getValue());
        });
        new Thread(imageTask).start();

        List<Film> filmsFollowed = FilmDAOImpl.getInstance().selectByCondition("INNER JOIN `follows` ON `films`.`id` = `follows`.`filmId` WHERE `follows`.`userId`=" + user.getId() + " ORDER BY `follows`.`date` DESC");
        try {
            for (Film film : filmsFollowed) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/FilmCardVertical.fxml"));
                filmsFollowedTilePane.getChildren().add(fxmlLoader.load());
                FilmCardVerticalController controller = fxmlLoader.getController();
                controller.setData(film);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
