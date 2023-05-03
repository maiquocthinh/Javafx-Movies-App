package com.schoolproject.javafxmoviesapp.Utils;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.NotificationDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Entity.Genre;
import com.schoolproject.javafxmoviesapp.Entity.Notification;
import com.schoolproject.javafxmoviesapp.Entity.User;
import com.schoolproject.javafxmoviesapp.Services.GmailService;
import javafx.scene.control.Alert;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SQLQueryUtil {
    public static void setGenreAndCountryForFilm(int filmId, List<Genre> genres, List<Country> countries) throws SQLException, IOException {
        StringBuffer genresSB = new StringBuffer();
        for (Genre genre : genres) genresSB.append(genre.getId() + ",");
        genresSB.deleteCharAt(genresSB.length() - 1);

        StringBuffer countriesSB = new StringBuffer();
        for (Country country : countries) countriesSB.append(country.getId() + ",");
        countriesSB.deleteCharAt(countriesSB.length() - 1);

        String sql = "SET @film_id := ?;"
                + "DELETE FROM `film_genre` WHERE `filmId`=@film_id; "
                + "DELETE FROM `film_country` WHERE `filmId`=@film_id; "
                + "INSERT INTO `film_genre` (`filmId`, `genreId`) SELECT @film_id, `id` FROM `genres` WHERE `id` IN (" + genresSB.toString() + ");"
                + "INSERT INTO `film_country` (`filmId`, `countryId`) SELECT @film_id, `id` FROM `countries` WHERE `id` IN (" + countriesSB.toString() + ");";
        Connection connection = JDBCUtil.getConnecttion();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, filmId);

        preparedStatement.executeUpdate();

        connection.close();
    }

    public static void insertAndSendNotify(int filmId, String title, String content) throws SQLException, IOException, MessagingException, GeneralSecurityException {
        Connection connection = JDBCUtil.getConnecttion();
        String sql;
        ResultSet res;

        // get user followed film
        sql = "SELECT `id`, `email`  FROM `follows` INNER JOIN `users` ON  follows.userId = users.id  WHERE filmId = " + filmId;
        res = connection.createStatement().executeQuery(sql);
        List<User> usersFollowed = new ArrayList<>();
        while (res.next()) {
            User user = new User();
            user.setId(res.getInt("id"));
            user.setEmail(res.getString("email"));
            usersFollowed.add(user);
        }

        if (usersFollowed.size() == 0) {
            // show alert send email success
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setContentText("There are no users following this film");
            alertError.showAndWait();
            return;
        }

        // insert notifications
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setContent(content);
        notification.setFilmId(filmId);
        NotificationDAOImpl.getInstance().insert(notification);

        // get the id just inserted
        res = connection.createStatement().executeQuery("SELECT MAX(`id`) AS `maxId` FROM `notifications`");
        int notificationId = 0;
        if (res.next()) notificationId = res.getInt("maxId");

        // insert user_notification
        StringBuffer sqlSB = new StringBuffer("INSERT INTO `user_notification` (`userId`, `notificationId`) VALUES ");
        for (User user : usersFollowed) {
            sqlSB.append("(" + user.getId() + ", " + notificationId + "), ");
        }
        sqlSB.replace(sqlSB.length() - 2, sqlSB.length() - 1, ";");
        connection.createStatement().executeUpdate(sqlSB.toString());

        // send email to user
        List<String> emails = new ArrayList<>();
        for (User user : usersFollowed) emails.add(user.getEmail());
        GmailService.sendManyMessage(emails, title, content);

        // show alert send email success
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        alertInfo.setContentText("Send Email Success");
        alertInfo.showAndWait();
    }

    public static void removeFromFollowed(int filmId) throws SQLException, IOException {
        Connection connection = JDBCUtil.getConnecttion();

        String sql = "DELETE FROM `follows` WHERE `filmId`=? AND `userId`=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, filmId);
        preparedStatement.setInt(2, AppSessionUtil.getInstance().getUser().getId());

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }
}
