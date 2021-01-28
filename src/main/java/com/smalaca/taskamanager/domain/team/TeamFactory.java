package com.smalaca.taskamanager.domain.team;

public class TeamFactory {
    private final TeamDomainRepository teamRepository;

    public TeamFactory(TeamDomainRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public TeamDomain create(String name) {
        if (teamRepository.doesTeamNotExistByName(name)){
            return new TeamDomain(name);
        } else {
            throw TeamException.teamAlreadyExists(name);
        }
    }
}
