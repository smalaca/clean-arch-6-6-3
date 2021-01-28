package com.smalaca.taskamanager.infrastructure.persistence.user;

import com.smalaca.taskamanager.anticorruptionlayer.TaskManagerAntiCorruptionLayer;
import com.smalaca.taskamanager.domain.user.UserDomain;
import com.smalaca.taskamanager.domain.user.UserDomainDto;
import com.smalaca.taskamanager.domain.user.UserDomainTestFactory;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.repository.UserRepository;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

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
        Optional<User> verification = userRepository.findByUserNameFirstNameAndUserNameLastName(FIRST_NAME, LAST_NAME);

        assertThat(actual).isTrue();
        assertThat(verification).isEmpty();
    }

    @Test
    void shouldRecognizeUserWithGivenFirstAndLastNameExists() {
        givenExistingUser();

        boolean actual = antiCorruptionLayer.doesUserNotExistsByFirstAndLastName(FIRST_NAME, LAST_NAME);
        User verification = userRepository.findByUserNameFirstNameAndUserNameLastName(FIRST_NAME, LAST_NAME).get();

        assertThat(actual).isFalse();
        assertThat(verification.getUserName().getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(verification.getUserName().getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    void shouldRecognizeUserWithGivenIdDoesNotExist() {
        long id = RandomUtils.nextLong();

        boolean actual = antiCorruptionLayer.existsUserById(id);
        Optional<User> verification = userRepository.findById(id);

        assertThat(actual).isFalse();
        assertThat(verification).isEmpty();
    }

    @Test
    void shouldRecognizeUserWithGivenIdExists() {
        givenExistingUser();

        boolean actual = antiCorruptionLayer.existsUserById(id);
        User verification = userRepository.findById(id).get();

        assertThat(actual).isTrue();
        assertThat(verification.getUserName().getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(verification.getUserName().getLastName()).isEqualTo(LAST_NAME);
    }

    private void givenExistingUser() {
        save(UserDomainTestFactory.create(LOGIN, PASSWORD, FIRST_NAME, LAST_NAME, TEAM_ROLE));
    }

    @Test
    void shouldFindUserById() {
        givenExistingUserWithContactDetails();

        UserDomainDto actual = antiCorruptionLayer.findUserById(id).asDto();
        User verification = userRepository.findById(id).get();

        assertThat(actual.getLogin()).isEqualTo(LOGIN);
        assertThat(actual.getPassword()).isEqualTo(PASSWORD);
        assertThat(actual.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(actual.getLastName()).isEqualTo(LAST_NAME);
        assertThat(actual.getTeamRole()).isEqualTo(TEAM_ROLE);
        assertThat(actual.getEmailAddress()).isEqualTo(EMAIL_ADDRESS);
        assertThat(actual.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
        assertThat(actual.getPhonePrefix()).isEqualTo(PHONE_PREFIX);

        assertThat(verification.getLogin()).isEqualTo(LOGIN);
        assertThat(verification.getPassword()).isEqualTo(PASSWORD);
        assertThat(verification.getUserName().getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(verification.getUserName().getLastName()).isEqualTo(LAST_NAME);
        assertThat(verification.getTeamRole().name()).isEqualTo(TEAM_ROLE);
        assertThat(verification.getEmailAddress().getEmailAddress()).isEqualTo(EMAIL_ADDRESS);
        assertThat(verification.getPhoneNumber().getNumber()).isEqualTo(PHONE_NUMBER);
        assertThat(verification.getPhoneNumber().getPrefix()).isEqualTo(PHONE_PREFIX);
    }

    private void givenExistingUserWithContactDetails() {
        save(UserDomainTestFactory.create(LOGIN, PASSWORD, FIRST_NAME, LAST_NAME, TEAM_ROLE, EMAIL_ADDRESS, PHONE_PREFIX, PHONE_NUMBER));
    }

    private void save(UserDomain user) {
        id = antiCorruptionLayer.saveUser(user);
    }
}