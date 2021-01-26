package com.smalaca.taskamanager.anticorruptionlayer;

import com.smalaca.taskamanager.domain.team.TeamDomainRepository;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.repository.TeamRepository;

public class TaskManagerAntiCorruptionLayer implements TeamDomainRepository {
    private final TeamRepository repository;

    public TaskManagerAntiCorruptionLayer(TeamRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long save(Team team) {
        return repository.save(team).getId();
    }

    @Override
    public boolean doesNotExistByName(String name) {
        return repository.findByName(name).isEmpty();
    }
}
