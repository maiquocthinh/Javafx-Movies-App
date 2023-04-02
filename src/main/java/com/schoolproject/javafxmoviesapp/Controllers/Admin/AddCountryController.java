package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.CountryDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Country;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddCountryController {

    @FXML
    private TextField nameTextField;

    @FXML
    void handleCreateCountry(MouseEvent event) {
        String name = nameTextField.getText();
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Name must not be empty!");
            alert.showAndWait();
            return;
        }

        country = new Country(nameTextField.getText());
        CountryDAOImpl.getInstance().insert(country);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private Country country = null;

    public Country getCountry() {
        return country;
    }
}
