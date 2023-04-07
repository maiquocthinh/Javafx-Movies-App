package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.RoleDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Role;
import com.schoolproject.javafxmoviesapp.Entity.User;
import com.schoolproject.javafxmoviesapp.Utils.AppSessionUtil;
import com.schoolproject.javafxmoviesapp.Utils.ValidateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private TextField avatarTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField newPasswordTextField;

    @FXML
    private ChoiceBox<Role> roleChoiceBox;

    private User user = null;
    private Role role = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Role> roles = FXCollections.observableArrayList();
        roles.addAll(RoleDAOImpl.getInstance().selectAll());
        roleChoiceBox.setItems(roles);

        user = AppSessionUtil.getInstance().getUser();
        role = AppSessionUtil.getInstance().getRole();

        nameTextField.setText(user.getName());
        emailTextField.setText(user.getEmail());
        avatarTextField.setText(user.getAvatar());
        for (Role role : roles)
            if (this.role.getId() == role.getId()) {
                roleChoiceBox.setValue(role);
                break;
            }
    }

    @FXML
    void handleSaveProfile(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        // get values
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        String avatar = avatarTextField.getText();
        Role role = roleChoiceBox.getValue();
        String newPassword = newPasswordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();

        // validate
        if (name.isEmpty()) {
            alert.setContentText("Name must not be empty!");
            alert.showAndWait();
            return;
        }
        if (email.isEmpty()) {
            alert.setContentText("Email must not be empty!");
            alert.showAndWait();
            return;
        }
        if (!ValidateUtil.isEmail(email)) {
            alert.setContentText("Email is invalid!");
            alert.showAndWait();
            return;
        }
        if (avatar.isEmpty()) {
            alert.setContentText("Avatar must not be empty!");
            alert.showAndWait();
            return;
        }
        if (!ValidateUtil.isURL(avatar)) {
            alert.setContentText("Avatar must be url!");
            alert.showAndWait();
            return;
        }
        if (newPassword.isEmpty()) {
            alert.setContentText("New Password must not be empty!");
            alert.showAndWait();
            return;
        }
        if (confirmPassword.isEmpty()) {
            alert.setContentText("Confirm Password must not be empty!");
            alert.showAndWait();
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            alert.setContentText("Confirm Password not match with New Password!");
            alert.showAndWait();
            return;
        }

        // update profile here ....


    }
}
