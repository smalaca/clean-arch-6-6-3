package com.smalaca.taskamanager.domain.user;

import com.smalaca.taskamanager.model.embedded.UserName;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.model.enums.TeamRole;

public class UserBuilder {
    private final User user;
    private String firstName;
    private String lastName;

    private UserBuilder(User user) {
        this.user = user;
    }

    public static UserBuilder user() {
        return new UserBuilder(new User());
    }

    public User build(UserDomainRepository userRepository) {
        if (userDoesNotExist(userRepository)) {
            return user;
        } else {
            throw UserException.userAlreadyExists(firstName, lastName);
        }
    }

    private boolean userDoesNotExist(UserDomainRepository userRepository) {
        return userRepository.doesUserNotExistsByFirstAndLastName(firstName, lastName);
    }

    public UserBuilder withTeamRole(String teamRole) {
        user.setTeamRole(TeamRole.valueOf(teamRole));
        return this;
    }

    public UserBuilder withUserName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        user.setUserName(userName(firstName, lastName));
        return this;
    }

    private UserName userName(String firstName, String lastName) {
        UserName userName = new UserName();
        userName.setFirstName(firstName);
        userName.setLastName(lastName);
        return userName;
    }

    public UserBuilder withLogin(String login) {
        user.setLogin(login);
        return this;
    }

    public UserBuilder withPassword(String password) {
        user.setPassword(password);
        return this;
    }
}
