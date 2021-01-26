package com.smalaca.taskamanager.application.user;

import com.smalaca.taskamanager.domain.user.UserException;
import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.model.embedded.UserName;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.model.enums.TeamRole;
import com.smalaca.taskamanager.repository.UserRepository;

public class UserApplicationService {
    private final UserRepository userRepository;

    public UserApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long create(UserDto userDto) {
        if (doesNotExist(userDto)) {
            User user = new User();
            user.setTeamRole(TeamRole.valueOf(userDto.getTeamRole()));
            UserName userName = new UserName();
            userName.setFirstName(userDto.getFirstName());
            userName.setLastName(userDto.getLastName());
            user.setUserName(userName);
            user.setLogin(userDto.getLogin());
            user.setPassword(userDto.getPassword());

            return userRepository.save(user).getId();
        } else {
            throw UserException.userAlreadyExists(userDto.getFirstName(), userDto.getLastName());
        }
    }

    private boolean doesNotExist(UserDto userDto) {
        return !exists(userDto);
    }

    private boolean exists(UserDto userDto) {
        return !userRepository.findByUserNameFirstNameAndUserNameLastName(userDto.getFirstName(), userDto.getLastName()).isEmpty();
    }
}
