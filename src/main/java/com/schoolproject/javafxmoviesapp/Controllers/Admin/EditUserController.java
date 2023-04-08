package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.RoleDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.UserDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Role;
import com.schoolproject.javafxmoviesapp.Entity.User;
import com.schoolproject.javafxmoviesapp.Utils.AppSessionUtil;
import com.schoolproject.javafxmoviesapp.Utils.CheckPermissionUtil;
import com.schoolproject.javafxmoviesapp.Utils.ValidateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditUserController implements Initializable {

    @FXML
    private TextField avatarTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private ChoiceBox<Role> roleChoiceBox;

    private User user = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Role> roles = FXCollections.observableArrayList();
        roles.addAll(RoleDAOImpl.getInstance().selectAll());
        roleChoiceBox.setItems(roles);

        if (!CheckPermissionUtil.getInstance().check("Set Role"))
            roleChoiceBox.setDisable(true);
    }

    @FXML
    void handleSaveUser(MouseEvent event) {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        String avatar = avatarTextField.getText();
        String password = passwordTextField.getText();
        int roleId = roleChoiceBox.getValue() != null ? roleChoiceBox.getValue().getId() : 0;

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

        this.user.setName(name);
        this.user.setEmail(email);
        this.user.setAvatar(avatar);
        this.user.setPassword(password);
        UserDAOImpl.getInstance().update(this.user);

        // check permission set role for user
        if (!CheckPermissionUtil.getInstance().check("Set Role")) {
            alertError.setContentText("You don't have permission to set role!!!");
            alertError.showAndWait();
            return;
        } else {
            this.user.setRoleId(roleId);
            UserDAOImpl.getInstance().updateRole(this.user);
        }

        // refresh AppSession if edited user is current user of AppSession
        if (AppSessionUtil.getInstance().getUser().getId() == this.user.getId())
            AppSessionUtil.getInstance().refresh();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    public void setData(User user) {
        this.user = user;
        nameTextField.setText(user.getName());
        emailTextField.setText(user.getEmail());
        avatarTextField.setText(user.getAvatar());
        Role role = RoleDAOImpl.getInstance().findById(user.getRoleId());
        List<Role> roles = roleChoiceBox.getItems();
        for (Role _role : roles)
            if (_role.getId() == role.getId()) {
                roleChoiceBox.setValue(_role);
                break;
            }

    }

}
