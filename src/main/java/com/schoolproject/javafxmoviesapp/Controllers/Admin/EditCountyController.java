package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.CountryDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Country;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EditCountyController {

    @FXML
    private TextField nameTextField;
    private Country country = null;

    @FXML
    void handleUpdateCountry(MouseEvent event) {
        String name = nameTextField.getText();
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Name must not be empty!");
            alert.showAndWait();
            return;
        }
        // create new genre here ...
        this.country.setName(nameTextField.getText());
        CountryDAOImpl.getInstance().update(this.country);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void setData(Country country){
        this.country = country;
        nameTextField.setText(country.getName());
    }

}
