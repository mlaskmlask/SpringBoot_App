package com.it.database.impl;

import com.it.database.IUserRepository;
import com.it.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class JDBCUserRepositoryImpl implements IUserRepository {

    @Autowired
    Connection connection;

    @Override
    public User authenticate(User user) {

        User userFromDB = this.getUser(user.getLogin());
        if (userFromDB != null && userFromDB.getPass().equals(DigestUtils.md5Hex(user.getPass()))) {
            return userFromDB;
        }
        return null;
    }

    @Override
    public User updateUserData(User user) {
        try {
            String SQL = "UPDATE tuser SET name=?, surname=? WHERE login=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.executeUpdate();
            return this.getUser(user.getLogin());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User updateUserPass(User user) {
        try {
            String SQL = "UPDATE tuser SET pass=? WHERE login=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, DigestUtils.md5Hex(user.getPass()));
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.executeUpdate();
            return this.getUser(user.getLogin());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean checkIfLoginExists(String login) {
        try {
            String SQL = "SELECT * FROM tuser WHERE login=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    @Override
    public void addUser(User user) {
        try {
            String SQL = "INSERT INTO tuser (name, surname, login, pass) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPass());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private User getUser(String login) {
        try {
            String SQL = "SELECT * FROM tuser WHERE login=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            User userFromDB = new User();
            userFromDB.setId(resultSet.getInt("id"));
            userFromDB.setName(resultSet.getString("name"));
            userFromDB.setSurname(resultSet.getString("surname"));
            userFromDB.setLogin(resultSet.getString("login"));
            userFromDB.setPass(resultSet.getString("pass"));
            return userFromDB;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
