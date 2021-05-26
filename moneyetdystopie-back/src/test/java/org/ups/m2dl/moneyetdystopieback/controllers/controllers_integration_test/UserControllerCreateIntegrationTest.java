package org.ups.m2dl.moneyetdystopieback.controllers.controllers_integration_test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.ups.m2dl.moneyetdystopieback.bean.UserBean;
import org.ups.m2dl.moneyetdystopieback.domain.*;
import org.ups.m2dl.moneyetdystopieback.services.*;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerCreateIntegrationTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CommandService commandService;

    private String jsonResult;
    private User userTest;
    private Seller sellerTest;
    private Customer customerTest;
    private ObjectMapper mapper;
    private String jsonUserTest;

    private MediaType contentType = new MediaType(
        MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype()
    );

    @BeforeAll
    void setup() {
        mapper = new ObjectMapper();
    }

    @BeforeEach
    void setupBeforeEach() {
        userTest =
            new User(
                "lastName",
                "firstName",
                "email@email.email",
                "Passwordpassword1",
                null,
                null,
                null
            );
        sellerTest = new Seller("storeName1", null, null, null);
        customerTest =
            new Customer("pseudo1", "numberCityCountry1", null, null, null);
        userTest.setCustomerAccount(customerTest);
        userTest.setSellerAccount(sellerTest);
    }

    @Test
    void whenSaveUser_thenUserReturn() throws Exception {
        // GIVEN
        jsonUserTest = new Gson().toJson(userTest);

        // WHEN
        mockMvc
            .perform(
                post("/user/create")
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

        UserBean result = mapper.readValue(jsonResult, UserBean.class);

        // THEN
        Assertions.assertEquals(
            userTest.getEmail(),
            result.getEmail(),
            "The returned user does not comply."
        );
        Assertions.assertEquals(
            userTest.getLastName(),
            result.getLastName(),
            "The returned user does not comply."
        );
        Assertions.assertEquals(
            userTest.getFirstName(),
            result.getFirstName(),
            "The returned user does not comply."
        );

        Assertions.assertEquals(
            userTest.getSellerAccount().getStoreName(),
            result.getSellerAccount().getStoreName(),
            "The returned user does not comply."
        );

        Assertions.assertEquals(
            userTest.getCustomerAccount().getAddress(),
            result.getCustomerAccount().getAddress(),
            "The returned user does not comply."
        );
        Assertions.assertEquals(
            userTest.getCustomerAccount().getPseudo(),
            result.getCustomerAccount().getPseudo(),
            "The returned user does not comply."
        );
    }

    @Test
    void whenSaveUser_thenUserIsSaved() throws Exception {
        // GIVEN
        jsonUserTest = new Gson().toJson(userTest);

        // WHEN
        mockMvc
            .perform(
                post("/user/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUserTest)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType));

        // THEN
        User resultUser = userService.findByEmail(userTest.getEmail());

        Assertions.assertNotNull(resultUser, "The saved user was not found.");

        Assertions.assertEquals(
            userTest.getEmail(),
            resultUser.getEmail(),
            "The returned user does not comply."
        );
        Assertions.assertTrue(
            passwordEncoder.matches(
                userTest.getPassword(),
                resultUser.getPassword()
            )
        );
        Assertions.assertEquals(
            userTest.getLastName(),
            resultUser.getLastName(),
            "The returned user does not comply."
        );
        Assertions.assertEquals(
            userTest.getFirstName(),
            resultUser.getFirstName(),
            "The returned user does not comply."
        );

        // THEN
        Customer resultCustomer = customerService.findByPseudo(
            userTest.getCustomerAccount().getPseudo()
        );

        Assertions.assertNotNull(
            resultCustomer,
            "The saved customer was not found."
        );

        Assertions.assertEquals(
            userTest.getCustomerAccount().getAddress(),
            resultCustomer.getAddress(),
            "The returned user does not comply."
        );
        Assertions.assertEquals(
            userTest.getCustomerAccount().getPseudo(),
            resultCustomer.getPseudo(),
            "The returned user does not comply."
        );

        // THEN
        Seller resultSeller = sellerService.findByStoreName(
            userTest.getSellerAccount().getStoreName()
        );

        Assertions.assertNotNull(
            resultSeller,
            "The saved seller was not found."
        );

        Assertions.assertEquals(
            userTest.getSellerAccount().getStoreName(),
            resultSeller.getStoreName(),
            "The returned user does not comply."
        );
    }

    @Test
    void whenSaveUserWithoutCustomer_thenUserIsSaved() throws Exception {
        // GIVEN
        userTest.setCustomerAccount(null);

        jsonUserTest = new Gson().toJson(userTest);

        // WHEN
        mockMvc
            .perform(
                post("/user/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUserTest)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType));

        // THEN
        User resultUser = userService.findByEmail(userTest.getEmail());

        Assertions.assertNotNull(resultUser, "The saved user was not found.");

        Assertions.assertEquals(
            userTest.getEmail(),
            resultUser.getEmail(),
            "The returned user does not comply."
        );
        Assertions.assertTrue(
            passwordEncoder.matches(
                userTest.getPassword(),
                resultUser.getPassword()
            ),
            "The returned user does not comply."
        );
        Assertions.assertEquals(
            userTest.getLastName(),
            resultUser.getLastName(),
            "The returned user does not comply."
        );
        Assertions.assertEquals(
            userTest.getFirstName(),
            resultUser.getFirstName(),
            "The returned user does not comply."
        );

        // THEN
        Assertions.assertNull(
            userTest.getCustomerAccount(),
            "The saved customer was found."
        );

        // THEN
        Seller resultSeller = sellerService.findByStoreName(
            userTest.getSellerAccount().getStoreName()
        );

        Assertions.assertNotNull(
            resultSeller,
            "The saved seller was not found."
        );

        Assertions.assertEquals(
            userTest.getSellerAccount().getStoreName(),
            resultSeller.getStoreName(),
            "The returned user does not comply."
        );
    }

    @Test
    void whenSaveUserWithoutSeller_thenUserIsSaved() throws Exception {
        // GIVEN
        userTest.setSellerAccount(null);
        jsonUserTest = new Gson().toJson(userTest);

        // WHEN
        mockMvc
            .perform(
                post("/user/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUserTest)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType));

        // THEN
        User resultUser = userService.findByEmail(userTest.getEmail());

        Assertions.assertNotNull(resultUser, "The saved user was not found.");

        Assertions.assertEquals(
            userTest.getEmail(),
            resultUser.getEmail(),
            "The returned user does not comply."
        );
        Assertions.assertTrue(
            passwordEncoder.matches(
                userTest.getPassword(),
                resultUser.getPassword()
            ),
            "The returned user does not comply."
        );
        Assertions.assertEquals(
            userTest.getLastName(),
            resultUser.getLastName(),
            "The returned user does not comply."
        );
        Assertions.assertEquals(
            userTest.getFirstName(),
            resultUser.getFirstName(),
            "The returned user does not comply."
        );

        // THEN
        Customer resultCustomer = customerService.findByPseudo(
            userTest.getCustomerAccount().getPseudo()
        );

        Assertions.assertNotNull(
            resultCustomer,
            "The saved customer was not found."
        );

        Assertions.assertEquals(
            userTest.getCustomerAccount().getAddress(),
            resultCustomer.getAddress(),
            "The returned user does not comply."
        );
        Assertions.assertEquals(
            userTest.getCustomerAccount().getPseudo(),
            resultCustomer.getPseudo(),
            "The returned user does not comply."
        );

        // THEN
        Assertions.assertNull(
            userTest.getSellerAccount(),
            "The saved seller was found."
        );
    }

    @ParameterizedTest
    @CsvSource(
        {
            ",storeName3,H&pseudo3, mail",
            "email4@email.email,,pseudo4,nom de boutique",
            "email5@email.email,storeName5,,pseudo",
        }
    )
    void whenSaveUserWithoutId_thenThrowBusinessException(
        String email,
        String storeName,
        String pseudo,
        String subject
    ) throws Exception {
        // GIVEN
        userTest =
            new User(
                "lastName",
                "firstName",
                email,
                "Passwordpassword2",
                null,
                null,
                null
            );
        sellerTest = new Seller(storeName, null, null, null);
        customerTest =
            new Customer(pseudo, "numberCityCountry", null, null, null);
        userTest.setEmail(null);
        userTest.setCustomerAccount(customerTest);
        userTest.setSellerAccount(sellerTest);

        jsonUserTest = new Gson().toJson(userTest);

        // WHEN
        mockMvc
            .perform(
                post("/user/create")
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
    }

    @ParameterizedTest
    @CsvSource(
        {
            ",storeName6,pseudo6",
            "email7@email.email,,pseudo7",
            "email8@email.email,storeName8,",
        }
    )
    void whenSaveUserWithoutId_thenNoSave(
        String email,
        String storeName,
        String pseudo
    ) throws Exception {
        // GIVEN
        userTest =
            new User(
                "lastName",
                "firstName",
                email,
                "Passwordpassword2",
                null,
                null,
                null
            );
        sellerTest = new Seller(storeName, null, null, null);
        customerTest =
            new Customer(pseudo, "numberCityCountry", null, null, null);
        userTest.setCustomerAccount(customerTest);
        userTest.setSellerAccount(sellerTest);

        jsonUserTest = new Gson().toJson(userTest);

        // WHEN
        mockMvc
            .perform(
                post("/user/create")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(jsonUserTest)
            )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(contentType));

        // THEN
        User resultUser = userService.findByEmail(userTest.getEmail());
        Assertions.assertNull(resultUser, "The saved user was found.");

        // THEN
        Customer resultCustomer = customerService.findByPseudo(
            userTest.getCustomerAccount().getPseudo()
        );
        Assertions.assertNull(resultCustomer, "The saved customer was found.");

        // THEN
        Seller resultSeller = sellerService.findByStoreName(
            userTest.getSellerAccount().getStoreName()
        );
        Assertions.assertNull(resultSeller, "The saved seller was found.");
    }

    @Test
    void givenValidSeller_whenSellerCommands_thenReturnCommands()
        throws Exception {
        // GIVEN
        userService.create(userTest);
        Token tokenTest = tokenService.createNewTokenForUser(userTest);
        tokenService.saveToken(tokenTest);
        Cookie cookie = tokenService.createTokenCookie(tokenTest);

        // WHEN
        mockMvc
            .perform(
                get("/user/sellerCommands")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .cookie(cookie)
            )
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

    @Test
    void givenUserNotSeller_whenSellerCommands_thenNotReturnError()
        throws Exception {
        // GIVEN
        userTest.setSellerAccount(null);
        userService.create(userTest);
        Token tokenTest = tokenService.createNewTokenForUser(userTest);
        tokenService.saveToken(tokenTest);
        Cookie cookie = tokenService.createTokenCookie(tokenTest);

        // WHEN
        mockMvc
            .perform(
                get("/user/sellerCommands")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .cookie(cookie)
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
            MoneyDystopieConstants.UNDEFINED_SHOP_ERROR,
            new String(
                jsonResult.getBytes(StandardCharsets.ISO_8859_1),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    void givenNotValidToken_whenSellerCommands_thenReturnError()
        throws Exception {
        // GIVEN
        userService.create(userTest);
        Token tokenTest = tokenService.createNewTokenForUser(userTest);
        tokenService.saveToken(tokenTest);
        Cookie cookie = tokenService.createTokenCookie(tokenTest);
        cookie.setValue("2");

        // WHEN
        mockMvc
            .perform(
                get("/user/sellerCommands")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .cookie(cookie)
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
}
