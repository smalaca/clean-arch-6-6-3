package com.smalaca.taskamanager.domain.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDomainDto {
    private final String firstName;
    private final String lastName;
    private final String teamRole;
    private final String login;
    private final String password;
}
