package com.schoolproject.javafxmoviesapp.Controllers.Auth;

import com.schoolproject.javafxmoviesapp.Views.AuthView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ResetPassword {

    @FXML
    private PasswordField passwordNew;

    @FXML
    private PasswordField passwordNewAgain;

    @FXML
    void handleConfirm(MouseEvent event) throws IOException {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        String passwordnew = passwordNew.getText().trim();
        String passrordnewagain = passwordNewAgain.getText().trim();

        if (passwordnew.isEmpty()){
            alertError.setContentText("New Password must not be empty!");
            alertError.showAndWait();
            return;
        }
        if (passrordnewagain.isEmpty()){
            alertError.setContentText("New Password Again must not be empty!");
            alertError.showAndWait();
            return;
        }
        if(!passwordnew.isEmpty() && !passrordnewagain.isEmpty()){
            alertInfo.setContentText("Your password was comfirm success");
            alertInfo.showAndWait();
            return;
        }
    }

}
