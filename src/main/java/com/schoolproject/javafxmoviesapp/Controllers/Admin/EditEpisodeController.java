package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.EpidodeDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Episode;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EditEpisodeController {

    @FXML
    private TextField linkTextField;

    @FXML
    private TextField nameTextField;

    private Episode episode = null;

    @FXML
    void handleSaveEpisode(MouseEvent event) {
        String name = nameTextField.getText();
        String link = linkTextField.getText();

        // validate
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

        this.episode.setName(nameTextField.getText());
        this.episode.setLink(linkTextField.getText());
        EpidodeDAOImpl.getInstance().update(this.episode);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setData(Episode episode) {
        this.episode = episode;
        nameTextField.setText(episode.getName());
        linkTextField.setText(episode.getLink());
    }
}
