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
        int filmId = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "INSERT INTO `films` (`name`, `poster`, `backdrop`, `trailer`, `release`, `type`, `status`, `runtime`, `quality`, `content`, `popular`) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
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
            preparedStatement.setBoolean(11, film.isPopular());

            // Execute SQL
            preparedStatement.executeUpdate();
            ResultSet res = connection.createStatement().executeQuery("SELECT LAST_INSERT_ID() AS lastIndex;");
            if (res.next()) filmId = res.getInt("lastIndex");

            // Close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filmId;
    }

    @Override
    public int update(Film film) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "UPDATE `films` SET `name`=?, `poster`=?, `backdrop`=?, `trailer`=?, `release`=?, `type`=?, `status`=?, `runtime`=?, `quality`=?, `content`=?, `popular`=? WHERE `id`=?";
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
            preparedStatement.setBoolean(11, film.isPopular());
            preparedStatement.setInt(12, film.getId());

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
            String sql = """
                DELETE FROM `film_genre` WHERE `filmId`=?;
                DELETE FROM `film_country` WHERE `filmId`=?;
                DELETE FROM `films` WHERE `id`=?;""";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, film.getId());
            preparedStatement.setInt(2, film.getId());
            preparedStatement.setInt(3, film.getId());

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
                boolean isPopular = res.getBoolean("popular");

                results.add(new Film(id, name, poster, backdrop, trailer, content, release, type, status, runtime, quality, rating, viewed, isPopular));
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
                boolean isPopular = res.getBoolean("popular");


                film = new Film(id, name, poster, backdrop, trailer, content, release, type, status, runtime, quality, rating, viewed, isPopular);
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
                boolean isPopular = res.getBoolean("popular");

                results.add(new Film(id, name, poster, backdrop, trailer, content, release, type, status, runtime, quality, rating, viewed, isPopular));
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
            String sql = "SELECT COUNT(*) FROM `films`";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) count = res.getInt(1);

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
            String sql = "SELECT COUNT(*) FROM `films` " + condition;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) count = res.getInt(1);

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
