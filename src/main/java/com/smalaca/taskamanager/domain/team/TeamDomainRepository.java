package com.smalaca.taskamanager.domain.team;

import com.smalaca.taskamanager.model.entities.Team;

public interface TeamDomainRepository {
    Long save(Team team);

    boolean doesNotExistByName(String name);
}
