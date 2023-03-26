package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EpisodesController {
    @FXML
    void openDialogCreateEpisode(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/AddEpisode.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        Scene dialogScene = new Scene(anchorPane);
        dialog.setTitle("Create Episode");
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.show();
    }
}
