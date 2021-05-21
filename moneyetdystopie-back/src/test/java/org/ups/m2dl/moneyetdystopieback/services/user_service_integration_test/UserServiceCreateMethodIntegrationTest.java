package org.ups.m2dl.moneyetdystopieback.services.user_service_integration_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.UserService;

@SpringBootTest
class UserServiceCreateMethodIntegrationTest {

    @Autowired
    private UserService userService;

    private User userTestTwinA;
    private User userTestTwinB;
    private User userTest;

    @Test
    void whenCreateUserWithSameEmail_thenThrowBusinessException()
        throws BusinessException {
        String sameMail = "email57@email.email";

        // GIVEN
        userTestTwinA =
            new User(
                "lastName25",
                "firstName25",
                sameMail,
                "Passwordpassword25",
                null,
                null,
                null
            );
        userTestTwinA.setSellerAccount(
            new Seller("storeName25", null, null, null)
        );
        userTestTwinA.setCustomerAccount(
            new Customer("pseudo25", "numberCityCountry25", null, null, null)
        );

        userTestTwinB =
            new User(
                "lastName26",
                "firstName26",
                sameMail,
                "Passwordpassword26",
                null,
                null,
                null
            );
        userTestTwinB.setSellerAccount(
            new Seller("storeName26", null, null, null)
        );
        userTestTwinB.setCustomerAccount(
            new Customer("pseudo26", "numberCityCountry26", null, null, null)
        );

        // WHEN
        userService.create(userTestTwinA);

        // THEN
        Assertions.assertThrows(
            BusinessException.class,
            () -> {
                // WHEN
                userService.create(userTestTwinB);
            }
        );
    }

    @Test
    void whenCreateUserWithoutCustomerSeller_thenThrowBusinessException() {
        // GIVEN
        userTest =
            new User(
                "lastName27",
                "firstName27",
                "email27@email.email",
                "Passwordpassword27",
                null,
                null,
                null
            );

        // THEN
        Assertions.assertThrows(
            BusinessException.class,
            () -> {
                // WHEN
                userService.create(userTest);
            }
        );
    }

    @Test
    void whenCreateUserWithoutEmail_thenThrowBusinessException() {
        // GIVEN
        userTest =
            new User(
                "lastName28",
                "firstName28",
                null,
                "Passwordpassword28",
                null,
                null,
                null
            );

        // THEN
        Assertions.assertThrows(
            BusinessException.class,
            () -> {
                // WHEN
                userService.create(userTest);
            }
        );
    }
}
