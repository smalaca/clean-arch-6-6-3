package com.smalaca.taskamanager.domain.productowner;

import com.smalaca.taskamanager.model.entities.ProductOwner;

public class ProductOwnerTestFactory {
    public static ProductOwner create(String firstName, String lastName) {
        ProductOwner productOwner = new ProductOwner();
        productOwner.setFirstName(firstName);
        productOwner.setLastName(lastName);
        return productOwner;
    }
}
