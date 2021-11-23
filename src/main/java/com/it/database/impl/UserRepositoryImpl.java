package com.it.database.impl;

import com.it.database.IUserRepository;
import com.it.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public class UserRepositoryImpl implements IUserRepository {

    private final List<User> userList = new ArrayList<>();
    UserRepositoryImpl() {
        //userList.add(new User("Maja", "Laskowska", "admin", DigestUtils.md5Hex("admin")));
        //userList.add(new User("Jan", "Kowalski", "jan", DigestUtils.md5Hex("jan")));
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

    @Override
    public User updateUserData(User user) {
        for (User currentUser : this.userList) {
            if (currentUser.getLogin().equals(user.getLogin())) {
                currentUser.setName(user.getName());
                currentUser.setSurname(user.getSurname());
                return currentUser;
            }
        }
        return null;
    }

    @Override
    public User updateUserPass(User user) {
        for (User currentUser: this.userList){
            if(currentUser.getLogin().equals(user.getLogin())){
                currentUser.setPass(DigestUtils.md5Hex(user.getPass()));
                return currentUser;
            }
        }
        return null;
    }

    @Override
    public boolean checkIfLoginExists(String login) {
        for(User currentUser : this.userList){
            if(currentUser.getLogin().equals(login)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void addUser(User user) {
        this.userList.add(user);
    }
}
