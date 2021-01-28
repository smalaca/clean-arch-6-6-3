package com.smalaca.taskamanager.infrastructure.persistence.team;

import com.smalaca.taskamanager.domain.team.TeamDomain;
import com.smalaca.taskamanager.domain.team.TeamDomainRepository;
import org.springframework.stereotype.Repository;

@Repository
class JpaTeamDomainRepository implements TeamDomainRepository {
    private final SpringDataTeamDomainRepository repository;

    JpaTeamDomainRepository(SpringDataTeamDomainRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long saveTeam(TeamDomain team) {
        return repository.save(team).getId();
    }

    @Override
    public boolean doesTeamNotExistByName(String name) {
        return !repository.existsByName(name);
    }
}
