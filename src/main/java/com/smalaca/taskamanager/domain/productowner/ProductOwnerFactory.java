package com.smalaca.taskamanager.domain.productowner;

import com.smalaca.taskamanager.model.entities.ProductOwner;

public class ProductOwnerFactory {
    private final ProductOwnerDomainRepository productOwnerRepository;

    public ProductOwnerFactory(ProductOwnerDomainRepository productOwnerRepository) {
        this.productOwnerRepository = productOwnerRepository;
    }

    public ProductOwner create(NewProductOwnerDto dto) {
        if (productOwnerRepository.doesNotExistByFirstAndLastName(dto.getFirstName(), dto.getLastName())) {
            ProductOwner productOwner = new ProductOwner();
            productOwner.setFirstName(dto.getFirstName());
            productOwner.setLastName(dto.getLastName());

            return productOwner;
        } else {
            throw ProductOwnerException.productOwnerAlreadyExists(dto.getFirstName(), dto.getLastName());
        }
    }
}
