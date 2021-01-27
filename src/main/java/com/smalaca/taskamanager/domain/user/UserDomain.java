package com.smalaca.taskamanager.domain.user;

public class UserDomain {
    private final String login;
    private final String password;
    private final UserName userName;
    private final TeamRole teamRole;

    UserDomain(String login, String password, UserName userName, TeamRole teamRole) {
        this.login = login;
        this.password = password;
        this.userName = userName;
        this.teamRole = teamRole;
    }

    public UserDomainDto asDto() {
        return UserDomainDto.builder()
                .login(login)
                .password(password)
                .teamRole(teamRole.name())
                .firstName(userName.getFirstName())
                .lastName(userName.getLastName())
                .build();
    }
}
