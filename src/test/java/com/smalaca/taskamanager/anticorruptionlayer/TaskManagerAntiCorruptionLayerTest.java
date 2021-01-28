package com.smalaca.taskamanager.anticorruptionlayer;

import com.smalaca.taskamanager.domain.team.TeamDomain;
import com.smalaca.taskamanager.domain.team.TeamDomainTestFactory;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.repository.TeamRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TaskManagerAntiCorruptionLayerTest {
    private static final String TEAM_NAME = "Defenders";

    @Autowired private TaskManagerAntiCorruptionLayer antiCorruptionLayer;
    @Autowired private TeamRepository teamRepository;

    private Long id;

    @AfterEach
    void deleteTeam() {
        if (id != null) {
            teamRepository.deleteById(id);
        }
    }

    @Test
    void shouldRecognizeTeamDoesNotExistByName() {
        boolean actual = antiCorruptionLayer.doesTeamNotExistByName(TEAM_NAME);
        Optional<Team> verification = teamRepository.findByName(TEAM_NAME);

        assertThat(actual).isTrue();
        assertThat(verification).isEmpty();
    }

    @Test
    void shouldRecognizeTeamExistsByName() {
        TeamDomain team = TeamDomainTestFactory.create(TEAM_NAME);
        id = antiCorruptionLayer.saveTeam(team);

        boolean actual = antiCorruptionLayer.doesTeamNotExistByName(TEAM_NAME);
        Team verification = teamRepository.findByName(TEAM_NAME).get();

        assertThat(actual).isFalse();
        assertThat(verification.getName()).isEqualTo(TEAM_NAME);
    }
}