package com.smalaca.taskamanager.domain.productowner;

public class ProductOwnerException extends RuntimeException {
    private ProductOwnerException(String firstName, String lastName) {
        super("Product Owner " + firstName + " " + lastName + " already exists.");
    }

    public static RuntimeException productOwnerAlreadyExists(String firstName, String lastName) {
        return new ProductOwnerException(firstName, lastName);
    }
}
