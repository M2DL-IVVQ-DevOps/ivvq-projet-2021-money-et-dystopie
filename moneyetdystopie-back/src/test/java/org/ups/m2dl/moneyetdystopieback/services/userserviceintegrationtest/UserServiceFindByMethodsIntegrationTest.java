package org.ups.m2dl.moneyetdystopieback.services.userserviceintegrationtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.UserService;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceFindByMethodsIntegrationTest {

    @Autowired
    UserService userService;

    User userTest;

    @Test
    void whenCreateSaveUser_thenCanFind() throws BusinessException {

        // GIVEN
        userTest = new User("lastName29", "firstName29", "email29@gmail", "Passwordpassword29");

        // WHEN
        userService.save(userTest);
        User resultFindBy = userService.findByEmail(userTest.getEmail());

        // THEN
        Assertions.assertNotNull( resultFindBy, "The saved user was not found.");
    }

    @Test
    void whenSearchUserDontExist_thenNoResult() {
        // GIVEN WHEN
        User resultFindBy = userService.findByEmail("email30@email.com");

        Assertions.assertNotNull( resultFindBy, "A user was returned despite his non-existence.");
    }
}
