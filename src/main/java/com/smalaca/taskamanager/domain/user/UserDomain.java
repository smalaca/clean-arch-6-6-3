package com.smalaca.taskamanager.domain.user;

import com.smalaca.taskamanager.domain.owner.OwnerDomain;
import com.smalaca.taskamanager.domain.phonenumber.PhoneNumber;

import static com.smalaca.taskamanager.domain.owner.OwnerDomain.Builder.owner;

public class UserDomain {
    private final String login;
    private final String password;
    private final UserName userName;
    private final TeamRole teamRole;
    private String emailAddress;
    private PhoneNumber phoneNumber;

    UserDomain(String login, String password, UserName userName, TeamRole teamRole) {
        this.login = login;
        this.password = password;
        this.userName = userName;
        this.teamRole = teamRole;
    }

    public UserDomain(UserDomainDto dto) {
        login = dto.getLogin();
        password = dto.getPassword();
        userName = new UserName(dto.getFirstName(), dto.getLastName());
        teamRole = TeamRole.valueOf(dto.getTeamRole());
        emailAddress = dto.getEmailAddress();
        phoneNumber = new PhoneNumber(dto.getPhonePrefix(), dto.getPhoneNumber());
    }

    public UserDomainDto asDto() {
        return UserDomainDto.builder()
                .login(login)
                .password(password)
                .teamRole(teamRole.name())
                .firstName(userName.getFirstName())
                .lastName(userName.getLastName())
                .emailAddress(emailAddress)
                .phoneNumber(phoneNumber.getNumber())
                .phonePrefix(phoneNumber.getPrefix())
                .build();
    }

    public OwnerDomain asOwner() {
        OwnerDomain.Builder ownerBuilder = owner(userName.getFirstName(), userName.getLastName());

        if (hasEmailAddress()) {
            ownerBuilder.withEmailAddress(emailAddress);
        }

        if (hasPhoneNumber()) {
            ownerBuilder.withPhoneNumber(phoneNumber);
        }

        return ownerBuilder.build();
    }

    private boolean hasPhoneNumber() {
        return phoneNumber != null;
    }

    private boolean hasEmailAddress() {
        return emailAddress != null;
    }
}
