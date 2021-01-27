package com.smalaca.taskamanager.application.user;

class UserApplicationServiceTest {
//    private static final String FIRST_NAME = "Peter";
//    private static final String LAST_NAME = "Parker";
//    private static final String TEAM_ROLE = "DEVELOPER";
//    private static final String LOGIN = "spiderman";
//    private static final String PASSWORD = "w3bRUL3Z";
//    private static final Long USER_ID = 123L;
//
//    private final UserDomainRepository repository = mock(UserDomainRepository.class);
//    private final UserApplicationService service = new UserApplicationService(repository);
//
//    @Test
//    void shouldRecognizeCreatedUserAlreadyExists() {
//        givenExistingUser();
//
//        UserException actual = assertThrows(UserException.class, () -> service.create(givenUserDto()));
//        assertThat(actual).hasMessage("User Peter Parker already exists.");
//    }
//
//    private void givenExistingUser() {
//        given(repository.doesUserNotExistsByFirstAndLastName(FIRST_NAME, LAST_NAME)).willReturn(false);
//    }
//
//    @Test
//    void shouldCreateUser() {
//        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
//        givenNonExistingUser();
//        given(repository.saveUser(any())).willReturn(USER_ID);
//
//        Long id = service.create(givenUserDto());
//
//        assertThat(id).isEqualTo(USER_ID);
//        then(repository).should().saveUser(captor.capture());
//        User actual = captor.getValue();
//        assertThat(actual.getUserName().getFirstName()).isEqualTo(FIRST_NAME);
//        assertThat(actual.getUserName().getLastName()).isEqualTo(LAST_NAME);
//        assertThat(actual.getTeamRole()).isEqualTo(DEVELOPER);
//        assertThat(actual.getLogin()).isEqualTo(LOGIN);
//        assertThat(actual.getPassword()).isEqualTo(PASSWORD);
//    }
//
//    private void givenNonExistingUser() {
//        given(repository.doesUserNotExistsByFirstAndLastName(FIRST_NAME, LAST_NAME)).willReturn(true);
//    }
//
//    private UserDto givenUserDto() {
//        UserDto userDto = new UserDto();
//        userDto.setFirstName(FIRST_NAME);
//        userDto.setLastName(LAST_NAME);
//        userDto.setTeamRole(TEAM_ROLE);
//        userDto.setLogin(LOGIN);
//        userDto.setPassword(PASSWORD);
//        return userDto;
//    }
}