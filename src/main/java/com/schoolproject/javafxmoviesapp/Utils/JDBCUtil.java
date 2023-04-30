package com.schoolproject.javafxmoviesapp.Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {
    public static Connection getConnecttion() throws IOException, SQLException {
        Properties prop = new Properties();
        prop.load(new FileInputStream(JDBCUtil.class.getResource("/Properties/database.properties").getPath()));

        String url = "jdbc:mySQL://" + prop.getProperty("DB_HOST") + ":" + prop.getProperty("DB_PORT") + "/" + prop.getProperty("DB_NAME") +"?allowMultiQueries=true";
        String username = prop.getProperty("DB_USER");
        String password = prop.getProperty("DB_PASS");

        return DriverManager.getConnection(url, username, password);
    }

    public static void printInfo(Connection connection) throws SQLException {
        if (connection != null) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            System.out.println(databaseMetaData.getDatabaseProductName());
            System.out.println(databaseMetaData.getDatabaseProductVersion());
        }
    }
//    public static void changeScene(ActionEvent event, String fxmlFile, String title){
//        Parent root = null;
//        if( fxmlFile != null && title != null){
//            try {
//                FXMLLoader loader = new FXMLLoader(JDBCUtil.class.getResource(fxmlFile));
//                root = loader.load();
//                    TvseriesController tvseriesController = loader.getController();
//                tvseriesController.setUserInformation;
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }else {
//            try{
//                root = FXMLLoader.load(DButils.class.getResource(fxmlFile));
//            }catch(IOException e){
//                e.printStackTrace();
//
//            }
//        }
//        Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setTitle(title);
//        stage.setScene(new Scene(root ,600,400 ));
//        stage.show();
//    }
}

