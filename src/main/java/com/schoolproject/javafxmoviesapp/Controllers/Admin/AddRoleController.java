package com.schoolproject.javafxmoviesapp.Controllers.Admin;

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

    @FXML
    void handleCreateRole(MouseEvent event) {
        String name = nameTextField.getText();
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Name must not be empty!");
            alert.showAndWait();
            return;
        }
        List<String> permission = getPermissionsSelected();
        // create role here...
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private List<String> getPermissionsSelected(){
        List<String> selectedValues = new ArrayList<>();

        ObservableList<Node> nodes = permissionsTilePane.getChildren();
        for (Node node : nodes) {
            if(node instanceof CheckBox){
                CheckBox checkBox = (CheckBox) node;
                if(checkBox.isSelected()) selectedValues.add(checkBox.getText());
            }
        }

        return selectedValues;
    }

}
