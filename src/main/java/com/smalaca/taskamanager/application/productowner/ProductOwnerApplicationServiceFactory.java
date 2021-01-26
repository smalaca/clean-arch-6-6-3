package com.smalaca.taskamanager.application.productowner;

import com.smalaca.taskamanager.domain.productowner.ProductOwnerDomainRepository;
import com.smalaca.taskamanager.domain.productowner.ProductOwnerFactory;

public class ProductOwnerApplicationServiceFactory {
    public ProductOwnerApplicationService productOwnerApplicationService(ProductOwnerDomainRepository productOwnerRepository) {
        return new ProductOwnerApplicationService(productOwnerRepository, new ProductOwnerFactory(productOwnerRepository));
    }
}
