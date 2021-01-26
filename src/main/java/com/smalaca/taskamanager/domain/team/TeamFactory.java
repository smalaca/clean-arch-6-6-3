package com.smalaca.taskamanager.domain.team;

import com.smalaca.taskamanager.model.entities.Team;

public class TeamFactory {
    private final TeamDomainRepository teamRepository;

    public TeamFactory(TeamDomainRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team create(String name) {
        if (teamRepository.doesNotExistByName(name)){
            Team team = new Team();
            team.setName(name);
            return team;
        } else {
            throw TeamException.teamAlreadyExists(name);
        }
    }
}
