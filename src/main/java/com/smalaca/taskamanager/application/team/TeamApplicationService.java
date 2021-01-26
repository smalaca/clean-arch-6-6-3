package com.smalaca.taskamanager.application.team;

import com.smalaca.taskamanager.domain.team.TeamFactory;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.repository.TeamRepository;

public class TeamApplicationService {
    private final TeamRepository teamRepository;

    public TeamApplicationService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Long create(String name) {
        Team team = new TeamFactory(teamRepository).create(name);
        Team saved = teamRepository.save(team);

        return saved.getId();
    }
}
