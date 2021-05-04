package org.ups.m2dl.moneyetdystopieback.services.userserviceintegrationtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.UserService;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceSaveMethodIntegrationTest {

    @Autowired
    UserService userService;

    User userTest;

    @Test
    void whenSaveUser_thenCanFind() throws BusinessException {
        // GIVEN
        userTest = new User("lastName31", "firstName31", "email31@gmail", "Passwordpassword31");
        userTest.setCustomerAccount(new Customer("superPseudo","rue de par là bas"));

        // WHEN
        userService.save(userTest);
        User resultFindById = userService.findByEmail(userTest.getEmail());

        //THEN
        Assertions.assertNotNull( resultFindById, "More or less users than expected were obtained.");
        Assertions.assertEquals( userTest.getFirstName(), resultFindById.getFirstName(), "The information retrieved does not match that expected.");
        Assertions.assertEquals( userTest.getPassword(), resultFindById.getPassword(), "The information retrieved does not match that expected.");
        Assertions.assertEquals( userTest.getEmail(), resultFindById.getEmail(), "The information retrieved does not match that expected.");
        Assertions.assertEquals( userTest.getLastName(), resultFindById.getLastName(), "The information retrieved does not match that expected.");
    }

    @Test
    void whenCreateUserNull_thenThrowBusinessException() {
        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            userService.save(null);
        });
    }

    @Test
    void whenSaveUserWithoutEmail_thenThrowBusinessException() {
        // GIVEN
        userTest = new User("lastName32", "firstName32", null, "passwordpassword32");

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            userService.save(userTest);
        });
    }
}
