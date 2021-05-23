package org.ups.m2dl.moneyetdystopieback.controllers.controllers_integration_test;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.ups.m2dl.moneyetdystopieback.bean.CommandBean;
import org.ups.m2dl.moneyetdystopieback.domain.*;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.CommandService;
import org.ups.m2dl.moneyetdystopieback.services.ItemService;
import org.ups.m2dl.moneyetdystopieback.services.SellerService;
import org.ups.m2dl.moneyetdystopieback.services.UserService;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class CommandControllerCreateIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ItemService itemService;

    private User userTest;
    private ItemCommand itemCommandTest;
    private Item itemTest;

    private String jsonResult;
    private ObjectMapper mapper;

    private final MediaType contentType = new MediaType(
        MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype()
    );

    private static final String VALID_CARD_NUMBER = "9999999999999995";

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
    }

    @BeforeEach
    public void createBeans() throws BusinessException {
        userTest =
            new User(
                "lastName",
                "firstName",
                "buyer@email.fr",
                "Password1!",
                null,
                new Customer("acheteur", "adresserueville", null, null, null),
                new ArrayList<>()
            );

        User userSellerTest = new User(
            "lastName",
            "firstName",
            "seller@email.fr",
            "Password1!",
            new Seller("storeName54", null, null, null),
            null,
            new ArrayList<>()
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
                userSellerTest.getSellerAccount()
            );

        userService.create(userTest);
        userService.create(userSellerTest);
        itemTest = itemService.create(itemTest);
    }

    @Test
    void whenCreateCommand_thenCommandReturn() throws Exception {
        // GIVEN
        itemCommandTest = new ItemCommand(null, 4, itemTest);
        String jsonInput = "{\n" +
                "\"customer\": { \"pseudo\": \"" +
                userTest.getCustomerAccount().getPseudo() +
                "\"}," +
                "\"itemCommands\": [{" +
                "\"amount\": " +
                itemCommandTest.getAmount() +
                "," +
                "\"item\": {\"id\": " +
                itemCommandTest.getItem().getId() +
                "}" +
                "}]}";

        // WHEN
        mockMvc
            .perform(
                post("/command/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonInput)
                    .queryParam("cardNumber", VALID_CARD_NUMBER)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andDo(
                mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                }
            );

        CommandBean resultFromJson = mapper.readValue(
            jsonResult,
            CommandBean.class
        );
        Command result = CommandService.getDto(resultFromJson);

        Seller sellerTest = sellerService.findByStoreName(
            itemTest.getSellerAccount().getStoreName()
        );

        // THEN
        assertAll(
            "The returned item does not comply.",
            () ->
                assertEquals(
                    userTest.getCustomerAccount().getPseudo(),
                    result.getCustomer().getPseudo()
                ),
            () ->
                assertEquals(
                    itemTest.getAmount() - itemCommandTest.getAmount(),
                    itemService
                        .findById(itemCommandTest.getItem().getId())
                        .getAmount()
                ),
            () -> assertEquals(1, sellerTest.getCommands().size())
        );
        assertNotNull(result.getId(), "The returned item does not have ID.");
    }
}
