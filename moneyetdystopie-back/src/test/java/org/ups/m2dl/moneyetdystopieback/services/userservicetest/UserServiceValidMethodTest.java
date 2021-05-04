package org.ups.m2dl.moneyetdystopieback.services.userservicetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceValidMethodTest {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    User userTest;


    @ParameterizedTest
    @ValueSource(strings = {"","qdsq.fre","0123456789012345678901234567890123@45678901234567890123456789.01234567890123456789012345678901234567890"})
    @NullSource
    void whenValidUserWithBadEmail_thenThrowBusinessException(String email)  {

        // GIVEN
        userTest = new User("lastName", "firstName", email, "password");

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            userService.valid(userTest);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"","0123456789012345678901234567890"})
    @NullSource
    void whenCreateUserWithBadLastName_thenThrowBusinessException(String lastName)  {

        // GIVEN
        userTest = new User(lastName, "firstName", "email", "password");

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // THEN
            userService.valid(userTest);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"","0123456789012345678901234567890"})
    @NullSource
    void whenCreateUserWithBadFirstName_thenThrowBusinessException(String firstName)  {

        // GIVEN
        userTest = new User("lastName", firstName, "email", "password");

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // THEN
            userService.valid(userTest);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"","0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"})
    @NullSource
    void whenCreateUserWithBadPassword_thenThrowBusinessException(String password)  {

        // GIVEN
        userTest = new User("lastName", "firstName", "email", password);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // THEN
            userService.valid(userTest);
        });
    }

    @Test
    void whenCreateUser_thenNoThrowBusinessException() {
        // GIVEN
        userTest = new User("lastName35", "firstName35", "email35@email.email", passwordEncoder.encode("passwordpassword35"));

        //WHEN
        Assertions.assertDoesNotThrow( () -> {
            userService.valid(userTest);
        });
    }
}