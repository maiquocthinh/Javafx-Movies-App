package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.RoleDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.UserDAOImpl;
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
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        // get values from ui
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        String avatar = avatarTextField.getText();
        String newPassword = newPasswordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();

        // validate user info
        {
            if (name.isEmpty()) {
                alertError.setContentText("Name must not be empty!");
                alertError.showAndWait();
                return;
            }
            if (email.isEmpty()) {
                alertError.setContentText("Email must not be empty!");
                alertError.showAndWait();
                return;
            }
            if (!ValidateUtil.isEmail(email)) {
                alertError.setContentText("Email is invalid!");
                alertError.showAndWait();
                return;
            }
            if (avatar.isEmpty()) {
                alertError.setContentText("Avatar must not be empty!");
                alertError.showAndWait();
                return;
            }
            if (!ValidateUtil.isURL(avatar)) {
                alertError.setContentText("Avatar must be url!");
                alertError.showAndWait();
                return;
            }
        }
        // if update new password
        if (!newPassword.isEmpty()) {
            // validate new password & confirm password
            {
                if (confirmPassword.isEmpty()) {
                    alertError.setContentText("Confirm Password must not be empty!");
                    alertError.showAndWait();
                    return;
                }
                if (!newPassword.equals(confirmPassword)) {
                    alertError.setContentText("Confirm Password not match with New Password!");
                    alertError.showAndWait();
                    return;
                }
            }
            user.setPassword(newPassword);
        }

        user.setName(name);
        user.setEmail(email);
        user.setAvatar(avatar);
        UserDAOImpl.getInstance().update(user);

        // check permission can update role
        {
            Role role = roleChoiceBox.getValue();
            AppSessionUtil.getInstance().setRole(role);
            user.setRoleId(role.getId());
            UserDAOImpl.getInstance().updateRole(user);
        }
        // show alert update profile success
        alertInfo.setContentText("Profile Update Success!");
        alertInfo.showAndWait();
    }
}
