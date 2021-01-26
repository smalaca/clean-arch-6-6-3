package com.smalaca.taskamanager.application.epic;

import com.smalaca.taskamanager.anticorruptionlayer.TaskManagerAntiCorruptionLayer;
import com.smalaca.taskamanager.domain.project.ProjectDomainRepository;
import com.smalaca.taskamanager.domain.user.UserDomainRepository;
import com.smalaca.taskamanager.domain.user.UserException;
import com.smalaca.taskamanager.domain.user.UserTestFactory;
import com.smalaca.taskamanager.dto.EpicDto;
import com.smalaca.taskamanager.exception.ProjectNotFoundException;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Project;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.model.enums.ToDoItemStatus;
import com.smalaca.taskamanager.repository.EpicRepository;
import com.smalaca.taskamanager.repository.ProjectRepository;
import com.smalaca.taskamanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static com.smalaca.taskamanager.model.enums.ToDoItemStatus.IN_PROGRESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class EpicApplicationServiceTest {
    private static final String TITLE = "Title like all the others";
    private static final String DESCRIPTION = "Something have to be done";
    private static final ToDoItemStatus STATUS = IN_PROGRESS;
    private static final String FIRST_NAME = "Nick";
    private static final String LAST_NAME = "Fury";
    private static final String EMAIL_ADDRESS = "nick.fury@shield.marvel.com";
    private static final String PHONE_PREFIX = "567";
    private static final String PHONE_NUMBER = "133131313";
    private static final long OWNER_ID = 42;
    private static final long PROJECT_ID = 69;
    private static final Project PROJECT = new Project();

    private final EpicRepository epicRepository = mock(EpicRepository.class);
    private final ProjectRepository projectRepository = mock(ProjectRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserDomainRepository userDomainRepository = new TaskManagerAntiCorruptionLayer(null, userRepository, null, null);
    private final ProjectDomainRepository projectDomainRepository = new TaskManagerAntiCorruptionLayer(null, null, null, projectRepository);
    private final EpicApplicationService service = new EpicApplicationServiceFactory().epicApplicationService(
            epicRepository, projectDomainRepository, userDomainRepository);

    @Test
    void shouldRecognizeProjectDoesNotExist() {
        givenNonExistingProject();

        assertThrows(ProjectNotFoundException.class, () -> service.create(givenEpicDto()));
    }

    private void givenNonExistingProject() {
        given(projectRepository.existsById(PROJECT_ID)).willReturn(false);
    }

    @Test
    void shouldCreateEpicWithoutOwner() {
        ArgumentCaptor<Epic> captor = ArgumentCaptor.forClass(Epic.class);
        givenExistingProject();
        givenSavedEpic();

        Long id = service.create(givenEpicDto());

        assertThat(id).isNull();
        then(epicRepository).should().save(captor.capture());
        Epic actual = captor.getValue();
        assertThat(actual.getTitle()).isEqualTo(TITLE);
        assertThat(actual.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(actual.getStatus()).isEqualTo(STATUS);
        assertThat(actual.getOwner()).isNull();
        thenProjectWasUpdated(actual);
    }

    @Test
    void shouldRecognizeUserForGivenOwnerIdDoesNotExist() {
        givenExistingProject();
        givenNonExistingUser();

        UserException actual = assertThrows(UserException.class, () -> service.create(givenEpicDtoWithOwnerId()));
        assertThat(actual).hasMessage("User with id " + OWNER_ID + " does not exist");
    }

    private void givenNonExistingUser() {
        given(userRepository.findById(OWNER_ID)).willReturn(Optional.empty());
    }

    @Test
    void shouldCreateEpicWithOwner() {
        ArgumentCaptor<Epic> captor = ArgumentCaptor.forClass(Epic.class);
        givenExistingProject();
        givenExistingUser();
        givenSavedEpic();

        Long id = service.create(givenEpicDtoWithOwnerId());

        assertThat(id).isNull();
        then(epicRepository).should().save(captor.capture());
        Epic actual = captor.getValue();
        assertThat(actual.getTitle()).isEqualTo(TITLE);
        assertThat(actual.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(actual.getStatus()).isEqualTo(STATUS);
        assertThat(actual.getOwner().getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(actual.getOwner().getLastName()).isEqualTo(LAST_NAME);
        assertThat(actual.getOwner().getEmailAddress().getEmailAddress()).isEqualTo(EMAIL_ADDRESS);
        assertThat(actual.getOwner().getPhoneNumber().getPrefix()).isEqualTo(PHONE_PREFIX);
        assertThat(actual.getOwner().getPhoneNumber().getNumber()).isEqualTo(PHONE_NUMBER);
        thenProjectWasUpdated(actual);
    }

    private void thenProjectWasUpdated(Epic actual) {
        then(projectRepository).should().save(PROJECT);
        assertThat(PROJECT.getEpics()).contains(actual);
    }

    private void givenSavedEpic() {
        given(epicRepository.save(any())).willReturn(new Epic());
    }

    private void givenExistingUser() {
        User user = UserTestFactory.create(FIRST_NAME, LAST_NAME, EMAIL_ADDRESS, PHONE_PREFIX, PHONE_NUMBER);
        given(userRepository.findById(OWNER_ID)).willReturn(Optional.of(user));
    }

    private void givenExistingProject() {
        given(projectRepository.existsById(PROJECT_ID)).willReturn(true);
        given(projectRepository.findById(PROJECT_ID)).willReturn(Optional.of(PROJECT));
    }

    private EpicDto givenEpicDtoWithOwnerId() {
        EpicDto dto = givenEpicDto();
        dto.setOwnerId(OWNER_ID);
        return dto;
    }

    private EpicDto givenEpicDto() {
        EpicDto dto = new EpicDto();
        dto.setTitle(TITLE);
        dto.setDescription(DESCRIPTION);
        dto.setStatus(STATUS.name());
        dto.setProjectId(PROJECT_ID);
        return dto;
    }
}