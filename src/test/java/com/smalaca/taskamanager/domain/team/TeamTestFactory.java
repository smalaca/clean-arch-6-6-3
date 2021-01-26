package com.smalaca.taskamanager.domain.team;

import com.smalaca.taskamanager.model.entities.Team;

public class TeamTestFactory {
    public static Team create(String name) {
        Team team = new Team();
        team.setName(name);

        return team;
    }
}
