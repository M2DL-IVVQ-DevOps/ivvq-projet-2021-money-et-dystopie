package org.ups.m2dl.moneyetdystopieback.services.userserviceintegrationtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceCreateMethodIntegrationTest {

    @Autowired
    UserService userService;

    User userTestTwinA;
    User userTestTwinB;
    User userTest;

    @Test
    void whenCreateUserWithSameEmail_thenThrowBusinessException() throws BusinessException {

        String sameMail = "email24@email.email";

        // GIVEN
        userTestTwinA = new User("lastName25", "firstName25", sameMail, "Passwordpassword25");
        userTestTwinA.setSellerAccount(new Seller("storeName25"));
        userTestTwinA.setCustomerAccount(new Customer("pseudo25", "numberCityCountry25"));

        userTestTwinB = new User("lastName26", "firstName26", sameMail, "Passwordpassword26");
        userTestTwinB.setSellerAccount(new Seller("storeName26"));
        userTestTwinB.setCustomerAccount(new Customer("pseudo26", "numberCityCountry26"));

        // WHEN
        userService.create(userTestTwinA);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            userService.create(userTestTwinB);
        });
    }

    @Test
    void whenCreateUserWithoutCustomerSeller_thenThrowBusinessException() {
        // GIVEN
        userTest = new User("lastName27", "firstName27", "email27@email.email", "passwordpassword27");

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            userService.create(userTest);
        });
    }

    @Test
    void whenCreateUserWithoutEmail_thenThrowBusinessException() {
        // GIVEN
        userTest = new User("lastName28", "firstName28", null, "passwordpassword28");

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            userService.create(userTest);
        });
    }
}
