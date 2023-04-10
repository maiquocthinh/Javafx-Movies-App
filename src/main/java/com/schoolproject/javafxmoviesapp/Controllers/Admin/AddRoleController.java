package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.RoleDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Role;
import com.schoolproject.javafxmoviesapp.Utils.CheckPermissionUtil;
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

public class AddRoleController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TilePane permissionsTilePane;

    private Role role = null;

    public Role getRole() {
        return this.role;
    }


    @FXML
    void handleCreateRole(MouseEvent event) {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        // check permission create role
        if (!CheckPermissionUtil.getInstance().check("Create Role")) {
            alertError.setContentText("You don't have permission to create new role!!!");
            alertError.showAndWait();
            return;
        }

        String name = nameTextField.getText();
        if (name.isEmpty()) {
            alertError.setContentText("Name must not be empty!");
            alertError.showAndWait();
            return;
        }
        List<String> permissions = getPermissionsSelected();

        this.role.setName(name);
        this.role.setPermissions(permissions);
        RoleDAOImpl.getInstance().insert(this.role);

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

}
