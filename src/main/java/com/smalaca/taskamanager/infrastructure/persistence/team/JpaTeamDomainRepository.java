package com.smalaca.taskamanager.infrastructure.persistence.team;

import com.smalaca.taskamanager.domain.team.TeamDomain;
import com.smalaca.taskamanager.domain.team.TeamDomainRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaTeamDomainRepository implements TeamDomainRepository {
    private final SpringDataTeamDomainRepository repository;

    public JpaTeamDomainRepository(SpringDataTeamDomainRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long saveTeam(TeamDomain team) {
        return null;
    }

    @Override
    public boolean doesTeamNotExistByName(String name) {
        return false;
    }
}
