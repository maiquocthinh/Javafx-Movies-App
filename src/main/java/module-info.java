module com.schoolproject.javafxmoviesapp {
    // Javafx
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    // Ikonli
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.javafx;
    // MySQL
    requires java.sql;
    // Mail
    requires javax.mail;
    // Google
    requires com.google.api.client;
    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.json.gson;
    requires com.google.api.services.gmail;
    requires google.api.client;
    requires com.google.api.client.json.jackson2;
    requires org.apache.commons.codec;
    // Other
    requires json.simple;
    requires jdk.httpserver;

    opens com.schoolproject.javafxmoviesapp.Controllers to javafx.fxml;
    opens com.schoolproject.javafxmoviesapp.Controllers.Admin to javafx.fxml;
    opens com.schoolproject.javafxmoviesapp.Controllers.Auth to javafx.fxml;
    exports com.schoolproject.javafxmoviesapp;
    exports com.schoolproject.javafxmoviesapp.Entity;
}