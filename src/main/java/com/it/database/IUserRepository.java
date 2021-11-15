package com.it.database;

import com.it.model.User;

public interface IUserRepository {

    boolean authenticate(User user);

}
