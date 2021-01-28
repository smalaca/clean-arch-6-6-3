package com.smalaca.taskamanager.domain.team;

public class TeamDomain {
    private final String name;

    TeamDomain(String name) {
        this.name = name;
    }

    public TeamDomainDto asDto() {
        return TeamDomainDto.builder()
                .name(name)
                .build();
    }
}
