package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.GenreDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HeadController implements Initializable {

    @FXML
    private TextField searchTextField;
    @FXML
    private ListView<String> listView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextField searchTextField = new TextField();
        ListView<String> listView = new ListView<>();
        ObservableList<String> data = FXCollections.observableArrayList("apple", "banana", "cherry", "date", "elderberry");
        listView.setItems(data);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Filter the data based on the search text
            ObservableList<String> filteredData = data.filtered(s -> s.toLowerCase().contains(newValue.toLowerCase()));

            // Update the ListView with the filtered data
            listView.setItems(filteredData);
        });
    }
}
