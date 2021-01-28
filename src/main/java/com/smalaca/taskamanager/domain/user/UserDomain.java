package com.smalaca.taskamanager.domain.user;

import com.smalaca.taskamanager.domain.owner.OwnerDomain;
import com.smalaca.taskamanager.domain.phonenumber.PhoneNumber;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.smalaca.taskamanager.domain.owner.OwnerDomain.Builder.owner;

@Entity
@Table(name = "USER")
public class UserDomain {
    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String password;

    @Embedded
    private UserName userName;

    @Enumerated(EnumType.STRING)
    private TeamRole teamRole;
    private String emailAddress;
    @Embedded
    private PhoneNumber phoneNumber;

    private UserDomain() {}

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
        UserDomainDto.UserDomainDtoBuilder builder = UserDomainDto.builder()
                .login(login)
                .password(password)
                .teamRole(teamRole.name())
                .firstName(userName.getFirstName())
                .lastName(userName.getLastName());

        if (hasEmailAddress()) {
            builder.emailAddress(emailAddress);
        }

        if (hasPhoneNumber()) {
            builder
                    .phoneNumber(phoneNumber.getNumber())
                    .phonePrefix(phoneNumber.getPrefix());
        }

        return builder.build();
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

    public Long getId() {
        return id;
    }
}
