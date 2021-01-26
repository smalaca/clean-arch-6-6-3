package com.smalaca.taskamanager.application.team;

import com.smalaca.taskamanager.domain.team.TeamFactory;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.repository.TeamRepository;

public class TeamApplicationService {
    private final TeamRepository teamRepository;
    private final TeamFactory teamFactory;

    public TeamApplicationService(TeamRepository teamRepository, TeamFactory teamFactory) {
        this.teamRepository = teamRepository;
        this.teamFactory = teamFactory;
    }

    public Long create(String name) {
        Team team = teamFactory.create(name);
        Team saved = teamRepository.save(team);

        return saved.getId();
    }
}
