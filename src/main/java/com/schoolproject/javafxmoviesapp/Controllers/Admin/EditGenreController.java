package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.GenreDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EditGenreController {

    @FXML
    private TextField nameTextField;
    private Genre genre = null;

    @FXML
    void handleUpdateGenre(MouseEvent event) {
        String name = nameTextField.getText();
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Name must not be empty!");
            alert.showAndWait();
            return;
        }
        // create new genre here ...
        this.genre.setName(nameTextField.getText());
        GenreDAOImpl.getInstance().update(this.genre);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void setData(Genre genre){
        this.genre = genre;
        nameTextField.setText(genre.getName());
    }

}
