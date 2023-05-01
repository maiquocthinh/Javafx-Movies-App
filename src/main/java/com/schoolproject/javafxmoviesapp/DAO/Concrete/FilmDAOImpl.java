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
            res = preparedStatement.executeUpdate();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT LAST_INSERT_ID();");
            if (resultSet.next()) film.setId(resultSet.getInt(1));

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
            String sql = "SET @film_id := ?;" +
                    "UPDATE `films` SET `viewed`= `viewed` + 1  WHERE `id` = @film_id;" +
                    "UPDATE `view_log` SET `viewed` = `viewed` + 1 WHERE `filmId` = @film_id AND `date` = CURRENT_DATE;" +
                    "INSERT INTO `view_log` (`filmId`, `viewed`, `date`) SELECT @film_id, 1, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM `view_log` WHERE `filmId` = @film_id AND `date` = CURRENT_DATE);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, film.getId());

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
    public int getRating(Film film) {
        int rating = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT `rating` FROM `films` WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, film.getId());

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) rating = res.getInt(1);

            // close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rating;
    }

    @Override
    public int updateRating(Film film) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "UPDATE `films` SET `rating`=? WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, film.getRating());
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
                    SET @film_id := ?;
                    DELETE FROM `film_genre` WHERE `filmId`=@film_id;
                    DELETE FROM `film_country` WHERE `filmId`=@film_id;
                    DELETE FROM `follows` WHERE `filmId`=@film_id;
                    DELETE FROM `comments` WHERE `filmId`=@film_id;
                    DELETE FROM `user_notification` WHERE `filmId`=@film_id;
                    DELETE FROM `view_log` WHERE `filmId`=@film_id;
                    DELETE FROM `episodes` WHERE `filmId`=@film_id;
                    DELETE FROM `films` WHERE `id`=@film_id;""";
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
            String sql = "SELECT * FROM `films` " + condition;
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

    @Override
    public List<Film> selectTopViewByDay(int amount) {
        List<Film> results = new ArrayList<>();
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT `films`.* , SUM(`view_log`.`viewed`) AS `d_viewed` " +
                    "FROM `films` " +
                    "INNER JOIN `view_log` ON `view_log`.`filmId` = `films`.`id` " +
                    "WHERE DATE(`view_log`.`date`) = DATE(NOW()) " +
                    "GROUP BY `view_log`.`filmId` " +
                    "ORDER BY `d_viewed` DESC " +
                    "LIMIT ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, amount);

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
    public List<Film> selectTopViewByMonth(int amount) {
        List<Film> results = new ArrayList<>();
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT `films`.* , SUM(`view_log`.`viewed`) AS `d_viewed` " +
                    "FROM `films` " +
                    "INNER JOIN `view_log` ON `view_log`.`filmId` = `films`.`id` " +
                    "WHERE MONTH(`view_log`.`date`) = MONTH(NOW()) AND YEAR(`view_log`.`date`) = YEAR(NOW()) " +
                    "GROUP BY `view_log`.`filmId` " +
                    "ORDER BY `d_viewed` DESC " +
                    "LIMIT ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, amount);

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
    public List<Film> selectTopViewByYear(int amount) {
        List<Film> results = new ArrayList<>();
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT `films`.* , SUM(`view_log`.`viewed`) AS `d_viewed` " +
                    "FROM `films` " +
                    "INNER JOIN `view_log` ON `view_log`.`filmId` = `films`.`id` " +
                    "WHERE YEAR(`view_log`.`date`) = YEAR(NOW()) " +
                    "GROUP BY `view_log`.`filmId` " +
                    "ORDER BY `d_viewed` DESC " +
                    "LIMIT ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, amount);

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
    public int getTotalFollow(int filmId) {
        int result = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT COUNT(*) AS `totalFollow` FROM `follows` WHERE `filmId`=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, filmId);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Get result
            if (res.next()) result = res.getInt("totalFollow");

            // Close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean isFollowed(int filmId, int userId) {
        boolean result = false;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT COUNT(*) FROM `follows` WHERE `filmId`=? AND `userId`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, filmId);
            preparedStatement.setInt(2, userId);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Get result
            if (res.next()) result = res.getInt(1) > 0;

            // Close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int followFilm(int filmId, int userId) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "INSERT INTO `follows` (`userId`,`filmId`) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, filmId);

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
    public int unfollowFilm(int filmId, int userId) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "DELETE FROM `follows` WHERE  `userId` = ? AND `filmId` = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, filmId);

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

}
