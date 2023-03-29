package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.Entity.Role;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Role> roles = FXCollections.observableArrayList();
        roles.add(new Role(0,"Admin", new ArrayList<String>()));
        roles.add(new Role(1,"Mod1", new ArrayList<String>()));
        roles.add(new Role(2,"Mod2", new ArrayList<String>()));
        roles.add(new Role(3,"Member", new ArrayList<String>()));
        roleChoiceBox.setValue(roles.get(2));
        roleChoiceBox.setItems(roles);


    }

    @FXML
    void handleCreateUser(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        String avatar = avatarTextField.getText();
        String password = passwordTextField.getText();
        int role = roleChoiceBox.getValue().getId();

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
        if (password.isEmpty()) {
            alert.setContentText("Password must not be empty!");
            alert.showAndWait();
            return;
        }

        // create user here  ...
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();

    }

}
