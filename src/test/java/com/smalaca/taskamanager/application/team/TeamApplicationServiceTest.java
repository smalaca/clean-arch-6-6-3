package com.smalaca.taskamanager.application.team;

import com.smalaca.taskamanager.domain.team.TeamException;
import com.smalaca.taskamanager.domain.team.TeamTestFactory;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class TeamApplicationServiceTest {
    private static final String TEAM_NAME = "Avengers";

    private final TeamRepository repository = mock(TeamRepository.class);
    private final TeamApplicationService service = new TeamApplicationServiceFactory().teamApplicationService(repository);

    @Test
    void shouldCreateTeam() {
        ArgumentCaptor<Team> captor = ArgumentCaptor.forClass(Team.class);
        givenNonExistingTeam();
        givenSavedTeam();

        Long id = service.create(TEAM_NAME);

        assertThat(id).isNull();
        then(repository).should().save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo(TEAM_NAME);
    }

    private void givenNonExistingTeam() {
        given(repository.findByName(TEAM_NAME)).willReturn(Optional.empty());
    }

    private void givenSavedTeam() {
        given(repository.save(any())).willReturn(TeamTestFactory.create(TEAM_NAME));
    }

    @Test
    void shouldRecognizeItIsNotPossibleToCreateTeam() {
        givenExistingTeam();

        TeamException actual = assertThrows(TeamException.class, () -> service.create(TEAM_NAME));

        assertThat(actual).hasMessage("Team with name: Avengers already exists.");
    }

    private void givenExistingTeam() {
        Optional<Team> existingTeam = Optional.of(TeamTestFactory.create(TEAM_NAME));
        given(repository.findByName(TEAM_NAME)).willReturn(existingTeam);
    }
}