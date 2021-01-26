package com.smalaca.taskamanager.domain.user;

import com.smalaca.taskamanager.model.entities.User;

public interface UserDomainRepository {
    Long saveUser(User user);

    boolean doesUserNotExistsByFirstAndLastName(String firstName, String lastName);

    boolean existsUserById(Long id);

    User findUserById(Long id);
}
