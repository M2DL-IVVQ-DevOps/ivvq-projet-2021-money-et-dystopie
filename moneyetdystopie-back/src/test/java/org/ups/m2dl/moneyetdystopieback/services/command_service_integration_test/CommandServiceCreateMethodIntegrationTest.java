package org.ups.m2dl.moneyetdystopieback.services.command_service_integration_test;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.ups.m2dl.moneyetdystopieback.domain.*;
import org.ups.m2dl.moneyetdystopieback.enums.CommandState;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.*;
import org.ups.m2dl.moneyetdystopieback.services.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class CommandServiceCreateMethodIntegrationTest {

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CommandService commandService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    private Command commandTest;

    private static final String VALID_CARD_NUMBER = "9999999999999995";

    private Item itemTest;
    private ItemCommand itemCommandTest;
    private Customer customerTest;
    private Token tokenTest;
    private Token tokenCustomerTest;
    private User userTest;
    private User userCustomerTest;

    @BeforeEach
    void setup() throws BusinessException {
        customerTest =
            new Customer("acheteur", "adresserueville", null, null, null);
        userCustomerTest =
            new User(
                "nom2",
                "prenom2",
                "mail2@mail.net",
                "Password2",
                null,
                customerTest,
                null
            );

        userService.create(userCustomerTest);
        tokenCustomerTest =
            tokenService.createNewTokenForUser(userCustomerTest);
        tokenService.saveToken(tokenCustomerTest);

        Seller sellerTest = new Seller("storeName54", null, null, null);
        userTest =
            new User(
                "nom",
                "prenom",
                "mail@mail.net",
                "Password1",
                sellerTest,
                null,
                null
            );

        itemTest =
            new Item(
                null,
                "title1",
                "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
                "description54",
                10,
                5.f,
                null,
                sellerTest
            );

        userService.create(userTest);
        tokenTest = tokenService.createNewTokenForUser(userTest);
        tokenService.saveToken(tokenTest);

        // customerService.create(customerTest);
        itemService.create(itemTest, tokenTest.getUser());
    }

    @Test
    void whenEmptyCommandIsSubmitted_thenExceptionIsThrown() {
        // Given : Une commande avec aucun article
        commandTest = new Command(null, null, customerTest, new ArrayList<>());
        // Then : une exception est levée
        Assertions.assertThrows(
            BusinessException.class,
            // When : Le service enregistre la commande
            () ->
                commandService.create(
                    commandTest,
                    VALID_CARD_NUMBER,
                    userCustomerTest
                )
        );
    }

    @Test
    void whenCommandWithAmountZeroIsSubmitted_thenExceptionIsThrown() {
        // Given : Une commande avec un article de quantité 0
        itemCommandTest = new ItemCommand(null, 0, itemTest);
        commandTest =
            new Command(null, null, customerTest, List.of(itemCommandTest));
        Assertions.assertThrows(
            BusinessException.class,
            // When : Le service enregistre la commande
            () ->
                commandService.create(
                    commandTest,
                    VALID_CARD_NUMBER,
                    userCustomerTest
                )
        );
    }

    @Test
    void whenValidCommandIsSubmitted_thenCommandIsAdded()
        throws BusinessException {
        // Given : Un article valide
        itemCommandTest = new ItemCommand(null, 5, itemTest);
        // Given : Une commande dans un état autorisé
        commandTest =
            new Command(
                null,
                CommandState.WAITING_FOR_PAYMENT,
                customerTest,
                List.of(itemCommandTest)
            );
        // When : Le service enregistre la commande
        Command actual = commandService.create(
            commandTest,
            VALID_CARD_NUMBER,
            userCustomerTest
        );
        // Then : Le repository référence la commande
        Assertions.assertTrue(commandRepository.existsById(actual.getId()));
        // Then : La commande est associée à l'utilisateur
        Assertions.assertFalse(
            customerService
                .findByPseudo(customerTest.getPseudo())
                .getPastCommands()
                .isEmpty()
        );
    }
}
