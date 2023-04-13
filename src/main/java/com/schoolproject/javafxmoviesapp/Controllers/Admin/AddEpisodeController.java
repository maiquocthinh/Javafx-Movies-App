package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.EpidodeDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Episode;
import com.schoolproject.javafxmoviesapp.Utils.CheckPermissionUtil;
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
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        // check permission create episode
        if (!CheckPermissionUtil.getInstance().check("Create Episode")) {
            alertError.setContentText("You don't have permission to create new episode!!!");
            alertError.showAndWait();
            return;
        }

        String name = nameTextField.getText();
        String link = linkTextField.getText();
        if (name.isEmpty()) {
            alertError.setContentText("Name must not be empty!");
            alertError.showAndWait();
            return;
        }
        if (link.isEmpty()) {
            alertError.setContentText("Link must not be empty!");
            alertError.showAndWait();
            return;
        }

        Episode episode = new Episode(name, link, filmId);
        EpidodeDAOImpl.getInstance().insert(episode);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private int filmId;

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

}
