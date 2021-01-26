package com.smalaca.taskamanager.application.productowner;

import com.smalaca.taskamanager.domain.productowner.NewProductOwnerDto;
import com.smalaca.taskamanager.domain.productowner.ProductOwnerFactory;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.repository.ProductOwnerRepository;

public class ProductOwnerApplicationService {
    private final ProductOwnerRepository productOwnerRepository;
    private final ProductOwnerFactory factory;

    ProductOwnerApplicationService(ProductOwnerRepository productOwnerRepository, ProductOwnerFactory factory) {
        this.productOwnerRepository = productOwnerRepository;
        this.factory = factory;
    }

    public Long create(NewProductOwnerDto dto) {
        ProductOwner productOwner = factory.create(dto);
        ProductOwner saved = productOwnerRepository.save(productOwner);

        return saved.getId();
    }
}
