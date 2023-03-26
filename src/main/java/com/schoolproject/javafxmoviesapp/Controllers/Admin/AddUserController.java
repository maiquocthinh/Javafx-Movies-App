package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.Entity.Role;
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
import java.util.List;
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
        roles.add(new Role("Admin", 0));
        roles.add(new Role("Mod1", 1));
        roles.add(new Role("Mod2", 2));
        roles.add(new Role("Member", 3));
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
        if (avatar.isEmpty()) {
            alert.setContentText("Avatar must not be empty!");
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
