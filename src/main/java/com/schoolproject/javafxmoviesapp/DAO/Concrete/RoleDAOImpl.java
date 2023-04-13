package com.schoolproject.javafxmoviesapp.DAO.Concrete;

import com.schoolproject.javafxmoviesapp.DAO.Interface.RoleDAO;
import com.schoolproject.javafxmoviesapp.Entity.Role;
import com.schoolproject.javafxmoviesapp.Entity.User;
import com.schoolproject.javafxmoviesapp.Utils.JDBCUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements RoleDAO<Role> {
    private static RoleDAOImpl roleDAOImpl = null;

    public static RoleDAOImpl getInstance() {
        if (roleDAOImpl != null) return roleDAOImpl;
        roleDAOImpl = new RoleDAOImpl();
        return roleDAOImpl;
    }

    @Override
    public int insert(Role role) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "INSERT INTO `roles` (`name`, `permissions`) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, role.getName());
            preparedStatement.setString(2, JSONArray.toJSONString(role.getPermissions()));

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
    public int update(Role role) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "UPDATE `roles` SET `name`=?, `permissions`=? WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, role.getName());
            preparedStatement.setString(2, JSONArray.toJSONString(role.getPermissions()));
            preparedStatement.setInt(3, role.getId());

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
    public int delete(Role role) {
        int res = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = """
            SET @role_id := ?;
            SET FOREIGN_KEY_CHECKS = 0;
            UPDATE `users` SET `roleId`=0 WHERE `roleId`=@role_id;
            SET FOREIGN_KEY_CHECKS = 1;
            DELETE FROM `roles` WHERE `id`=@role_id""";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, role.getId());

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
    public List<Role> selectAll() {
        List<Role> results = new ArrayList<>();
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT * FROM `roles`";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Add data to List
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                ArrayList<String> permissions = (ArrayList<String>) ((JSONArray) JSONValue.parse(res.getString("permissions")));

                results.add(new Role(id, name, permissions));
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
    public Role findById(int id) {
        Role role = null;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT * FROM `roles` WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Add data to List
            while (res.next()) {
                String name = res.getString("name");
                ArrayList<String> permissions = (ArrayList<String>) ((JSONArray) JSONValue.parse(res.getString("permissions")));

                role = (new Role(id, name, permissions));
            }

            // Close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return role;
    }

    @Override
    public List<Role> selectByCondition(String condition) {
        List<Role> results = new ArrayList<>();
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT * FROM `roles`" + condition;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Add data to List
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                ArrayList<String> permissions = (ArrayList<String>) ((JSONArray) JSONValue.parse(res.getString("permissions")));

                results.add(new Role(id, name, permissions));
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
    public Role findByUser(User user) {
        Role role = null;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT `roles`.`id`, `roles`.`name`, `roles`.`permissions` FROM `users` INNER JOIN `roles` ON `users`.`roleId` = `roles`.`id` WHERE `users`.`id`=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getId());

            // Execute SQL
            ResultSet res = preparedStatement.executeQuery();

            // Add data to List
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                ArrayList<String> permissions = (ArrayList<String>) ((JSONArray) JSONValue.parse(res.getString("permissions")));
                role = new Role(id, name, permissions);
            }

            // Close Connection
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return role;
    }

    @Override
    public int countAll() {
        int count = 0;
        try {
            // Get Connection
            Connection connection = JDBCUtil.getConnecttion();

            // Create Statement
            String sql = "SELECT COUNT(*) FROM `roles`";
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
            String sql = "SELECT COUNT(*) FROM `roles` " + condition;
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
