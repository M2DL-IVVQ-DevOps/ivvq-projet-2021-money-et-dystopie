package org.ups.m2dl.moneyetdystopieback.services.item_service_integration_test;

import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.ups.m2dl.moneyetdystopieback.domain.Item;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.domain.Token;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.ItemService;
import org.ups.m2dl.moneyetdystopieback.services.SellerService;
import org.ups.m2dl.moneyetdystopieback.services.TokenService;
import org.ups.m2dl.moneyetdystopieback.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ItemServiceCreateMethodIntegrationTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    private Item itemTestTwinA;
    private Item itemTestTwinB;
    private Item itemTest;

    private Seller sellerTest;
    private User userTest;
    private Token tokenTest;

    private int itemsNumber;

    @Test
    void givenConnectedUser_whenCreateItemWithSameDatas_thenItemCreated()
        throws BusinessException {
        // GIVEN
        sellerTest = new Seller("storeName491", null, null, null);
        userTest =
            new User(
                "lastName1",
                "firstName1",
                "email1@email.com",
                "Password1",
                sellerTest,
                null,
                new ArrayList<>()
            );

        userService.create(userTest);
        tokenTest = tokenService.createNewTokenForUser(userTest);
        tokenService.saveToken(tokenTest);

        itemTestTwinA =
            new Item(
                null,
                "title49",
                "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
                "description49",
                10,
                5.f,
                null,
                sellerTest
            );
        itemTestTwinB =
            new Item(
                null,
                "title49",
                "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
                "description49",
                10,
                5.f,
                null,
                sellerTest
            );

        itemsNumber = itemService.findAll().size();

        // WHEN
        itemService.create(itemTestTwinA, tokenTest.getUser());
        itemService.create(itemTestTwinB, tokenTest.getUser());

        // THEN
        Assertions.assertEquals(
            itemsNumber + 2,
            itemService.findAll().size(),
            "Twin items are registered."
        );
    }

    @Test
    void givenNotConnectedUser_whenCreateItemWithSameDatas_thenItemNotCreated()
        throws BusinessException {
        // GIVEN
        sellerTest = new Seller("storeName492", null, null, null);
        userTest =
            new User(
                "lastName2",
                "firstName2",
                "email2@email.com",
                "Password2",
                sellerTest,
                null,
                new ArrayList<>()
            );

        userService.create(userTest);

        itemTestTwinA =
            new Item(
                null,
                "title49",
                "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
                "description49",
                10,
                5.f,
                null,
                sellerTest
            );

        // THEN
        Assertions.assertThrows(
            BusinessException.class,
            () -> {
                // WHEN
                itemService.create(itemTestTwinA, new User());
            }
        );
    }

    @Test
    void whenCreateItemWithoutSeller_thenThrowBusinessException() {
        // GIVEN
        itemTest =
            new Item(
                null,
                "title50",
                "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
                "description50",
                10,
                5.f,
                null,
                null
            );

        // THEN
        Assertions.assertThrows(
            BusinessException.class,
            () -> {
                // WHEN
                itemService.create(itemTest, new User());
            }
        );
    }
}
