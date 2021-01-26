package com.smalaca.taskamanager.application.team;

import com.smalaca.taskamanager.domain.team.TeamFactory;
import com.smalaca.taskamanager.repository.TeamRepository;

public class TeamApplicationServiceFactory {
    public TeamApplicationService teamApplicationService(TeamRepository teamRepository) {
        return new TeamApplicationService(teamRepository, new TeamFactory(teamRepository));
    }
}
