package com.smalaca.taskamanager.application.productowner;

import com.smalaca.taskamanager.domain.productowner.ProductOwnerFactory;
import com.smalaca.taskamanager.repository.ProductOwnerRepository;

public class ProductOwnerApplicationServiceFactory {
    public ProductOwnerApplicationService productOwnerApplicationService(ProductOwnerRepository productOwnerRepository) {
        return new ProductOwnerApplicationService(productOwnerRepository, new ProductOwnerFactory(productOwnerRepository));
    }
}
