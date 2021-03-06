package org.ups.m2dl.moneyetdystopieback.services.user_service_integration_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceFindByMethodsIntegrationTest {

    @Autowired
    private UserService userService;

    private User userTest;

    private String password =
        "$2a$10$le2GJFsFwf.E1.EdNzGd/edrKUHWYFxsXRJJGZOrJgWMS/WAtthD2";

    @Test
    void whenCreateSaveUser_thenCanFind() throws BusinessException {
        // GIVEN
        userTest =
            new User(
                "lastName58",
                "firstName58",
                "email58@gmail",
                password,
                null,
                null,
                null
            );

        // WHEN
        userService.save(userTest);
        User resultFindBy = userService.findByEmail(userTest.getEmail());

        // THEN
        Assertions.assertNotNull(resultFindBy, "The saved user was not found.");
    }

    @Test
    void whenSearchUserDontExist_thenNoResult() {
        // GIVEN WHEN
        User resultFindBy = userService.findByEmail("email30@email.com");

        Assertions.assertNull(
            resultFindBy,
            "A user was returned despite his non-existence."
        );
    }
}
