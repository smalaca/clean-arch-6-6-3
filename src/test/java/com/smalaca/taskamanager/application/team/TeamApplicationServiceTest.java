package com.smalaca.taskamanager.application.team;

import com.smalaca.taskamanager.domain.team.TeamDomainRepository;
import com.smalaca.taskamanager.domain.team.TeamException;
import com.smalaca.taskamanager.model.entities.Team;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class TeamApplicationServiceTest {
    private static final String TEAM_NAME = "Avengers";
    private static final Long TEAM_ID = 1234L;

    private final TeamDomainRepository repository = mock(TeamDomainRepository.class);
    private final TeamApplicationService service = new TeamApplicationServiceFactory().teamApplicationService(repository);

    @Test
    void shouldCreateTeam() {
        ArgumentCaptor<Team> captor = ArgumentCaptor.forClass(Team.class);
        givenNonExistingTeam();
        givenSavedTeam();

        Long id = service.create(TEAM_NAME);

        assertThat(id).isEqualTo(TEAM_ID);
        then(repository).should().save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo(TEAM_NAME);
    }

    private void givenNonExistingTeam() {
        given(repository.doesNotExistByName(TEAM_NAME)).willReturn(true);
    }

    private void givenSavedTeam() {
        given(repository.save(any())).willReturn(TEAM_ID);
    }

    @Test
    void shouldRecognizeItIsNotPossibleToCreateTeam() {
        givenExistingTeam();

        TeamException actual = assertThrows(TeamException.class, () -> service.create(TEAM_NAME));

        assertThat(actual).hasMessage("Team with name: Avengers already exists.");
    }

    private void givenExistingTeam() {
        given(repository.doesNotExistByName(TEAM_NAME)).willReturn(false);
    }
}