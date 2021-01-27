package com.smalaca.taskamanager.application.user;

import com.smalaca.taskamanager.domain.user.UserBuilder;
import com.smalaca.taskamanager.domain.user.UserDomain;
import com.smalaca.taskamanager.domain.user.UserDomainRepository;
import com.smalaca.taskamanager.dto.UserDto;

public class UserApplicationService {
    private final UserDomainRepository userRepository;

    public UserApplicationService(UserDomainRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long create(UserDto userDto) {
        UserDomain user = UserBuilder.user()
                .withTeamRole(userDto.getTeamRole())
                .withUserName(userDto.getFirstName(), userDto.getLastName())
                .withLogin(userDto.getLogin())
                .withPassword(userDto.getPassword())
                .build(userRepository);

        return userRepository.saveUser(user);
    }
}
