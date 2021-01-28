package com.smalaca.taskamanager.domain.user;

public class UserDomainTestFactory {
    public static UserDomain create(String login, String password, String firstName, String lastName, String teamRoleName) {
        UserName userName = new UserName(firstName, lastName);
        TeamRole teamRole = TeamRole.valueOf(teamRoleName);
        return new UserDomain(login, password, userName, teamRole);
    }

    public static UserDomain create(
            String login, String password, String firstName, String lastName, String teamRole, String emailAddress, String phonePrefix, String phoneNumber) {
        UserDomainDto dto = UserDomainDto.builder()
                .login(login)
                .password(password)
                .teamRole(teamRole)
                .firstName(firstName)
                .lastName(lastName)
                .emailAddress(emailAddress)
                .phoneNumber(phoneNumber)
                .phonePrefix(phonePrefix)
                .build();

        return new UserDomain(dto);
    }
}
