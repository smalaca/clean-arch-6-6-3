package com.smalaca.taskamanager.application.productowner;

import com.smalaca.taskamanager.domain.productowner.NewProductOwnerDto;
import com.smalaca.taskamanager.domain.productowner.ProductOwnerDomainRepository;
import com.smalaca.taskamanager.domain.productowner.ProductOwnerFactory;
import com.smalaca.taskamanager.model.entities.ProductOwner;

public class ProductOwnerApplicationService {
    private final ProductOwnerDomainRepository productOwnerRepository;
    private final ProductOwnerFactory factory;

    ProductOwnerApplicationService(ProductOwnerDomainRepository productOwnerRepository, ProductOwnerFactory factory) {
        this.productOwnerRepository = productOwnerRepository;
        this.factory = factory;
    }

    public Long create(NewProductOwnerDto dto) {
        ProductOwner productOwner = factory.create(dto);

        return productOwnerRepository.saveProductOwner(productOwner);
    }
}
