package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.CountryDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Utils.CheckPermissionUtil;
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
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        // check permission create country
        if (!CheckPermissionUtil.getInstance().check("Create Country")) {
            alertError.setContentText("You don't have permission to create new country!!!");
            alertError.showAndWait();
            return;
        }
        String name = nameTextField.getText();
        if (name.isEmpty()) {
            alertError.setContentText("Name must not be empty!");
            alertError.showAndWait();
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
