package org.ups.m2dl.moneyetdystopieback.services.command_service_integration_test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.ups.m2dl.moneyetdystopieback.domain.*;
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
    private ItemService itemService;

    private Command commandTest;


    private Item itemTest;

    private ItemCommand itemCommandTest;

    private Customer customerTest;

    @BeforeEach
    void setup() throws BusinessException {
        customerTest = new Customer("acheteur", "adresserueville", null, null, null);

        Seller sellerTest = new Seller("storeName54", null, null, null);

        itemTest = new Item(
                null,
                "title1",
                "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
                "description54",
                10,
                5.f,
                null,
                sellerTest
        );

        customerService.create(customerTest);
        sellerService.create(sellerTest);
        itemService.create(itemTest);
    }

    @Test
    void whenEmptyCommandIsSubmitted_thenExceptionIsThrown() {
        // Given : Une commande avec aucun article
        commandTest =
            new Command(
                null,
                null,
                    customerTest,
                new ArrayList<>()
            );
        // Then : une exception est levée
        Assertions.assertThrows(
            BusinessException.class,
            // When : Le service enregistre la commande
            () -> commandService.create(commandTest)
        );
    }

    @Test
    void whenCommandWithAmountZeroIsSubmitted_thenExceptionIsThrown() {
        // Given : Une commande avec un article de quantité 0
        itemCommandTest = new ItemCommand(
            null,
            0,
                itemTest
        );
        commandTest =
            new Command(
                null,
                null,
                    customerTest,
                List.of(itemCommandTest)
            );
        Assertions.assertThrows(
            BusinessException.class,
            // When : Le service enregistre la commande
            () -> commandService.create(commandTest)
        );
    }

    @Test
    void whenValidCommandIsSubmitted_thenCommandIsAdded() throws BusinessException {
        // Given : Un article valide
        itemCommandTest = new ItemCommand(
                null,
                5,
                itemTest
        );
        // Given : Une commande dans un état non autorisé
        commandTest =
            new Command(
                null,
                null,
                    customerTest,
                List.of(itemCommandTest)
            );
        // When : Le service enregistre la commande
        Command actual = commandService.create(commandTest);
        // Then : Le repository référence la commande
        Assertions.assertTrue(commandRepository.existsById(actual.getId()));
        // Then : La commande est associée à l'utilisateur
        Assertions.assertFalse(customerService.findByPseudo(customerTest.getPseudo()).getPastCommands().isEmpty());
    }
}
