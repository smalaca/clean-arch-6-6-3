package com.smalaca.taskamanager.infrastructure.persistence.user;

import com.smalaca.taskamanager.domain.user.UserDomain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SpringDataUserDomainRepository extends CrudRepository<UserDomain, Long> {
}
