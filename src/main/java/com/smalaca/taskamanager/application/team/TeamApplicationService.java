package com.smalaca.taskamanager.application.team;

import com.smalaca.taskamanager.domain.team.TeamDomainRepository;
import com.smalaca.taskamanager.domain.team.TeamFactory;
import com.smalaca.taskamanager.model.entities.Team;

public class TeamApplicationService {
    private final TeamDomainRepository teamRepository;
    private final TeamFactory teamFactory;

    TeamApplicationService(TeamDomainRepository teamRepository, TeamFactory teamFactory) {
        this.teamRepository = teamRepository;
        this.teamFactory = teamFactory;
    }

    public Long create(String name) {
        Team team = teamFactory.create(name);

        return teamRepository.saveTeam(team);
    }
}
