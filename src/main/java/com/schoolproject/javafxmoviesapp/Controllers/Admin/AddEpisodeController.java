package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddEpisodeController {

    @FXML
    private TextField linkTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    void handleCreateEpisode(MouseEvent event) {
        String name = nameTextField.getText();
        String link = linkTextField.getText();
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Name must not be empty!");
            alert.showAndWait();
            return;
        }
        if (link.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Link must not be empty!");
            alert.showAndWait();
            return;
        }

        // create episode here ...

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
