package com.schoolproject.javafxmoviesapp;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.RoleDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.UserDAOImpl;
import com.schoolproject.javafxmoviesapp.Utils.AppSessionUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        // cái này là giải đăng nhập thôi
        // cụ thể là nó ấy user & role của user đó trong db ra luôn
        // rồi lưu vô AppSessionUtil để sử dụng trong toàn ứng dụng luôn
        {
            AppSessionUtil.getInstance().setUser(UserDAOImpl.getInstance().findById(1));
            AppSessionUtil.getInstance().setRole(RoleDAOImpl.getInstance().findByUser(AppSessionUtil.getInstance().getUser()));
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/Home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello Word");
        primaryStage.getIcons().add(new Image(getClass().getResource("/Images/app-icon.png").openStream()));
        primaryStage.show();
    }
}
