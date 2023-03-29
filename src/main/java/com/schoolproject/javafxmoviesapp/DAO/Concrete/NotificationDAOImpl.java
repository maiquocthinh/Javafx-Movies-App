package com.schoolproject.javafxmoviesapp.DAO.Concrete;

import com.schoolproject.javafxmoviesapp.DAO.Interface.NotificationDAO;
import com.schoolproject.javafxmoviesapp.Entity.Notification;
import com.schoolproject.javafxmoviesapp.Utils.JDBCUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationDAOImpl implements NotificationDAO<Notification> {
    private static NotificationDAOImpl notificationDAOImpl = null;

    public static NotificationDAOImpl getInstance() {
        if (notificationDAOImpl != null) return notificationDAOImpl;
        notificationDAOImpl = new NotificationDAOImpl();
        return notificationDAOImpl;
    }

    private DateFormat sqlDatetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public int insert(Notification notification) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "INSERT INTO `notifications` (`title`, `content`, `date`, `read`,`userId`, `filmId`) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, notification.getTitle());
            preparedStatement.setString(2, notification.getContent());
            preparedStatement.setString(3, sqlDatetimeFormat.format(notification.getDate()));
            preparedStatement.setBoolean(4, notification.isRead());
            preparedStatement.setInt(5, notification.getUserId());
            preparedStatement.setInt(6, notification.getFilmId());

            // Execute SQL
            res = preparedStatement.executeUpdate();

            // Close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public int update(Notification notification) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "UPDATE `comments` SET  `title`=?, `content`=?, `date`=?, `read`=?, `userId`=?, `filmId`=? WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, notification.getTitle());
            preparedStatement.setString(2, notification.getContent());
            preparedStatement.setString(3, sqlDatetimeFormat.format(notification.getDate()));
            preparedStatement.setBoolean(4, notification.isRead());
            preparedStatement.setInt(5, notification.getUserId());
            preparedStatement.setInt(6, notification.getFilmId());
            preparedStatement.setInt(7, notification.getId());

            // Execute SQL
            res = preparedStatement.executeUpdate();

            // Close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public int delete(Notification notification) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "DELETE FROM `notifications` WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, notification.getId());

            // Execute SQL
            res = preparedStatement.executeUpdate();

            // Close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public List<Notification> selectAll() {
        List<Notification> results = new ArrayList<>();
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT * FROM `notifications`";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Add data to List
            while (res.next()) {
                int id = res.getInt("id");
                String title = res.getString("title");
                String content = res.getString("content");
                Date date = res.getTime("date");
                Boolean read = res.getBoolean("read");
                int userId = res.getInt("userId");
                int filmId = res.getInt("filmId");

                results.add(new Notification(id, title, content, read, date, userId, filmId));
            }

            // Close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    @Override
    public Notification findById(int id) {
        Notification notification = null;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT * FROM `notifications` WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Add data to List
            while (res.next()) {
                String title = res.getString("title");
                String content = res.getString("content");
                Date date = res.getTime("date");
                Boolean read = res.getBoolean("read");
                int userId = res.getInt("userId");
                int filmId = res.getInt("filmId");

                notification = new Notification(id, title, content, read, date, userId, filmId);
            }

            // Close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return notification;
    }

    @Override
    public List<Notification> selectByCondition(String condition) {
        List<Notification> results = new ArrayList<>();
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT * FROM `notifications`" + condition;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Add data to List
            while (res.next()) {
                int id = res.getInt("id");
                String title = res.getString("title");
                String content = res.getString("content");
                Date date = res.getTime("date");
                Boolean read = res.getBoolean("read");
                int userId = res.getInt("userId");
                int filmId = res.getInt("filmId");

                results.add(new Notification(id, title, content, read, date, userId, filmId));
            }

            // Close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return results;
    }
}
