package com.smalaca.taskamanager.domain.productowner;

import com.smalaca.taskamanager.model.entities.ProductOwner;

public interface ProductOwnerDomainRepository {
    boolean doesProductOwnerNotExistByFirstAndLastName(String firstName, String lastName);

    Long saveProductOwner(ProductOwner productOwner);
}
