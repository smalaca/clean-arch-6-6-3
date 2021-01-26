package com.smalaca.taskamanager.application.user;

import com.smalaca.taskamanager.domain.user.UserException;
import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.repository.UserRepository;

import static com.smalaca.taskamanager.domain.user.UserBuilder.user;

public class UserApplicationService {
    private final UserRepository userRepository;

    public UserApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long create(UserDto userDto) {
        if (doesNotExist(userDto)) {
            User user = user()
                    .withTeamRole(userDto.getTeamRole())
                    .withUserName(userDto.getFirstName(), userDto.getLastName())
                    .withLogin(userDto.getLogin())
                    .withPassword(userDto.getPassword())
                    .build();

            return userRepository.save(user).getId();
        } else {
            throw UserException.userAlreadyExists(userDto.getFirstName(), userDto.getLastName());
        }
    }

    private boolean doesNotExist(UserDto userDto) {
        return userRepository.findByUserNameFirstNameAndUserNameLastName(userDto.getFirstName(), userDto.getLastName()).isEmpty();
    }
}
