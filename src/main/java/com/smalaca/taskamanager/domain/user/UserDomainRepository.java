package com.smalaca.taskamanager.domain.user;

public interface UserDomainRepository {
    Long saveUser(UserDomain user);

    boolean doesUserNotExistsByFirstAndLastName(String firstName, String lastName);

    boolean existsUserById(Long id);

    UserDomain findUserById(Long id);
}
