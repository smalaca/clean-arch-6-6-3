package com.smalaca.taskamanager.domain.team;

import com.smalaca.taskamanager.model.entities.Team;

public interface TeamDomainRepository {
    Long saveTeam(Team team);

    boolean doesTeamNotExistByName(String name);
}
