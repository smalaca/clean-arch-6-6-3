package com.smalaca.taskamanager.application.productowner;

import com.smalaca.taskamanager.domain.productowner.ProductOwnerException;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.repository.ProductOwnerRepository;

public class ProductOwnerApplicationService {
    private final ProductOwnerRepository productOwnerRepository;

    ProductOwnerApplicationService(ProductOwnerRepository productOwnerRepository) {
        this.productOwnerRepository = productOwnerRepository;
    }

    public Long create(NewProductOwnerDto dto) {
        if (productOwnerRepository.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName()).isEmpty()) {
            ProductOwner productOwner = new ProductOwner();
            productOwner.setFirstName(dto.getFirstName());
            productOwner.setLastName(dto.getLastName());
            ProductOwner saved = productOwnerRepository.save(productOwner);

            return saved.getId();
        } else {
            throw ProductOwnerException.productOwnerAlreadyExists(dto.getFirstName(), dto.getLastName());
        }
    }
}
