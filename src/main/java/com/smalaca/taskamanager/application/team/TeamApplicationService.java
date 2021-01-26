package com.smalaca.taskamanager.application.team;

import com.smalaca.taskamanager.domain.team.TeamException;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.repository.TeamRepository;

public class TeamApplicationService {
    private final TeamRepository teamRepository;

    public TeamApplicationService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Long create(String name) {
        if (teamRepository.findByName(name).isEmpty()){
            Team team = new Team();
            team.setName(name);
            Team saved = teamRepository.save(team);

            return saved.getId();
        } else {
            throw TeamException.teamAlreadyExists(name);
        }
    }
}
