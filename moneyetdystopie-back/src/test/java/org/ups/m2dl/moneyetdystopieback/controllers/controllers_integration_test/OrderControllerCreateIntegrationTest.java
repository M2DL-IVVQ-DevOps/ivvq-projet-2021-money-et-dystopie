package org.ups.m2dl.moneyetdystopieback.controllers.controllers_integration_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.ups.m2dl.moneyetdystopieback.bean.CommandBean;
import org.ups.m2dl.moneyetdystopieback.domain.*;
import org.ups.m2dl.moneyetdystopieback.enums.CommandState;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.CustomerRepository;
import org.ups.m2dl.moneyetdystopieback.repositories.OrderRepository;
import org.ups.m2dl.moneyetdystopieback.repositories.SellerRepository;
import org.ups.m2dl.moneyetdystopieback.repositories.UserRepository;
import org.ups.m2dl.moneyetdystopieback.services.OrderService;
import org.ups.m2dl.moneyetdystopieback.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerCreateIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

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

    private Item itemTest;
    private Command orderTest;

    private Customer customer;
    private Seller seller;

    private Command order;

    private String jsonInput;
    private String jsonResult;
    private ObjectMapper mapper;

    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype()
    );

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
    }

    @BeforeEach
    public void createBeans() throws BusinessException {
        customer = new Customer("acheteur", "adresserueville", null, null, null);
        User buyerAccount = new User("lastName", "firstName", "buyer@email.fr", "Password1!", null, customer, new ArrayList<>());
        seller = new Seller("storeName", null, new ArrayList<>(), new ArrayList<>());
        User sellerAccount = new User("lastName", "firstName", "seller@email.fr", "Password1!", seller, null, new ArrayList<>());
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
    void whenCreateOrder_thenOrderReturn() throws Exception {
        // GIVEN
        itemTest =
                new Item(
                        null,
                        "title39",
                        "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
                        "description39",
                        10,
                        5.f,
                        null,
                        seller
                );
        order = new Command(null, CommandState.WAITING_FOR_PAYMENT, customer, List.of(itemTest));

        jsonInput = new Gson().toJson(orderService.getBean(order));

        // WHEN
        mockMvc
                .perform(
                        post("/order/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonInput)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andDo(
                        mvcResult -> {
                            jsonResult = mvcResult.getResponse().getContentAsString();
                        }
                );

        CommandBean resultFromJson = mapper.readValue(jsonResult, CommandBean.class);
        Command result = orderService.getDto(resultFromJson);

        // THEN
        assertAll("The returned item does not comply.",
                () -> assertEquals(
                        order.getState(),
                        result.getState()),
                () -> assertEquals(
                        order.getCustomer().getPseudo(),
                        result.getCustomer().getPseudo()),
                () -> assertEquals(
                        order.getItems().size(),
                        result.getItems().size())
        );
        assertNotNull(result.getId(), "The returned item does not have ID.");
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "title47,description47,1,2.f,false,",
            }
    )
    void whenCreateInvalidOrder_thenErrorReturned() {}
}

