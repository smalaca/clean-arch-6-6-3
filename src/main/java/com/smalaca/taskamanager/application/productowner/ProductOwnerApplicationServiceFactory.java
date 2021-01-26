package com.smalaca.taskamanager.application.productowner;

import com.smalaca.taskamanager.repository.ProductOwnerRepository;

public class ProductOwnerApplicationServiceFactory {
    public ProductOwnerApplicationService productOwnerApplicationService(ProductOwnerRepository productOwnerRepository) {
        return new ProductOwnerApplicationService(productOwnerRepository);
    }
}
