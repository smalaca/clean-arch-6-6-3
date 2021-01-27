package com.smalaca.taskamanager.domain.user;

public class UserBuilder {
    private String firstName;
    private String lastName;
    private String teamRole;
    private String login;
    private String password;

    public static UserBuilder user() {
        return new UserBuilder();
    }

    public UserDomain build(UserDomainRepository userRepository) {
        if (userDoesNotExist(userRepository)) {
            return new UserDomain(login, password, userName(), teamRole());
        } else {
            throw UserException.userAlreadyExists(firstName, lastName);
        }
    }

    private TeamRole teamRole() {
        return TeamRole.valueOf(teamRole);
    }

    private UserName userName() {
        return new UserName(firstName, lastName);
    }

    private boolean userDoesNotExist(UserDomainRepository userRepository) {
        return userRepository.doesUserNotExistsByFirstAndLastName(firstName, lastName);
    }

    public UserBuilder withTeamRole(String teamRole) {
        this.teamRole = teamRole;
        return this;
    }

    public UserBuilder withUserName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withLogin(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }
}
