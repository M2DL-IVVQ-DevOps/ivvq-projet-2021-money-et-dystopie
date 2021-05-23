package org.ups.m2dl.moneyetdystopieback.controllers.controllers_integration_test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.ups.m2dl.moneyetdystopieback.bean.UserBean;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.domain.Token;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.services.TokenService;
import org.ups.m2dl.moneyetdystopieback.services.UserService;
import org.ups.m2dl.moneyetdystopieback.utils.MoneyDystopieConstants;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TokenControllerCreateIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    private String jsonResult;
    private User userTest;
    private Seller sellerTest;
    private Customer customerTest;
    private ObjectMapper mapper;
    private String jsonUserTest;
    private String jsonUserBeanTest;

    private final MediaType contentType = new MediaType(
        MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype()
    );

    @BeforeEach
    public void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    void givenValidData_whenCreateToken_thenUserReturned() throws Exception {
        // GIVEN
        userTest =
            new User(
                "TokenlastName1",
                "TokenfirstName1",
                "Tokenemail1@email.email",
                "TokenPasswordpassword1",
                null,
                null,
                null
            );
        sellerTest = new Seller("TokenstoreName1", null, null, null);
        customerTest =
            new Customer(
                "Tokenpseudo1",
                "TokennumberCityCountry1",
                null,
                null,
                null
            );
        userTest.setCustomerAccount(customerTest);
        userTest.setSellerAccount(sellerTest);

        jsonUserTest = new Gson().toJson(userTest);

        userService.create(userTest);
        UserBean userBeanTest = new UserBean(
            null,
            null,
            "Tokenemail1@email.email",
            "TokenPasswordpassword1",
            null,
            null
        );
        jsonUserBeanTest = new Gson().toJson(userBeanTest);

        // WHEN
        mockMvc
            .perform(
                post("/token/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUserBeanTest)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(cookie().exists("token"))
            .andDo(
                mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                }
            );

        // THEN
        UserBean resultFromJson = mapper.readValue(jsonResult, UserBean.class);
        Assertions.assertEquals(userTest.getEmail(), resultFromJson.getEmail());
        Assertions.assertEquals(
            userTest.getFirstName(),
            resultFromJson.getFirstName()
        );
        Assertions.assertEquals(
            userTest.getLastName(),
            resultFromJson.getLastName()
        );
        Assertions.assertEquals(
            userTest.getCustomerAccount().getAddress(),
            resultFromJson.getCustomerAccount().getAddress()
        );
        Assertions.assertEquals(
            userTest.getCustomerAccount().getPseudo(),
            resultFromJson.getCustomerAccount().getPseudo()
        );
        Assertions.assertEquals(
            userTest.getSellerAccount().getStoreName(),
            resultFromJson.getSellerAccount().getStoreName()
        );
    }

    @Test
    void givenValidData_whenCreateToken_thenUserReturned2() throws Exception {
        // GIVEN
        userTest =
                new User(
                        "TokenlastName1",
                        "TokenfirstName1",
                        "Tokenemail1@email.email",
                        "TokenPasswordpassword1",
                        null,
                        null,
                        null
                );
        sellerTest = new Seller("TokenstoreName1", null, null, null);
        customerTest =
                new Customer(
                        "Tokenpseudo1",
                        "TokennumberCityCountry1",
                        null,
                        null,
                        null
                );
        userTest.setCustomerAccount(customerTest);
        userTest.setSellerAccount(sellerTest);

        jsonUserTest = new Gson().toJson(userTest);

        userService.create(userTest);
        UserBean userBeanTest = new UserBean(
                null,
                null,
                "Tokenemail1@email.email",
                "TokenPasswordpassword1",
                null,
                null
        );
        jsonUserBeanTest = new Gson().toJson(userBeanTest);

        // WHEN
        mockMvc
                .perform(
                        post("/token/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonUserBeanTest)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(cookie().exists("token"))
                .andDo(
                        mvcResult -> {
                            jsonResult = mvcResult.getResponse().getContentAsString();
                        }
                );

        // THEN
        UserBean resultFromJson = mapper.readValue(jsonResult, UserBean.class);
        Assertions.assertEquals(userTest.getEmail(), resultFromJson.getEmail());
        Assertions.assertEquals(
                userTest.getFirstName(),
                resultFromJson.getFirstName()
        );
        Assertions.assertEquals(
                userTest.getLastName(),
                resultFromJson.getLastName()
        );
        Assertions.assertEquals(
                userTest.getCustomerAccount().getAddress(),
                resultFromJson.getCustomerAccount().getAddress()
        );
        Assertions.assertEquals(
                userTest.getCustomerAccount().getPseudo(),
                resultFromJson.getCustomerAccount().getPseudo()
        );
        Assertions.assertEquals(
                userTest.getSellerAccount().getStoreName(),
                resultFromJson.getSellerAccount().getStoreName()
        );
    }

    @Test
    void givenValidToken_whenCreateToken_thenUserReturned() throws Exception {
        // GIVEN
        userTest =
                new User(
                        "TokenlastName1",
                        "TokenfirstName1",
                        "Tokenemail1@email.email",
                        "TokenPasswordpassword1",
                        null,
                        null,
                        null
                );
        sellerTest = new Seller("TokenstoreName1", null, null, null);
        customerTest =
                new Customer(
                        "Tokenpseudo1",
                        "TokennumberCityCountry1",
                        null,
                        null,
                        null
                );
        userTest.setCustomerAccount(customerTest);
        userTest.setSellerAccount(sellerTest);

        jsonUserTest = new Gson().toJson(userTest);

        userService.create(userTest);
        Token tokenTest = tokenService.createNewTokenForUser(userTest);
        tokenService.saveToken(tokenTest);
        Cookie cookie = tokenService.createTokenCookie(tokenTest);
        UserBean userBeanTest = new UserBean(
                null,
                null,
                null,
                null,
                null,
                null
        );
        jsonUserBeanTest = new Gson().toJson(userBeanTest);

        // WHEN
        mockMvc
                .perform(
                        post("/token/create").cookie(cookie)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonUserBeanTest)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(cookie().exists("token"))
                .andDo(
                        mvcResult -> {
                            jsonResult = mvcResult.getResponse().getContentAsString();
                        }
                );

        // THEN
        UserBean resultFromJson = mapper.readValue(jsonResult, UserBean.class);
        Assertions.assertEquals(userTest.getEmail(), resultFromJson.getEmail());
        Assertions.assertEquals(
                userTest.getFirstName(),
                resultFromJson.getFirstName()
        );
        Assertions.assertEquals(
                userTest.getLastName(),
                resultFromJson.getLastName()
        );
        Assertions.assertEquals(
                userTest.getCustomerAccount().getAddress(),
                resultFromJson.getCustomerAccount().getAddress()
        );
        Assertions.assertEquals(
                userTest.getCustomerAccount().getPseudo(),
                resultFromJson.getCustomerAccount().getPseudo()
        );
        Assertions.assertEquals(
                userTest.getSellerAccount().getStoreName(),
                resultFromJson.getSellerAccount().getStoreName()
        );
    }

    @Test
    void givenInvalidData_whenCreateToken_thenInvalidConnexionError()
        throws Exception {
        // GIVEN
        userTest =
            new User(
                "TokenlastName2",
                "TokenfirstName2",
                "Tokenemail2@email.email",
                "TokenPasswordpassword2",
                null,
                null,
                null
            );
        sellerTest = new Seller("TokenstoreName2", null, null, null);
        customerTest =
            new Customer(
                "Tokenpseudo2",
                "TokennumberCityCountry2",
                null,
                null,
                null
            );
        userTest.setCustomerAccount(customerTest);
        userTest.setSellerAccount(sellerTest);

        jsonUserTest = new Gson().toJson(userTest);

        userService.create(userTest);
        UserBean userBeanTest = new UserBean(
            null,
            null,
            "Tokenemail2@email.email",
            "TokenWrongPassword",
            null,
            null
        );
        jsonUserBeanTest = new Gson().toJson(userBeanTest);

        // WHEN
        mockMvc
            .perform(
                post("/token/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUserBeanTest)
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
            MoneyDystopieConstants.INVALID_CONNEXION_ERROR,
            new String(
                jsonResult.getBytes(StandardCharsets.ISO_8859_1),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    void givenValidData_whenCheckToken_thenUserReturned() throws Exception {
        // GIVEN
        userTest =
            new User(
                "TokenlastName3",
                "TokenfirstName3",
                "Tokenemail3@email.email",
                "TokenPasswordpassword3",
                null,
                null,
                null
            );
        sellerTest = new Seller("TokenstoreName3", null, null, null);
        customerTest =
            new Customer(
                "Tokenpseudo3",
                "TokennumberCityCountry3",
                null,
                null,
                null
            );
        userTest.setCustomerAccount(customerTest);
        userTest.setSellerAccount(sellerTest);

        jsonUserTest = new Gson().toJson(userTest);

        userService.create(userTest);
        Token tokenTest = tokenService.createNewTokenForUser(userTest);
        tokenService.saveToken(tokenTest);
        Cookie cookie = tokenService.createTokenCookie(tokenTest);

        // WHEN
        mockMvc
            .perform(post("/token/check").cookie(cookie))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andDo(
                mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                }
            );

        // THEN
        UserBean resultFromJson = mapper.readValue(jsonResult, UserBean.class);
        Assertions.assertEquals(userTest.getEmail(), resultFromJson.getEmail());
        Assertions.assertEquals(
            userTest.getFirstName(),
            resultFromJson.getFirstName()
        );
        Assertions.assertEquals(
            userTest.getLastName(),
            resultFromJson.getLastName()
        );
        Assertions.assertEquals(
            userTest.getCustomerAccount().getAddress(),
            resultFromJson.getCustomerAccount().getAddress()
        );
        Assertions.assertEquals(
            userTest.getCustomerAccount().getPseudo(),
            resultFromJson.getCustomerAccount().getPseudo()
        );
        Assertions.assertEquals(
            userTest.getSellerAccount().getStoreName(),
            resultFromJson.getSellerAccount().getStoreName()
        );
    }

    @Test
    void givenInvalidData_whenCheckToken_thenExpiredConnexionError()
        throws Exception {
        // GIVEN
        userTest =
            new User(
                "TokenlastName4",
                "TokenfirstName4",
                "Tokenemail4@email.email",
                "TokenPasswordpassword4",
                null,
                null,
                null
            );
        sellerTest = new Seller("TokenstoreName4", null, null, null);
        customerTest =
            new Customer(
                "Tokenpseudo4",
                "TokennumberCityCountry4",
                null,
                null,
                null
            );
        userTest.setCustomerAccount(customerTest);
        userTest.setSellerAccount(sellerTest);

        jsonUserTest = new Gson().toJson(userTest);

        userService.create(userTest);
        Token tokenTest = tokenService.createNewTokenForUser(userTest);
        tokenService.saveToken(tokenTest);
        tokenTest.setValue(tokenTest.getValue() + "8");
        Cookie cookie = tokenService.createTokenCookie(tokenTest);

        // WHEN
        mockMvc
            .perform(post("/token/check").cookie(cookie))
            .andExpect(status().isForbidden())
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
    void givenValidData_whenRemoveToken_thenSuccess() throws Exception {
        // GIVEN
        userTest =
            new User(
                "TokenlastName5",
                "TokenfirstName5",
                "Tokenemail5@email.email",
                "TokenPasswordpassword5",
                null,
                null,
                null
            );
        sellerTest = new Seller("TokenstoreName5", null, null, null);
        customerTest =
            new Customer(
                "Tokenpseudo5",
                "TokennumberCityCountry5",
                null,
                null,
                null
            );
        userTest.setCustomerAccount(customerTest);
        userTest.setSellerAccount(sellerTest);

        jsonUserTest = new Gson().toJson(userTest);

        userService.create(userTest);
        Token tokenTest = tokenService.createNewTokenForUser(userTest);
        tokenService.saveToken(tokenTest);
        Cookie cookie = tokenService.createTokenCookie(tokenTest);

        // WHEN
        mockMvc
            .perform(post("/token/remove").cookie(cookie))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andDo(
                mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                }
            );

        // THEN
        boolean success = mapper.readValue(jsonResult, boolean.class);
        Assertions.assertTrue(success);
    }

    @Test
    void givenInvalidData_whenRemoveToken_thenFailure() throws Exception {
        // GIVEN
        userTest =
            new User(
                "TokenlastName6",
                "TokenfirstName6",
                "Tokenemail6@email.email",
                "TokenPasswordpassword6",
                null,
                null,
                null
            );
        sellerTest = new Seller("TokenstoreName6", null, null, null);
        customerTest =
            new Customer(
                "Tokenpseudo6",
                "TokennumberCityCountry6",
                null,
                null,
                null
            );
        userTest.setCustomerAccount(customerTest);
        userTest.setSellerAccount(sellerTest);

        jsonUserTest = new Gson().toJson(userTest);

        userService.create(userTest);
        Token tokenTest = tokenService.createNewTokenForUser(userTest);
        tokenService.saveToken(tokenTest);
        tokenTest.setValue(tokenTest.getValue() + "8");
        Cookie cookie = tokenService.createTokenCookie(tokenTest);

        // WHEN
        mockMvc
            .perform(post("/token/remove").cookie(cookie))
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
}
