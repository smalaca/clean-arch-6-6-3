package com.smalaca.taskamanager.repository;

import com.smalaca.taskamanager.model.entities.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
    @Deprecated
    Optional<Team> findByName(String name);
}
