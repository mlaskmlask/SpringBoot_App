package com.it.services;

import com.it.model.User;
import com.it.model.model.ChangePassData;
import com.it.model.model.UserRegistrationData;

public interface IUserService {
    User authenticate(User user);
    User updateUserData (User user);
    User updateUserPass (ChangePassData changePassData);
    boolean registerUser(UserRegistrationData userRegistrationData);
}
