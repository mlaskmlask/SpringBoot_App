package com.it.database.impl;

import com.it.database.IUserRepository;
import com.it.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepositoryImpl implements IUserRepository {

    private final List<User> userList = new ArrayList<>();

    UserRepositoryImpl() {
        userList.add(new User("Maja", "Laskowska", "admin", DigestUtils.md5Hex("admin")));
    }

    @Override
    public User authenticate(User user) {
        for (User currentUser : this.userList) {
            if (currentUser.getLogin().equals(user.getLogin())) {
                if (currentUser.getPass().equals(DigestUtils.md5Hex(user.getPass()))) {
                    return currentUser;
                } else {
                    return null;
                }
            }
        }
        return null;
    }
}
