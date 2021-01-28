package com.smalaca.taskamanager.infrastructure.persistence.user;

import com.smalaca.taskamanager.domain.user.UserDomain;
import com.smalaca.taskamanager.domain.user.UserDomainRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaUserDomainRepository implements UserDomainRepository {
    private final SpringDataUserDomainRepository repository;

    JpaUserDomainRepository(SpringDataUserDomainRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long saveUser(UserDomain user) {
        return null;
    }

    @Override
    public boolean doesUserNotExistsByFirstAndLastName(String firstName, String lastName) {
        return false;
    }

    @Override
    public boolean existsUserById(Long id) {
        return false;
    }

    @Override
    public UserDomain findUserById(Long id) {
        return null;
    }
}
