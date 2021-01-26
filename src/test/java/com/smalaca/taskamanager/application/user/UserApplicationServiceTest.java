package com.smalaca.taskamanager.application.user;

import com.smalaca.taskamanager.anticorruptionlayer.TaskManagerAntiCorruptionLayer;
import com.smalaca.taskamanager.domain.user.UserDomainRepository;
import com.smalaca.taskamanager.domain.user.UserException;
import com.smalaca.taskamanager.domain.user.UserTestFactory;
import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static com.smalaca.taskamanager.model.enums.TeamRole.DEVELOPER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class UserApplicationServiceTest {
    private static final String FIRST_NAME = "Peter";
    private static final String LAST_NAME = "Parker";
    private static final String TEAM_ROLE = "DEVELOPER";
    private static final String LOGIN = "spiderman";
    private static final String PASSWORD = "w3bRUL3Z";

    private final UserRepository repository = mock(UserRepository.class);
    private final UserDomainRepository userRepository = new TaskManagerAntiCorruptionLayer(null, repository);
    private final UserApplicationService service = new UserApplicationService(userRepository);

    @Test
    void shouldRecognizeCreatedUserAlreadyExists() {
        givenExistingUser();

        UserException actual = assertThrows(UserException.class, () -> service.create(givenUserDto()));
        assertThat(actual).hasMessage("User Peter Parker already exists.");
    }

    private void givenExistingUser() {
        User user = UserTestFactory.create(FIRST_NAME, LAST_NAME);
        given(repository.findByUserNameFirstNameAndUserNameLastName(FIRST_NAME, LAST_NAME)).willReturn(Optional.of(user));
    }

    @Test
    void shouldCreateUser() {
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        givenNonExistingUser();
        given(repository.save(any())).willReturn(UserTestFactory.create(FIRST_NAME, LAST_NAME));

        Long id = service.create(givenUserDto());

        assertThat(id).isNull();
        then(repository).should().save(captor.capture());
        User actual = captor.getValue();
        assertThat(actual.getUserName().getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(actual.getUserName().getLastName()).isEqualTo(LAST_NAME);
        assertThat(actual.getTeamRole()).isEqualTo(DEVELOPER);
        assertThat(actual.getLogin()).isEqualTo(LOGIN);
        assertThat(actual.getPassword()).isEqualTo(PASSWORD);
    }

    private void givenNonExistingUser() {
        given(repository.findByUserNameFirstNameAndUserNameLastName(FIRST_NAME, LAST_NAME)).willReturn(Optional.empty());
    }

    private UserDto givenUserDto() {
        UserDto userDto = new UserDto();
        userDto.setFirstName(FIRST_NAME);
        userDto.setLastName(LAST_NAME);
        userDto.setTeamRole(TEAM_ROLE);
        userDto.setLogin(LOGIN);
        userDto.setPassword(PASSWORD);
        return userDto;
    }
}