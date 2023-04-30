package com.schoolproject.javafxmoviesapp.Controllers.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DialogBoxController {
    // report
    @FXML
    private Button repoerSuccess, repoerFailed;
    @FXML
    private TextArea textReport;

    public void initialize(URL agr0, ResourceBundle arg1) {
    }

    void handleWarringEmtyText(Stage stage) {
        if (textReport.getText().isEmpty()) {
            // Nếu không có thông tin được nhập, hiển thị hộp thoại cảnh báo
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText("Bạn chưa nhập thông tin báo cáo");
            alert.setContentText("bạn chắc chăn muốn đóng cửa sổ");
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, closeButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                alert.close();
                stage.close();
            } else {
                alert.close();
            }
        } else {
            stage.close();
        }
    }

    public void handlerReportSuccess(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        handleWarringEmtyText(stage);
    }

    public void handlerReportFailed(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void handlenextEp(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
