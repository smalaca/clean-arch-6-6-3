package com.smalaca.taskamanager.infrastructure.persistence.team;

import com.smalaca.taskamanager.domain.team.TeamDomain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SpringDataTeamDomainRepository extends CrudRepository<TeamDomain, Long> {
    boolean existsByName(String name);
}
