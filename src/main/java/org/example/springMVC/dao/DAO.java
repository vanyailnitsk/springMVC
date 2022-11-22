package org.example.springMVC.dao;

import org.example.springMVC.util.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DAO {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/users";
    static final String USER = "postgres";
    static final String PASS = "meepo";

    public DAO() {
    }
    public User getUserObject(String login, String password) {
        User user = new User();
        String SQL_SELECT = String.format("select * from Users where login='%s' and password='%s'",login,password);
        try (Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            user.setLogin(login);
            user.setPassword(password);
            if (resultSet.next()) {
                 user.setName(resultSet.getString("name"));
                 user.setId(resultSet.getInt("id"));
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    public User getUserObject(int id) {
        User user = new User();
        String SQL_SELECT = String.format("select * from users where id=%d",id);
        try (Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setName(resultSet.getString("name"));
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<>();
        String SQL_SELECT = "select * from users";
        try (Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword("hidden");
                list.add(user);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void createUser(User user) {
        String SQL = "insert into users (name,login,password,email) values(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL)) {

            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getLogin());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.setString(4,user.getEmail());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateUser(User user) {
        String SQL = "update users set name=?, email=?,password=? where id=?";
        try (Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL)) {

            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.setInt(4,user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int id) {
        String SQL = "delete from users where id=?";
        try (Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL)) {

            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
