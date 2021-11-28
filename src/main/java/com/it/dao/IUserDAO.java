package com.it.dao;

import com.it.model.User;

public interface IUserDAO {

    User getUserByLogin(String login);
    void updateUser (User user);
    void persistUser (User user);
}
