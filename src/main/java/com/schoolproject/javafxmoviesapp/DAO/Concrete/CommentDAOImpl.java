package com.schoolproject.javafxmoviesapp.DAO.Concrete;

import com.schoolproject.javafxmoviesapp.DAO.Interface.CommentDAO;
import com.schoolproject.javafxmoviesapp.Entity.Comment;
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

public class CommentDAOImpl implements CommentDAO<Comment> {
    private static CommentDAOImpl commentDAOImpl = null;

    public static CommentDAOImpl getInstance() {
        if (commentDAOImpl != null) return commentDAOImpl;
        commentDAOImpl = new CommentDAOImpl();
        return commentDAOImpl;
    }

   private DateFormat sqlDatetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public int insert(Comment comment) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "INSERT INTO `comments` (`content`, `date`,`userId`, `filmId`) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setString(2, sqlDatetimeFormat.format(comment.getDate()));
            preparedStatement.setInt(3, comment.getUserId());
            preparedStatement.setInt(4, comment.getFilmId());

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
    public int update(Comment comment) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "UPDATE `comments` SET `content`=?, `date`=?, `userId`=?, `filmId`=? WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setString(2, sqlDatetimeFormat.format(comment.getDate()));
            preparedStatement.setInt(3, comment.getUserId());
            preparedStatement.setInt(4, comment.getFilmId());
            preparedStatement.setInt(5, comment.getId());

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
    public int delete(Comment comment) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "DELETE FROM `comments` WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, comment.getId());

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
    public List<Comment> selectAll() {
        List<Comment> results = new ArrayList<>();
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT * FROM `comments`";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Add data to List
            while (res.next()) {
                int id = res.getInt("id");
                String content = res.getString("content");
                Date date = res.getTime("date");
                int userId = res.getInt("userId");
                int filmId = res.getInt("filmId");

                results.add(new Comment(id, content, date, userId, filmId));
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
    public Comment findById(int id) {
        Comment comment = null;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT * FROM `comments` WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Add data to List
            while (res.next()) {
                String content = res.getString("content");
                Date date = res.getTime("date");
                int userId = res.getInt("userId");
                int filmId = res.getInt("filmId");

                comment = new Comment(id, content, date, userId, filmId);
            }

            // Close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return comment;
    }

    @Override
    public List<Comment> selectByCondition(String condition) {
        List<Comment> results = new ArrayList<>();
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT * FROM `comments`" + condition;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Add data to List
            while (res.next()) {
                int id = res.getInt("id");
                String content = res.getString("content");
                Date date = res.getTime("date");
                int userId = res.getInt("userId");
                int filmId = res.getInt("filmId");

                results.add(new Comment(id, content, date, userId, filmId));
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
    public int countAll() {
        int count = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT COUNT(*) FROM `comments`";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();
            while(res.next()) count = res.getInt(1);

            // close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public int countByCondition(String condition) {
        int count = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT COUNT(*) FROM `comments` " + condition;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();
            while(res.next()) count = res.getInt(1);

            // close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

}
