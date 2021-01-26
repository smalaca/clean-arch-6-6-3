package com.smalaca.taskamanager.domain.team;

public class TeamException extends RuntimeException{
    private TeamException(String message) {
        super(message);
    }

    public static RuntimeException teamAlreadyExists(String name) {
        return new TeamException("Team with name: " + name + " already exists.");
    }
}
