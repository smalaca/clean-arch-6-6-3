package com.smalaca.taskamanager.infrastructure.persistence.user;

import com.smalaca.taskamanager.domain.user.UserDomain;
import com.smalaca.taskamanager.domain.user.UserDomainRepository;
import org.springframework.stereotype.Repository;

@Repository
class JpaUserDomainRepository implements UserDomainRepository {
    private final SpringDataUserDomainRepository repository;

    JpaUserDomainRepository(SpringDataUserDomainRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long saveUser(UserDomain user) {
        return repository.save(user).getId();
    }

    @Override
    public boolean doesUserNotExistsByFirstAndLastName(String firstName, String lastName) {
        return !repository.existsByUserNameFirstNameAndUserNameLastName(firstName, lastName);
    }

    @Override
    public boolean existsUserById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public UserDomain findUserById(Long id) {
        return repository.findById(id).get();
    }
}
