package com.schoolproject.javafxmoviesapp.Controllers.Auth;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.RoleDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.UserDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.User;
import com.schoolproject.javafxmoviesapp.Utils.AppSessionUtil;
import com.schoolproject.javafxmoviesapp.Utils.ValidateUtil;
import com.schoolproject.javafxmoviesapp.Views.AuthView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextFiled;

    @FXML
    void handleLogin(MouseEvent event) throws IOException {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        // get values from ui
        String email = emailTextField.getText().trim();
        String password = passwordTextFiled.getText().trim();

        // validate
        {
            if (email.isEmpty()) {
                alertError.setContentText("Email must not be empty!");
                alertError.showAndWait();
                return;
            }
            if (!ValidateUtil.isEmail(email)) {
                alertError.setContentText("Email is invalid!");
                alertError.showAndWait();
                return;
            }
            if (password.isEmpty()) {
                alertError.setContentText("Password must not be empty!");
                alertError.showAndWait();
                return;
            }
        }

        // check login
        User user = UserDAOImpl.getInstance().findByEmail(email);
        if (user == null) {
            alertError.setContentText("Email or Password incorrect!");
            alertError.showAndWait();
            return;
        }
        if (!DigestUtils.sha256Hex(password).equals(user.getPassword())) {
            alertError.setContentText("Email or Password incorrect");
            alertError.showAndWait();
            return;
        }
        // write info of session
        AppSessionUtil.getInstance().setUser(user);
        AppSessionUtil.getInstance().setRole(RoleDAOImpl.getInstance().findByUser(user));

        // show success
        alertInfo = new Alert(Alert.AlertType.INFORMATION);
        alertInfo.setContentText("Login Success!");
        alertInfo.showAndWait();
    }

    @FXML
    void switchToForgotPassword(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AuthView.getInstance().switchToForgotPassword(stage);
    }

    @FXML
    void switchToSignup(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AuthView.getInstance().switchToSignup(stage);
    }
}
