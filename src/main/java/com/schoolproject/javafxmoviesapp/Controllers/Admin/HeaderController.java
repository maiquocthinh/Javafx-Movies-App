package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.Entity.User;
import com.schoolproject.javafxmoviesapp.Utils.AppSessionUtil;
import com.schoolproject.javafxmoviesapp.Views.AdminView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HeaderController implements Initializable {

    @FXML
    private ImageView avatarImageView;

    @FXML
    private Label usernameTextField;

    @FXML
    void navToProfile(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminView.getInstance().switchToProfile(stage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = AppSessionUtil.getInstance().getUser();
        usernameTextField.setText(user.getName());
        avatarImageView.setImage(new Image(user.getAvatar()));
    }
}
