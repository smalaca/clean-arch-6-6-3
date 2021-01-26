package com.smalaca.taskamanager.application.user;

import com.smalaca.taskamanager.domain.user.UserDomainRepository;
import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.model.entities.User;

import static com.smalaca.taskamanager.domain.user.UserBuilder.user;

public class UserApplicationService {
    private final UserDomainRepository userRepository;

    public UserApplicationService(UserDomainRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long create(UserDto userDto) {
        User user = user()
                .withTeamRole(userDto.getTeamRole())
                .withUserName(userDto.getFirstName(), userDto.getLastName())
                .withLogin(userDto.getLogin())
                .withPassword(userDto.getPassword())
                .build(userRepository);

        return userRepository.save(user);
    }
}
