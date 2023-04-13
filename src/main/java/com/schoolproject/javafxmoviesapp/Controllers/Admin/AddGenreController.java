package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.GenreDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import com.schoolproject.javafxmoviesapp.Utils.CheckPermissionUtil;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddGenreController {

    @FXML
    private TextField nameTextField;

    @FXML
    void handleCreateGenre(MouseEvent event) {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        // check permission create genre
        if (!CheckPermissionUtil.getInstance().check("Create Genre")) {
            alertError.setContentText("You don't have permission to create new genre!!!");
            alertError.showAndWait();
            return;
        }

        String name = nameTextField.getText();
        if (name.isEmpty()) {
            alertError.setContentText("Name must not be empty!");
            alertError.showAndWait();
            return;
        }

        genre = new Genre(nameTextField.getText());
        GenreDAOImpl.getInstance().insert(genre);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private Genre genre = null;

    public Genre getGenre() {
        return genre;
    }
}
