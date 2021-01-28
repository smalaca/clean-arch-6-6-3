package com.smalaca.taskamanager.domain.team;

public interface TeamDomainRepository {
    Long saveTeam(TeamDomain team);

    boolean doesTeamNotExistByName(String name);
}
