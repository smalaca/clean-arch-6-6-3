package com.smalaca.taskamanager.infrastructure.persistence.user;

import com.smalaca.taskamanager.anticorruptionlayer.TaskManagerAntiCorruptionLayer;
import com.smalaca.taskamanager.domain.user.UserDomain;
import com.smalaca.taskamanager.domain.user.UserDomainDto;
import com.smalaca.taskamanager.domain.user.UserDomainTestFactory;
import com.smalaca.taskamanager.repository.UserRepository;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JpaUserDomainRepositoryIntegrationTest {
    private static final String FIRST_NAME = "Steve";
    private static final String LAST_NAME = "Rogers";
    private static final String LOGIN = "captain-america";
    private static final String PASSWORD = "Avengers Assemble";
    private static final String TEAM_ROLE = "DEVELOPER";
    private static final String EMAIL_ADDRESS = "steve.rogers@avengers.com";
    private static final String PHONE_PREFIX = "123";
    private static final String PHONE_NUMBER = "1234567";

    @Autowired private TaskManagerAntiCorruptionLayer antiCorruptionLayer;
    @Autowired private UserRepository userRepository;

    private Long id;

    @AfterEach
    void deleteUser() {
        if (id != null) {
            userRepository.deleteById(id);
        }
    }

    @Test
    void shouldRecognizeUserWithGivenFirstAndLastNameDoesNotExist() {
        boolean actual = antiCorruptionLayer.doesUserNotExistsByFirstAndLastName(FIRST_NAME, LAST_NAME);

        assertThat(actual).isEqualTo(true);
    }

    @Test
    void shouldRecognizeUserWithGivenFirstAndLastNameExists() {
        givenExistingUser();

        boolean actual = antiCorruptionLayer.doesUserNotExistsByFirstAndLastName(FIRST_NAME, LAST_NAME);

        assertThat(actual).isEqualTo(false);
    }

    @Test
    void shouldRecognizeUserWithGivenIdDoesNotExist() {
        boolean actual = antiCorruptionLayer.existsUserById(RandomUtils.nextLong());

        assertThat(actual).isFalse();
    }

    @Test
    void shouldRecognizeUserWithGivenIdExists() {
        givenExistingUser();

        boolean actual = antiCorruptionLayer.existsUserById(id);

        assertThat(actual).isTrue();
    }

    private void givenExistingUser() {
        save(UserDomainTestFactory.create(LOGIN, PASSWORD, FIRST_NAME, LAST_NAME, TEAM_ROLE));
    }

    @Test
    void shouldFindUserById() {
        givenExistingUserWithContactDetails();

        UserDomainDto user = antiCorruptionLayer.findUserById(id).asDto();

        assertThat(user.getLogin()).isEqualTo(LOGIN);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
        assertThat(user.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(user.getLastName()).isEqualTo(LAST_NAME);
        assertThat(user.getTeamRole()).isEqualTo(TEAM_ROLE);
        assertThat(user.getEmailAddress()).isEqualTo(EMAIL_ADDRESS);
        assertThat(user.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
        assertThat(user.getPhonePrefix()).isEqualTo(PHONE_PREFIX);
    }

    private void givenExistingUserWithContactDetails() {
        save(UserDomainTestFactory.create(LOGIN, PASSWORD, FIRST_NAME, LAST_NAME, TEAM_ROLE, EMAIL_ADDRESS, PHONE_PREFIX, PHONE_NUMBER));
    }

    private void save(UserDomain user) {
        id = antiCorruptionLayer.saveUser(user);
    }
}