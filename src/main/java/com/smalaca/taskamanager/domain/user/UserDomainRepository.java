package com.smalaca.taskamanager.domain.user;

import com.smalaca.taskamanager.model.entities.User;

public interface UserDomainRepository {
    Long save(User user);

    boolean doesNotExistsByFirstAndLastName(String firstName, String lastName);

    boolean existsById(Long id);

    User findById(Long id);
}
