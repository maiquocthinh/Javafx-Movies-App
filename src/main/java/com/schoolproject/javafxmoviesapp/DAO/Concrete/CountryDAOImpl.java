package com.schoolproject.javafxmoviesapp.DAO.Concrete;

import com.schoolproject.javafxmoviesapp.DAO.Interface.CountryDAO;
import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Utils.JDBCUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDAOImpl implements CountryDAO<Country> {
    private static CountryDAOImpl countryDAOImpl = null;

    public static CountryDAOImpl getInstance() {
        if (countryDAOImpl != null) return countryDAOImpl;
        countryDAOImpl = new CountryDAOImpl();
        return countryDAOImpl;
    }

    @Override
    public int insert(Country country) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "INSERT INTO `countries` (`name`) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, country.getName());

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
    public int update(Country country) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "UPDATE `countries` SET `name`=? WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, country.getName());
            preparedStatement.setInt(2, country.getId());

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
    public int delete(Country country) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "DELETE FROM `countries` WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, country.getId());

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
    public List<Country> selectAll() {
        List<Country> results = new ArrayList<>();
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT * FROM `countries`";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Add data to List
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");

                results.add(new Country(id, name));
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
    public Country findById(int id) {
        Country country = null;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT * FROM `countries` WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Add data to List
            while (res.next()) {
                String name = res.getString("name");

                country = (new Country(id, name));
            }

            // Close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return country;
    }

    @Override
    public List<Country> selectByCondition(String condition) {
        List<Country> results = new ArrayList<>();
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT * FROM `countries`" + condition;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Add data to List
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");

                results.add(new Country(id, name));
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
            String sql = "SELECT COUNT(*) FROM `countries`";
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
            String sql = "SELECT COUNT(*) FROM `countries` " + condition;
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
