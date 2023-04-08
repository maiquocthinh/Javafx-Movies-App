package com.schoolproject.javafxmoviesapp.Controllers.Auth;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class HelloWorldController {

    @FXML
    private Button button;

    @FXML
    private Label label;

    @FXML
    void handleClick(MouseEvent event) {
        label.setText("Hello World");
        label.setVisible(true);
    }

}
