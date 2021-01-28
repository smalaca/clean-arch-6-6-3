package com.smalaca.taskamanager.infrastructure.persistence.team;

import com.smalaca.taskamanager.domain.team.TeamDomain;
import com.smalaca.taskamanager.domain.team.TeamDomainRepository;
import com.smalaca.taskamanager.domain.team.TeamDomainTestFactory;
import com.smalaca.taskamanager.repository.TeamRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JpaTeamDomainRepositoryIntegrationTest {
    private static final String TEAM_NAME = "Defenders";

    @Autowired private TeamDomainRepository teamDomainRepository;
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
        boolean actual = teamDomainRepository.doesTeamNotExistByName(TEAM_NAME);

        assertThat(actual).isTrue();
    }

    @Test
    void shouldRecognizeTeamExistsByName() {
        TeamDomain team = TeamDomainTestFactory.create(TEAM_NAME);
        id = teamDomainRepository.saveTeam(team);

        boolean actual = teamDomainRepository.doesTeamNotExistByName(TEAM_NAME);

        assertThat(actual).isFalse();
    }
}