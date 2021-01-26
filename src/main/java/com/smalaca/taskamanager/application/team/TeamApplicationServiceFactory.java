package com.smalaca.taskamanager.application.team;

import com.smalaca.taskamanager.domain.team.TeamDomainRepository;
import com.smalaca.taskamanager.domain.team.TeamFactory;

public class TeamApplicationServiceFactory {
    public TeamApplicationService teamApplicationService(TeamDomainRepository teamRepository) {
        return new TeamApplicationService(teamRepository, new TeamFactory(teamRepository));
    }
}
