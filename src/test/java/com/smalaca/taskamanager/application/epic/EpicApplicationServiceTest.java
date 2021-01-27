package com.smalaca.taskamanager.application.epic;

class EpicApplicationServiceTest {
//    private static final String TITLE = "Title like all the others";
//    private static final String DESCRIPTION = "Something have to be done";
//    private static final ToDoItemStatus STATUS = IN_PROGRESS;
//    private static final String FIRST_NAME = "Nick";
//    private static final String LAST_NAME = "Fury";
//    private static final String EMAIL_ADDRESS = "nick.fury@shield.marvel.com";
//    private static final String PHONE_PREFIX = "567";
//    private static final String PHONE_NUMBER = "133131313";
//    private static final long OWNER_ID = 42;
//    private static final long PROJECT_ID = 69;
//    private static final Project PROJECT = new Project();
//    private static final long EPIC_ID = 1357L;
//
//    private final EpicDomainRepository epicRepository = mock(EpicDomainRepository.class);
//    private final UserDomainRepository userRepository = mock(UserDomainRepository.class);
//    private final ProjectDomainRepository projectRepository = mock(ProjectDomainRepository.class);
//    private final EpicApplicationService service = new EpicApplicationServiceFactory().epicApplicationService(epicRepository, projectRepository, userRepository);
//
//    @Test
//    void shouldRecognizeProjectDoesNotExist() {
//        givenNonExistingProject();
//
//        assertThrows(ProjectNotFoundException.class, () -> service.create(givenEpicDto()));
//    }
//
//    private void givenNonExistingProject() {
//        given(projectRepository.existsProjectById(PROJECT_ID)).willReturn(false);
//    }
//
//    @Test
//    void shouldCreateEpicWithoutOwner() {
//        ArgumentCaptor<Epic> captor = ArgumentCaptor.forClass(Epic.class);
//        givenExistingProject();
//        givenSavedEpic();
//
//        Long id = service.create(givenEpicDto());
//
//        assertThat(id).isEqualTo(EPIC_ID);
//        then(epicRepository).should().saveEpic(captor.capture());
//        Epic actual = captor.getValue();
//        assertThat(actual.getTitle()).isEqualTo(TITLE);
//        assertThat(actual.getDescription()).isEqualTo(DESCRIPTION);
//        assertThat(actual.getStatus()).isEqualTo(STATUS);
//        assertThat(actual.getOwner()).isNull();
//        thenProjectWasUpdated(actual);
//    }
//
//    @Test
//    void shouldRecognizeUserForGivenOwnerIdDoesNotExist() {
//        givenExistingProject();
//        givenNonExistingUser();
//
//        UserException actual = assertThrows(UserException.class, () -> service.create(givenEpicDtoWithOwnerId()));
//        assertThat(actual).hasMessage("User with id " + OWNER_ID + " does not exist");
//    }
//
//    private void givenNonExistingUser() {
//        given(userRepository.existsUserById(OWNER_ID)).willReturn(false);
//    }
//
//    @Test
//    void shouldCreateEpicWithOwner() {
//        ArgumentCaptor<Epic> captor = ArgumentCaptor.forClass(Epic.class);
//        givenExistingProject();
//        givenExistingUser();
//        givenSavedEpic();
//
//        Long id = service.create(givenEpicDtoWithOwnerId());
//
//        assertThat(id).isEqualTo(EPIC_ID);
//        then(epicRepository).should().saveEpic(captor.capture());
//        Epic actual = captor.getValue();
//        assertThat(actual.getTitle()).isEqualTo(TITLE);
//        assertThat(actual.getDescription()).isEqualTo(DESCRIPTION);
//        assertThat(actual.getStatus()).isEqualTo(STATUS);
//        assertThat(actual.getOwner().getFirstName()).isEqualTo(FIRST_NAME);
//        assertThat(actual.getOwner().getLastName()).isEqualTo(LAST_NAME);
//        assertThat(actual.getOwner().getEmailAddress().getEmailAddress()).isEqualTo(EMAIL_ADDRESS);
//        assertThat(actual.getOwner().getPhoneNumber().getPrefix()).isEqualTo(PHONE_PREFIX);
//        assertThat(actual.getOwner().getPhoneNumber().getNumber()).isEqualTo(PHONE_NUMBER);
//        thenProjectWasUpdated(actual);
//    }
//
//    private void thenProjectWasUpdated(Epic actual) {
//        then(projectRepository).should().saveProject(PROJECT);
//        assertThat(PROJECT.getEpics()).contains(actual);
//    }
//
//    private void givenSavedEpic() {
//        given(epicRepository.saveEpic(any())).willReturn(EPIC_ID);
//    }
//
//    private void givenExistingUser() {
//        User user = UserTestFactory.create(FIRST_NAME, LAST_NAME, EMAIL_ADDRESS, PHONE_PREFIX, PHONE_NUMBER);
//        given(userRepository.existsUserById(OWNER_ID)).willReturn(true);
//        given(userRepository.findUserById(OWNER_ID)).willReturn(user);
//    }
//
//    private void givenExistingProject() {
//        given(projectRepository.existsProjectById(PROJECT_ID)).willReturn(true);
//        given(projectRepository.findProjectById(PROJECT_ID)).willReturn(PROJECT);
//    }
//
//    private EpicDto givenEpicDtoWithOwnerId() {
//        EpicDto dto = givenEpicDto();
//        dto.setOwnerId(OWNER_ID);
//        return dto;
//    }
//
//    private EpicDto givenEpicDto() {
//        EpicDto dto = new EpicDto();
//        dto.setTitle(TITLE);
//        dto.setDescription(DESCRIPTION);
//        dto.setStatus(STATUS.name());
//        dto.setProjectId(PROJECT_ID);
//        return dto;
//    }
}