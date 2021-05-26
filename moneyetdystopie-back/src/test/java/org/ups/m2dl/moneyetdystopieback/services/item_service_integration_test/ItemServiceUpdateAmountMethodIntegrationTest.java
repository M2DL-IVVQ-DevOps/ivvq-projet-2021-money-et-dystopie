package org.ups.m2dl.moneyetdystopieback.services.item_service_integration_test;

import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
import org.ups.m2dl.moneyetdystopieback.services.TokenService;
import org.ups.m2dl.moneyetdystopieback.services.UserService;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class ItemServiceUpdateAmountMethodIntegrationTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    private Item itemTest;

    private Seller sellerTest;
    private Token tokenTest;

    @BeforeEach
    void initBeans() throws BusinessException {
        sellerTest = new Seller("storeName", null, null, null);
        User userTest = new User(
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

        itemTest =
            new Item(
                null,
                "title",
                "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
                "description",
                10,
                5.f,
                null,
                sellerTest
            );
        itemTest = itemService.create(itemTest, tokenTest.getUser());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 5, Integer.MAX_VALUE })
    void givenConnectedUser_whenUpdateItemAmount_thenItemIsUpdated(int amount)
        throws BusinessException {
        // WHEN
        Item itemToUpdate = new Item(
            itemTest.getId(),
            null,
            null,
            null,
            amount,
            null,
            null,
            sellerTest
        );
        itemService.updateAmount(itemToUpdate, tokenTest.getUser());

        // THEN
        Assertions.assertEquals(
            amount,
            itemService.findById(itemToUpdate.getId()).getAmount(),
            "The amount has not been updated."
        );
    }

    @Test
    void givenConnectedUser_whenUpdateItemAmountWithWrongAmount_thenItemExceptionIsThrown() {
        // WHEN
        Item itemToUpdate = new Item(
            itemTest.getId(),
            null,
            null,
            null,
            -1,
            null,
            null,
            sellerTest
        );

        // THEN
        Assertions.assertThrows(
            Exception.class,
            () -> itemService.updateAmount(itemToUpdate, tokenTest.getUser()),
            "No exception has been thrown when an incorrect amount has been entered."
        );

        // THEN
        Assertions.assertEquals(
            10,
            itemService.findById(itemToUpdate.getId()).getAmount(),
            "The amount has been updated (but shouldn't)."
        );
    }
}
