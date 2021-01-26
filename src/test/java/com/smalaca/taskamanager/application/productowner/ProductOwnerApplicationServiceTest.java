package com.smalaca.taskamanager.application.productowner;

import com.smalaca.taskamanager.anticorruptionlayer.TaskManagerAntiCorruptionLayer;
import com.smalaca.taskamanager.domain.productowner.NewProductOwnerDto;
import com.smalaca.taskamanager.domain.productowner.ProductOwnerDomainRepository;
import com.smalaca.taskamanager.domain.productowner.ProductOwnerException;
import com.smalaca.taskamanager.domain.productowner.ProductOwnerTestFactory;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.repository.ProductOwnerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class ProductOwnerApplicationServiceTest {
    private static final String FIRST_NAME = "Tony";
    private static final String LAST_NAME = "Stark";

    private final ProductOwnerRepository repository = mock(ProductOwnerRepository.class);
    private final ProductOwnerDomainRepository productOwnerDomainRepository = new TaskManagerAntiCorruptionLayer(null, null, repository);
    private final ProductOwnerApplicationService service = new ProductOwnerApplicationServiceFactory().productOwnerApplicationService(productOwnerDomainRepository);

    @Test
    void shouldCreateProductOwner() {
        ArgumentCaptor<ProductOwner> captor = ArgumentCaptor.forClass(ProductOwner.class);
        givenNonExistingProductOwner();
        givenSavedProductOwner();

        Long id = service.create(givenDto());

        assertThat(id).isNull();
        then(repository).should().save(captor.capture());
        ProductOwner actual = captor.getValue();
        assertThat(actual.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(actual.getLastName()).isEqualTo(LAST_NAME);
    }

    private void givenSavedProductOwner() {
        ProductOwner savedProductOwner = ProductOwnerTestFactory.create(FIRST_NAME, LAST_NAME);
        given(repository.save(any())).willReturn(savedProductOwner);
    }

    private void givenNonExistingProductOwner() {
        given(repository.findByFirstNameAndLastName(FIRST_NAME, LAST_NAME)).willReturn(Optional.empty());
    }

    @Test
    void shouldRecognizeProductOwnerCannotBeCreated() {
        givenExistingProductOwner();

        ProductOwnerException actual = assertThrows(ProductOwnerException.class, () -> service.create(givenDto()));

        assertThat(actual).hasMessage("Product Owner Tony Stark already exists.");
    }

    private void givenExistingProductOwner() {
        ProductOwner existing = ProductOwnerTestFactory.create(FIRST_NAME, LAST_NAME);
        given(repository.findByFirstNameAndLastName(FIRST_NAME, LAST_NAME)).willReturn(Optional.of(existing));
    }

    private NewProductOwnerDto givenDto() {
        return new NewProductOwnerDto(FIRST_NAME, LAST_NAME);
    }
}