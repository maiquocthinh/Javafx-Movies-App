package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.RoleDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Role;
import com.schoolproject.javafxmoviesapp.Utils.AppSessionUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EditRoleController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TilePane permissionsTilePane;

    private Role role;

    @FXML
    void handleSaveRole(MouseEvent event) {
        String name = nameTextField.getText();
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Name must not be empty!");
            alert.showAndWait();
            return;
        }
        List<String> permissions = getPermissionsSelected();

        this.role.setName(name);
        this.role.setPermissions(permissions);
        RoleDAOImpl.getInstance().update(this.role);

        // refresh AppSession if edited role is current role of AppSession
        if (AppSessionUtil.getInstance().getRole().getId() == this.role.getId())
            AppSessionUtil.getInstance().refresh();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private List<String> getPermissionsSelected() {
        List<String> selectedValues = new ArrayList<>();

        ObservableList<Node> nodes = permissionsTilePane.getChildren();
        for (Node node : nodes) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) selectedValues.add(checkBox.getText());
            }
        }

        return selectedValues;
    }

    public void setData(Role role) {
        this.role = role;
        nameTextField.setText(role.getName());
        ObservableList<Node> nodes = permissionsTilePane.getChildren();
        for (Node node : nodes) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                if (role.getPermissions().contains(checkBox.getText()))
                    checkBox.setSelected(true);
            }
        }
    }

}
