package org.ups.m2dl.moneyetdystopieback.services.order_service_integration_test;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.ups.m2dl.moneyetdystopieback.domain.*;
import org.ups.m2dl.moneyetdystopieback.enums.CommandState;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.CustomerRepository;
import org.ups.m2dl.moneyetdystopieback.repositories.OrderRepository;
import org.ups.m2dl.moneyetdystopieback.repositories.SellerRepository;
import org.ups.m2dl.moneyetdystopieback.repositories.UserRepository;
import org.ups.m2dl.moneyetdystopieback.services.OrderService;
import org.ups.m2dl.moneyetdystopieback.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class OrderServiceCreateMethodIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    private Command orderTest;

    private Customer customer;
    private Seller seller;

    @BeforeEach
    public void createBeans() throws BusinessException {
        customer =
            new Customer("acheteur", "adresserueville", null, null, null);
        User buyerAccount = new User(
            "lastName",
            "firstName",
            "buyer@email.fr",
            "Password1!",
            null,
            customer,
            new ArrayList<>()
        );
        seller =
            new Seller("storeName", null, new ArrayList<>(), new ArrayList<>());
        User sellerAccount = new User(
            "lastName",
            "firstName",
            "seller@email.fr",
            "Password1!",
            seller,
            null,
            new ArrayList<>()
        );
        buyerAccount = userService.create(buyerAccount);
        sellerAccount = userService.create(sellerAccount);
        customer = buyerAccount.getCustomerAccount();
        seller = sellerAccount.getSellerAccount();
    }

    @AfterEach
    public void resetRepositories() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
        customerRepository.deleteAll();
        sellerRepository.deleteAll();
    }

    @Test
    void whenEmptyOrderIsSubmitted_thenExceptionIsThrown() {
        // Given : Une commande avec aucun article
        orderTest =
            new Command(
                null,
                CommandState.WAITING_FOR_PAYMENT,
                customer,
                new ArrayList<>()
            );
        // Then : une exception est levée
        Assertions.assertThrows(
            BusinessException.class,
            // When : Le service enregistre la commande
            () -> orderService.create(orderTest)
        );
        // TODO Vérifier le message ?
    }

    @Test
    void whenOrderWithZeroItemsIsSubmitted_thenExceptionIsThrown() {
        // Given : Une commande avec un article de quantité 0
        Item emptyItem = new Item(
            null,
            "title",
            "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
            "description",
            0, // Volontairement à 0
            5.f,
            null,
            null
        );
        orderTest =
            new Command(
                null,
                CommandState.WAITING_FOR_PAYMENT,
                customer,
                List.of(emptyItem)
            );
        Assertions.assertThrows(
            BusinessException.class,
            // When : Le service enregistre la commande
            () -> orderService.create(orderTest)
        );
        // TODO Vérifier le message ?
    }

    @ParameterizedTest
    @EnumSource(
        value = CommandState.class,
        names = {
            "IN_PROGRESS",
            "WAITING_FOR_SHIPMENT",
            "WAITING_FOR_DELIVERY",
            "COMPLETED",
        }
    )
    void whenOrderWithCompletedStatusIsSubmitted_thenExceptionIsThrown(
        CommandState state
    ) {
        // Given : Un article valide
        Item validItem = new Item(
            null,
            "title",
            "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
            "description",
            10,
            5.f,
            null,
            null
        );
        // Given : Une commande dans un état non autorisé
        orderTest = new Command(null, state, customer, List.of(validItem));
        Assertions.assertThrows(
            BusinessException.class,
            // When : Le service enregistre la commande
            () -> orderService.create(orderTest)
        );
        // TODO Vérifier le message ?
    }

    @Test
    void whenValidOrderIsSubmitted_thenOrderIsAdded() throws BusinessException {
        // Given : Un article valide
        Item validItem = new Item(
            null,
            "title",
            "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
            "description",
            10,
            5.f,
            null,
            seller
        );
        // Given : Une commande dans un état non autorisé
        orderTest =
            new Command(
                null,
                CommandState.WAITING_FOR_PAYMENT,
                customer,
                List.of(validItem)
            );
        // When : Le service enregistre la commande
        Command actual = orderService.create(orderTest);
        // Then : Le repository référence la commande
        Assertions.assertNotNull(actual.getId());
        Assertions.assertTrue(orderRepository.existsById(actual.getId()));
        // Then : La commande est associée à l'utilisateur
        Assertions.assertTrue(customer.getPastCommands().contains(actual));
    }
}
