package com.smalaca.taskamanager.application.productowner;

import com.smalaca.taskamanager.domain.productowner.NewProductOwnerDto;
import com.smalaca.taskamanager.domain.productowner.ProductOwnerFactory;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.repository.ProductOwnerRepository;

public class ProductOwnerApplicationService {
    private final ProductOwnerRepository productOwnerRepository;

    ProductOwnerApplicationService(ProductOwnerRepository productOwnerRepository) {
        this.productOwnerRepository = productOwnerRepository;
    }

    public Long create(NewProductOwnerDto dto) {
        ProductOwner productOwner = new ProductOwnerFactory(productOwnerRepository).create(dto);
        ProductOwner saved = productOwnerRepository.save(productOwner);

        return saved.getId();
    }
}
