package com.smalaca.taskamanager.domain.team;

import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.repository.TeamRepository;

public class TeamFactory {
    private final TeamRepository teamRepository;

    public TeamFactory(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team create(String name) {
        if (teamRepository.findByName(name).isEmpty()){
            Team team = new Team();
            team.setName(name);
            return team;
        } else {
            throw TeamException.teamAlreadyExists(name);
        }
    }
}
