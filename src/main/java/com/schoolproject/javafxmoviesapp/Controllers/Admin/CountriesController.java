package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CountriesController implements Initializable {
    @FXML
    private ScrollPane navbar;
    @FXML
    private NavbarController navbarController;

    @FXML
    private Button createButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navbarController.getNavCountries().getStyleClass().add("activeNav");
    }

    @FXML
    void openDialogCreateCountry(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Admin/AddCountry.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        Scene dialogScene = new Scene(anchorPane);
        dialog.setTitle("Create Country");
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.show();
    }
}
