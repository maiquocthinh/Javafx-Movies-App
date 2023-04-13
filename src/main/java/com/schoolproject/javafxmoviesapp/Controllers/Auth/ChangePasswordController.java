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

public class ResetPassword {

    @FXML
    private PasswordField passwordNew;

    @FXML
    private PasswordField passwordNewAgain;

    private  String email="";

    public void setEmail(String email) {
        this.email = email;
    }

    @FXML
    void handleConfirm(MouseEvent event) throws IOException {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        String passwordnew = passwordNew.getText().trim();
        String passrordnewagain = passwordNewAgain.getText().trim();

        {
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
        if(passwordnew.equals(passrordnewagain) == false){
            alertError.setContentText("Password and confirm password must be the same");
            alertError.showAndWait();
            return;
        }
        }

        // do change password
        if (email.isEmpty() && !ValidateUtil.isEmail(email)) return;
        User user = UserDAOImpl.getInstance().findByEmail(email);
        user.setPassword(passwordnew);
        UserDAOImpl.getInstance().update(user);

        // show alert change password success
        alertInfo.setContentText("Change password success!");
        alertInfo.showAndWait();
        return;
    }
    @FXML
    void backToInputOTP(MouseEvent event) throws IOException{
        Stage  stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        AuthView.getInstance().backToInputOTP(stage);
    }
}
