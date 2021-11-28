package com.it.services.impl;

import com.it.dao.IUserDAO;
import com.it.model.User;
import com.it.model.model.ChangePassData;
import com.it.model.model.UserRegistrationData;
import com.it.services.IUserService;
import com.it.session.SessionObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserDAO userDAO;

    @Resource
    SessionObject sessionObject;

    @Override
    public User authenticate(User user) {
        User userFromDatabase = this.userDAO.getUserByLogin(user.getLogin());
        if (userFromDatabase != null && userFromDatabase.getPass().equals(DigestUtils.md5Hex(user.getPass()))) {
            return userFromDatabase;
        } else {
            return null;
        }
    }

    @Override
    public User updateUserData(User user) {
        User currentUser = this.sessionObject.getUser();
        currentUser.setName(user.getName());
        currentUser.setSurname(user.getSurname());
        this.userDAO.updateUser(currentUser);
        return currentUser;
    }

    @Override
    public User updateUserPass(ChangePassData changePassData) {
        User user = new User();
        user.setLogin(this.sessionObject.getUser().getLogin());
        user.setPass(changePassData.getPass());
        User authenticatedUser = this.authenticate(user);
        if (authenticatedUser == null) {
            return null;
        }
        authenticatedUser.setPass(DigestUtils.md5Hex(changePassData.getNewPass()));
        this.userDAO.updateUser(authenticatedUser);
        return authenticatedUser;
    }

    @Override
    public boolean registerUser(UserRegistrationData userRegistrationData) {
        User userFromDatabase = this.userDAO.getUserByLogin(userRegistrationData.getLogin());
        if(userFromDatabase !=null){
            return false;
        }
        User user = new User();
        user.setName(userRegistrationData.getName());
        user.setSurname(userRegistrationData.getSurname());
        user.setLogin(userRegistrationData.getLogin());
        user.setPass(DigestUtils.md5Hex(userRegistrationData.getPass()));

        this.userDAO.persistUser(user);
        return true;
    }
}
