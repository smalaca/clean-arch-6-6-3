package com.smalaca.taskamanager.domain.team;

public class TeamDomainTestFactory {
    public static TeamDomain create(String teamName) {
        return new TeamDomain(teamName);
    }
}