package org.ups.m2dl.moneyetdystopieback.services.seller_service_integration_test;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.ups.m2dl.moneyetdystopieback.domain.*;
import org.ups.m2dl.moneyetdystopieback.enums.CommandState;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SellerServiceGetAllCommandsMethodIntegrationTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private CommandService commandService;

    @Autowired
    private ItemCommandService itemCommandService;

    private User userTest;
    private Seller sellerTest;
    private Customer customerTest;
    private Token tokenTest;

    private static final String EXPECTED_CARD_NUMBER = "9999999999999995";

    @BeforeEach
    void initBeans() throws BusinessException {
        sellerTest = new Seller("storeName", null, null, new ArrayList<>());
        customerTest =
            new Customer(
                "pseudo",
                "adresse, rue, ville",
                null,
                new Command(),
                new ArrayList<>()
            );
        userTest =
            new User(
                "lastName1",
                "firstName1",
                "email1@email.com",
                "Password1",
                sellerTest,
                customerTest,
                new ArrayList<>()
            );
        userTest = userService.create(userTest);
        sellerTest = userTest.getSellerAccount();
        customerTest = userTest.getCustomerAccount();
        tokenTest = tokenService.createNewTokenForUser(userTest);
        tokenService.saveToken(tokenTest);
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 3 })
    void givenConnectedUser_whenGetAllCommands_thenAllCommandsAreReturned(
        int amount
    ) throws BusinessException {
        // GIVEN
        for (int i = 0; i < amount; i++) {
            Item itemTest = new Item(
                null,
                "title" + i,
                "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
                "description" + i,
                10 + amount,
                5.f,
                null,
                sellerTest
            );
            itemTest = itemService.create(itemTest, userTest);
            ItemCommand itemCommand = itemCommandService.create(
                new ItemCommand(null, 1, itemTest)
            );
            Command command = new Command(
                null,
                CommandState.WAITING_FOR_SHIPMENT,
                customerTest,
                List.of(itemCommand)
            );
            commandService.create(command, EXPECTED_CARD_NUMBER, userTest);
        }

        // WHEN
        userTest = userService.findByEmail(userTest.getEmail());
        List<Command> actual = sellerService.getAllCommands(userTest);

        // THEN
        Assertions.assertNotNull(actual);

        // THEN
        Assertions.assertEquals(
            amount,
            actual.size(),
            "All the expected items have not been retrieved."
        );
    }
}
