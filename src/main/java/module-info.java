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
    // Other
    requires json.simple;

    opens com.schoolproject.javafxmoviesapp.Controllers to javafx.fxml;
    opens com.schoolproject.javafxmoviesapp.Controllers.Admin to javafx.fxml;
    exports com.schoolproject.javafxmoviesapp;
}