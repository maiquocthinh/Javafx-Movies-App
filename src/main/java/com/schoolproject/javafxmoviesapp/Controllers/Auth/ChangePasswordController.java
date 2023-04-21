package com.schoolproject.javafxmoviesapp.Controllers.Auth;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.UserDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.User;
import com.schoolproject.javafxmoviesapp.Utils.ValidateUtil;
import com.schoolproject.javafxmoviesapp.Views.AuthView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePasswordController {

    @FXML
    private PasswordField confirmPasswordTextField;

    @FXML
    private PasswordField passwordTextField;

    private String email = "";

    public void setEmail(String email) {
        this.email = email;
    }

    @FXML
    void switchToChangePassword(MouseEvent event) throws IOException {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        String password = passwordTextField.getText().trim();
        String confirmPassword = confirmPasswordTextField.getText().trim();

        // validate
        {
            if (password.isEmpty()) {
                alertError.setContentText("New Password must not be empty!");
                alertError.showAndWait();
                return;
            }
            if (confirmPassword.isEmpty()) {
                alertError.setContentText("New Password Again must not be empty!");
                alertError.showAndWait();
                return;
            }
            if (!password.equals(confirmPassword)) {
                alertError.setContentText("Password and confirm password must be the same");
                alertError.showAndWait();
                return;
            }
        }

        // do change password
        if (email.isEmpty() && !ValidateUtil.isEmail(email)) return;
        User user = UserDAOImpl.getInstance().findByEmail(email);
        user.setPassword(password);
        UserDAOImpl.getInstance().update(user);

        // show alert change password success
        alertInfo.setContentText("Change password success!");
        alertInfo.showAndWait();
        return;
    }

    @FXML
    void backToLogin(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AuthView.getInstance().backToLogin(stage);
    }
}
