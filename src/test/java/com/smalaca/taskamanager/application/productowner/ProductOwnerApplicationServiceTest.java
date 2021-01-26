package com.smalaca.taskamanager.application.productowner;

import com.smalaca.taskamanager.domain.productowner.NewProductOwnerDto;
import com.smalaca.taskamanager.domain.productowner.ProductOwnerDomainRepository;
import com.smalaca.taskamanager.domain.productowner.ProductOwnerException;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class ProductOwnerApplicationServiceTest {
    private static final String FIRST_NAME = "Tony";
    private static final String LAST_NAME = "Stark";
    private static final long PRODUCT_OWNER_ID = 987L;

    private final ProductOwnerDomainRepository repository = mock(ProductOwnerDomainRepository.class);
    private final ProductOwnerApplicationService service = new ProductOwnerApplicationServiceFactory().productOwnerApplicationService(repository);

    @Test
    void shouldCreateProductOwner() {
        ArgumentCaptor<ProductOwner> captor = ArgumentCaptor.forClass(ProductOwner.class);
        givenNonExistingProductOwner();
        givenSavedProductOwner();

        Long id = service.create(givenDto());

        assertThat(id).isEqualTo(PRODUCT_OWNER_ID);
        then(repository).should().saveProductOwner(captor.capture());
        ProductOwner actual = captor.getValue();
        assertThat(actual.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(actual.getLastName()).isEqualTo(LAST_NAME);
    }

    private void givenSavedProductOwner() {
        given(repository.saveProductOwner(any())).willReturn(PRODUCT_OWNER_ID);
    }

    private void givenNonExistingProductOwner() {
        given(repository.doesProductOwnerNotExistByFirstAndLastName(FIRST_NAME, LAST_NAME)).willReturn(true);
    }

    @Test
    void shouldRecognizeProductOwnerCannotBeCreated() {
        givenExistingProductOwner();

        ProductOwnerException actual = assertThrows(ProductOwnerException.class, () -> service.create(givenDto()));

        assertThat(actual).hasMessage("Product Owner Tony Stark already exists.");
    }

    private void givenExistingProductOwner() {
        given(repository.doesProductOwnerNotExistByFirstAndLastName(FIRST_NAME, LAST_NAME)).willReturn(false);
    }

    private NewProductOwnerDto givenDto() {
        return new NewProductOwnerDto(FIRST_NAME, LAST_NAME);
    }
}