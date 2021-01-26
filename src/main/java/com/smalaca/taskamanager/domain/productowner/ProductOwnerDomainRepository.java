package com.smalaca.taskamanager.domain.productowner;

import com.smalaca.taskamanager.model.entities.ProductOwner;

public interface ProductOwnerDomainRepository {
    boolean doesNotExistByFirstAndLastName(String firstName, String lastName);

    Long save(ProductOwner productOwner);
}
