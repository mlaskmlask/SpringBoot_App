package com.it.database;

import com.it.model.User;

public interface IUserRepository {

    User authenticate(User user);

}
