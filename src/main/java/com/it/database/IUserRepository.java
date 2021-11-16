package com.it.database;

import com.it.model.User;

public interface IUserRepository {

    User authenticate(User user);

    User updateUserData(User user);

    User updateUserPass (User user);

    boolean checkIfLoginExists (String login);

    void addUser (User user);
}
