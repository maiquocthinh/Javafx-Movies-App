package com.schoolproject.javafxmoviesapp.DAO.Concrete;

import com.schoolproject.javafxmoviesapp.DAO.Interface.FilmDAO;
import com.schoolproject.javafxmoviesapp.Entity.Film;
import com.schoolproject.javafxmoviesapp.Utils.JDBCUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAOImpl implements FilmDAO<Film> {
    private static FilmDAOImpl filmDAOImpl = null;

    public static FilmDAOImpl getInstance() {
        if (filmDAOImpl != null) return filmDAOImpl;
        filmDAOImpl = new FilmDAOImpl();
        return filmDAOImpl;
    }

    @Override
    public int insert(Film film) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "INSERT INTO `films` (`name`, `poster`, `backdrop`, `trailer`, `release`, `type`, `status`, `runtime`, `quality`, `content`) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, film.getName());
            preparedStatement.setString(2, film.getPoster());
            preparedStatement.setString(3, film.getBackdrop());
            preparedStatement.setString(4, film.getTrailer());
            preparedStatement.setInt(5, film.getRelease());
            preparedStatement.setString(6, film.getType());
            preparedStatement.setString(7, film.getStatus());
            preparedStatement.setString(8, film.getRuntime());
            preparedStatement.setString(9, film.getQuality());
            preparedStatement.setString(10, film.getContent());

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
    public int update(Film film) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "UPDATE `films` SET `name`=?, `poster`=?, `backdrop`=?, `trailer`=?, `release`=?, `type`=?, `status`=?, `runtime`=?, `quality`=?, `content`=? WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, film.getName());
            preparedStatement.setString(2, film.getPoster());
            preparedStatement.setString(3, film.getBackdrop());
            preparedStatement.setString(4, film.getTrailer());
            preparedStatement.setInt(5, film.getRelease());
            preparedStatement.setString(6, film.getType());
            preparedStatement.setString(7, film.getStatus());
            preparedStatement.setString(8, film.getRuntime());
            preparedStatement.setString(9, film.getQuality());
            preparedStatement.setString(10, film.getContent());
            preparedStatement.setInt(11, film.getId());

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
    public int updateView(Film film) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "UPDATE `films` SET `viewed`=?  WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, film.getViewed());
            preparedStatement.setInt(2, film.getId());

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
    public int delete(Film film) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "DELETE FROM `films` WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, film.getId());

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
    public List<Film> selectAll() {
        List<Film> results = new ArrayList<>();
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT * FROM `films`";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Add data to List
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                String poster = res.getString("poster");
                String backdrop = res.getString("backdrop");
                String trailer = res.getString("trailer");
                String content = res.getString("content");
                int release = res.getInt("release");
                String type = res.getString("type");
                String status = res.getString("status");
                String runtime = res.getString("runtime");
                String quality = res.getString("quality");
                float rating = res.getFloat("rating");
                int viewed = res.getInt("viewed");

                results.add(new Film(id, name, poster, backdrop, trailer, content, release, type, status, runtime, quality, rating, viewed));
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
    public Film findById(int id) {
        Film film = null;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT * FROM `films` WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Add data to List
            while (res.next()) {
                String name = res.getString("name");
                String poster = res.getString("poster");
                String backdrop = res.getString("backdrop");
                String trailer = res.getString("trailer");
                String content = res.getString("content");
                int release = res.getInt("release");
                String type = res.getString("type");
                String status = res.getString("status");
                String runtime = res.getString("runtime");
                String quality = res.getString("quality");
                float rating = res.getFloat("rating");
                int viewed = res.getInt("viewed");

                film = new Film(id, name, poster, backdrop, trailer, content, release, type, status, runtime, quality, rating, viewed);
            }

            // Close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return film;
    }

    @Override
    public List<Film> selectByCondition(String condition) {
        List<Film> results = new ArrayList<>();
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT * FROM `films`" + condition;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Add data to List
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                String poster = res.getString("poster");
                String backdrop = res.getString("backdrop");
                String trailer = res.getString("trailer");
                String content = res.getString("content");
                int release = res.getInt("release");
                String type = res.getString("type");
                String status = res.getString("status");
                String runtime = res.getString("runtime");
                String quality = res.getString("quality");
                float rating = res.getFloat("rating");
                int viewed = res.getInt("viewed");

                results.add(new Film(id, name, poster, backdrop, trailer, content, release, type, status, runtime, quality, rating, viewed));
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
    public int count() {
        int count = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT COUNT(*) FROM `films`";
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
