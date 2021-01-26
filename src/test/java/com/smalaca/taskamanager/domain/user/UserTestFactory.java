package com.smalaca.taskamanager.domain.user;

import com.smalaca.taskamanager.model.embedded.UserName;
import com.smalaca.taskamanager.model.entities.User;

public class UserTestFactory {
    public static User create(String firstName, String lastName) {
        User user = new User();
        UserName userName = new UserName();
        userName.setFirstName(firstName);
        userName.setLastName(lastName);
        user.setUserName(userName);
        return user;
    }
}
