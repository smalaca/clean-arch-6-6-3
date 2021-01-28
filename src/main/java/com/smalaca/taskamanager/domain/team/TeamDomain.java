package com.smalaca.taskamanager.domain.team;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEAM")
public class TeamDomain {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private TeamDomain() {}

    TeamDomain(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
}
