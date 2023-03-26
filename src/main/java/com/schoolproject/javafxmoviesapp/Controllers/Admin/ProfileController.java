package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.Entity.Role;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Role> roles = FXCollections.observableArrayList();
        roles.add(new Role("Admin", 0));
        roles.add(new Role("Mod1", 1));
        roles.add(new Role("Mod2", 2));
        roles.add(new Role("Member", 3));
        roleChoiceBox.setValue(roles.get(0));
        roleChoiceBox.setItems(roles);
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
