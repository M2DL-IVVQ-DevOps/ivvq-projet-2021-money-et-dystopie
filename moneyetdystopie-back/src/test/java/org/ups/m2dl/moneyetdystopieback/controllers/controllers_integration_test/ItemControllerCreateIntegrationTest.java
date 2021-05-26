package org.ups.m2dl.moneyetdystopieback.controllers.controllers_integration_test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.ups.m2dl.moneyetdystopieback.bean.ItemBean;
import org.ups.m2dl.moneyetdystopieback.domain.Item;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.domain.Token;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.ItemService;
import org.ups.m2dl.moneyetdystopieback.services.SellerService;
import org.ups.m2dl.moneyetdystopieback.services.TokenService;
import org.ups.m2dl.moneyetdystopieback.services.UserService;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ItemControllerCreateIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemService itemService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    private String jsonResult;
    private Item itemTest;
    private Seller sellerTest, parameterizedSellerTest;
    private User userTest;
    private Token tokenTest;
    private ObjectMapper mapper;
    private String jsonUserTest;
    private int itemsNumber;

    private MediaType contentType = new MediaType(
        MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype()
    );
    private Cookie cookie;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
    }

    @BeforeEach
    void initTestBeans() throws BusinessException {
        sellerTest = new Seller("CreateItemstoreName", null, null, null);
        userTest =
            new User(
                "CreateItemlastName",
                "CreateItemfirstName",
                "CreateItememail1@email.com",
                "CreateItemPassword1",
                sellerTest,
                null,
                new ArrayList<>()
            );

        userService.create(userTest);
        tokenTest = tokenService.createNewTokenForUser(userTest);
        tokenService.saveToken(tokenTest);
        cookie = tokenService.createTokenCookie(tokenTest);
    }

    @Test
    void givenConnectedUser_whenSaveItem_thenItemReturn() throws Exception {
        // GIVEN
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

        jsonUserTest = new Gson().toJson(itemTest);

        // WHEN
        mockMvc
            .perform(
                post("/item/create")
                    .cookie(cookie)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUserTest)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andDo(
                mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                }
            );

        ItemBean resultFromJson = mapper.readValue(jsonResult, ItemBean.class);

        // THEN
        assertAll(
            "The returned item does not comply.",
            () -> assertEquals(itemTest.getTitle(), resultFromJson.getTitle()),
            () ->
                assertEquals(
                    itemTest.getPicture(),
                    resultFromJson.getPicture()
                ),
            () ->
                assertEquals(itemTest.getAmount(), resultFromJson.getAmount()),
            () -> assertEquals(itemTest.getPrice(), resultFromJson.getPrice()),
            () ->
                assertEquals(
                    itemTest.getSellerAccount().getStoreName(),
                    resultFromJson.getSellerAccount().getStoreName()
                )
        );

        // THEN
        Assertions.assertFalse(jsonResult.isBlank());
    }

    @Test
    void givenNotConnectedUser_whenSaveItem_thenItemNotReturn()
        throws Exception {
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
                sellerTest
            );

        jsonUserTest = new Gson().toJson(itemTest);

        // WHEN
        mockMvc
            .perform(
                post("/item/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUserTest)
            )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(contentType))
            .andDo(
                mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                }
            );

        // THEN
        Assertions.assertFalse(jsonResult.isBlank());
        Assertions.assertEquals(
            MoneyDystopieConstants.EXPIRED_CONNEXION_ERROR,
            new String(
                jsonResult.getBytes(StandardCharsets.ISO_8859_1),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    void givenConnectedUser_whenSaveUser_thenItemIsSavedSellerModified()
        throws Exception {
        // GIVEN
        itemTest =
            new Item(
                null,
                "title40",
                "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
                "description40",
                10,
                5.f,
                null,
                sellerTest
            );
        itemsNumber = itemService.findAll().size();

        jsonUserTest = new Gson().toJson(itemTest);

        // WHEN
        mockMvc
            .perform(
                post("/item/create")
                    .cookie(cookie)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUserTest)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andDo(
                mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                }
            );

        ItemBean result = mapper.readValue(jsonResult, ItemBean.class);

        // THEN
        Item resultItem = itemService.findById(result.getId());

        Assertions.assertNotNull(resultItem, "The saved item was not found.");

        Assertions.assertEquals(
            itemsNumber + 1,
            itemService.findAll().size(),
            "The saved item exist."
        );

        assertAll(
            "The returned item does not comply.",
            () -> assertEquals(itemTest.getTitle(), resultItem.getTitle()),
            () -> assertEquals(itemTest.getPicture(), resultItem.getPicture()),
            () -> assertEquals(itemTest.getAmount(), resultItem.getAmount()),
            () -> assertEquals(itemTest.getPrice(), resultItem.getPrice())
        );

        // THEN
        Seller resultSeller = sellerService.findByStoreName(
            resultItem.getSellerAccount().getStoreName()
        );

        Assertions.assertNotNull(
            resultSeller,
            "The saved seller was not found."
        );

        Assertions.assertNotNull(
            resultSeller.getItems(),
            "The seller does not comply."
        );

        Assertions.assertEquals(
            sellerTest.getStoreName(),
            resultSeller.getStoreName(),
            "The returned user does not comply."
        );
    }

    @Test
    void givenNotConnectedUser_whenSaveUser_thenItemIsSavedSellerNotModified()
        throws Exception {
        // GIVEN
        itemTest =
            new Item(
                null,
                "title40",
                "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
                "description40",
                10,
                5.f,
                null,
                sellerTest
            );
        itemsNumber = itemService.findAll().size();

        jsonUserTest = new Gson().toJson(itemTest);

        // WHEN
        mockMvc
            .perform(
                post("/item/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUserTest)
            )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(contentType))
            .andDo(
                mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                }
            );

        // THEN
        Assertions.assertFalse(jsonResult.isBlank());
        Assertions.assertEquals(
            MoneyDystopieConstants.EXPIRED_CONNEXION_ERROR,
            new String(
                jsonResult.getBytes(StandardCharsets.ISO_8859_1),
                StandardCharsets.UTF_8
            )
        );
    }

    @ParameterizedTest
    @CsvSource(
        {
            "title41,description41,1,2.f,false,",
            ",description42,1,2.f,true,storeName42",
            "title44,,1,2.f,true,storeName44",
            "title45,description45,,2.f,true,storeName45",
            "title46,description46,1,,true,storeName46",
        }
    )
    void whenSaveItemWithoutElement_thenThrowBusinessException(
        String title,
        String description,
        Integer amount,
        Float price,
        Boolean areSeller,
        String storeName
    ) throws Exception {
        // GIVEN
        if (areSeller) {
            sellerTest = new Seller(storeName, null, null, null);
        }
        itemTest =
            new Item(
                null,
                title,
                null,
                description,
                amount,
                price,
                null,
                areSeller ? parameterizedSellerTest : null
            );

        itemsNumber = itemService.findAll().size();

        jsonUserTest = new Gson().toJson(itemTest);

        // WHEN
        mockMvc
            .perform(
                post("/item/create")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(jsonUserTest)
            )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(contentType))
            .andDo(
                mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                }
            );

        // THEN
        Assertions.assertFalse(jsonResult.isBlank());
        Assertions.assertEquals(
            itemsNumber,
            itemService.findAll().size(),
            "The saved item exist."
        );
    }

    @ParameterizedTest
    @CsvSource(
        {
            "title47,description47,1,2.f,false,",
            ",description48,1,2.f,true,storeName48",
            "title50,,1,2.f,true,storeName50",
            "title51,description51,,2.f,true,storeName51",
            "title52,description52,1,,true,storeName52",
        }
    )
    void whenSaveUserWithoutId_thenNoSave(
        String title,
        String description,
        Integer amount,
        Float price,
        Boolean areSeller,
        String storeName
    ) throws Exception {
        // GIVEN
        if (areSeller) {
            sellerTest = new Seller(storeName, null, null, null);
            sellerService.save(sellerTest);
        }
        itemTest =
            new Item(
                null,
                title,
                "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
                description,
                amount,
                price,
                null,
                areSeller ? sellerTest : null
            );
        itemsNumber = itemService.findAll().size();

        jsonUserTest = new Gson().toJson(itemTest);

        // WHEN
        mockMvc
            .perform(
                post("/item/create")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(jsonUserTest)
            )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(contentType));

        // THEN
        Assertions.assertEquals(
            itemsNumber,
            itemService.findAll().size(),
            "The saved item exist."
        );

        // THEN
        Seller resultSeller = sellerService.findByStoreName(
            itemTest.getSellerAccount() != null
                ? itemTest.getSellerAccount().getStoreName()
                : null
        );

        if (areSeller) {
            Assertions.assertNotNull(
                resultSeller,
                "The saved seller was found."
            );
        } else {
            Assertions.assertNull(resultSeller, "The saved seller was exist.");
        }

        if (areSeller) {
            Assertions.assertTrue(
                resultSeller.getItems().isEmpty(),
                "The item is add in seller."
            );
        }
    }

    @Test
    void givenItemWithoutId_whenAmount_thenErrorReturned() throws Exception {
        // GIVEN
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
        jsonUserTest = new Gson().toJson(itemTest);

        // WHEN
        mockMvc
            .perform(
                post("/item/amount")
                    .cookie(cookie)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUserTest)
            )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(contentType))
            .andDo(
                mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                }
            );

        // THEN
        Assertions.assertFalse(jsonResult.isBlank());
        Assertions.assertEquals(
            MoneyDystopieConstants.DEFAULT_ERROR_CONTENT,
            new String(
                jsonResult.getBytes(StandardCharsets.ISO_8859_1),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    void givenConnectedUser_whenAll_thenAuthorized() throws Exception {
        // GIVEN

        // WHEN
        mockMvc
            .perform(get("/item/all").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andDo(
                mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                }
            );

        // THEN
        Assertions.assertFalse(jsonResult.isBlank());
    }
}
