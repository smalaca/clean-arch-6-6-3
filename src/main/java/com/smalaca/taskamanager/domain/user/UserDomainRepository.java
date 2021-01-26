package com.smalaca.taskamanager.domain.user;

import com.smalaca.taskamanager.model.entities.User;

public interface UserDomainRepository {
    Long save(User user);

    boolean doesNotExistsByFirstAndLastName(String firstName, String lastName);
}
