package com.schoolproject.javafxmoviesapp;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.RoleDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.UserDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Role;
import com.schoolproject.javafxmoviesapp.Entity.User;
import com.schoolproject.javafxmoviesapp.Utils.AppSessionUtil;
import com.schoolproject.javafxmoviesapp.Views.AdminView;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        {
            // emulator user & role info login to admin
            // later can be replaced by login action
            User user = UserDAOImpl.getInstance().findById(1);
            Role role = RoleDAOImpl.getInstance().findByUser(user);
            AppSessionUtil.getInstance().setUser(user);
            AppSessionUtil.getInstance().setRole(role);
        }

        primaryStage.getIcons().add(new Image(getClass().getResource("/Images/app-icon.png").openStream()));
        AdminView.getInstance().switchToDashboard(primaryStage);
    }
}
