package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {

    }

    private Connection connection = getConnection();

    public void createUsersTable() {
        PreparedStatement preparedStatement = null;
        String sql = "CREATE TABLE `mydbtest`.`JMUsers` (\n" +
                "  `id` BIGINT(19) NOT NULL AUTO_INCREMENT,\n" +
                "  `user_name` VARCHAR(45) NOT NULL,\n" +
                "  `user_lastName` VARCHAR(45) NOT NULL,\n" +
                "  `user_age` SMALLINT NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            System.out.println("Table create");
        } catch (SQLException e) {
            System.out.println("Table already exists");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void dropUsersTable() {
        PreparedStatement preparedStatement = null;
        String sql = "DROP TABLE `mydbtest`.`JMUsers`;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            System.out.println("Table droped");
        } catch (SQLException e) {
            System.out.println("Table failed droped. Table does not exist.");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO mydbtest.JMUsers (user_name, user_lastName, user_age) VALUES(?,?,?);";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM mydbtest.JMUsers WHERE id = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM mydbtest.JMUsers;";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("user_name"));
                user.setLastName(resultSet.getString("user_lastName"));
                user.setAge(resultSet.getByte("user_age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        return userList;
    }

    public void cleanUsersTable() {

        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM mydbtest.JMUsers;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
