package com.smalaca.taskamanager.domain.productowner;

import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.repository.ProductOwnerRepository;

public class ProductOwnerFactory {
    private final ProductOwnerRepository productOwnerRepository;

    public ProductOwnerFactory(ProductOwnerRepository productOwnerRepository) {
        this.productOwnerRepository = productOwnerRepository;
    }

    public ProductOwner create(NewProductOwnerDto dto) {
        if (productOwnerRepository.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName()).isEmpty()) {
            ProductOwner productOwner = new ProductOwner();
            productOwner.setFirstName(dto.getFirstName());
            productOwner.setLastName(dto.getLastName());

            return productOwner;
        } else {
            throw ProductOwnerException.productOwnerAlreadyExists(dto.getFirstName(), dto.getLastName());
        }
    }
}
