package com.schoolproject.javafxmoviesapp.Controllers.Admin;

import com.schoolproject.javafxmoviesapp.Utils.FxmlUtil;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private ScrollPane navbar;
    @FXML
    private NavbarController navbarController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navbarController.getNavDashboard().getStyleClass().add("activeNav");
    }
}
