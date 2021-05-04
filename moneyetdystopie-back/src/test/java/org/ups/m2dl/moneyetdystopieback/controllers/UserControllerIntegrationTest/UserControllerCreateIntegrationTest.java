package org.ups.m2dl.moneyetdystopieback.controllers.UserControllerIntegrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.util.Assert;
import org.ups.m2dl.moneyetdystopieback.bean.UserBean;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessMessage;
import org.ups.m2dl.moneyetdystopieback.services.CustomerService;
import org.ups.m2dl.moneyetdystopieback.services.SellerService;
import org.ups.m2dl.moneyetdystopieback.services.UserService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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

    private String jsonResult;
    private User userTest;
    private Seller sellerTest;
    private Customer customerTest;
    private ObjectMapper mapper;
    private String jsonUserTest;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    @BeforeEach
    void setup(){
        mapper = new ObjectMapper();
    }

    @Test
    void whenSaveUser_thenUserReturn() throws Exception {

        // GIVEN
        userTest = new User("lastName1", "firstName1", "email1@email.email", "Passwordpassword1");
        sellerTest = new Seller("storeName1");
        customerTest = new Customer("pseudo1", "numberCityCountry1");
        userTest.setCustomerAccount(customerTest);
        userTest.setSellerAccount(sellerTest);

        jsonUserTest = new Gson().toJson(userTest);

        // WHEN
        mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON).content(jsonUserTest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andDo(mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                });

        UserBean result = mapper.readValue(jsonResult, UserBean.class);

        // THEN
        Assertions.assertEquals( userTest.getEmail(), result.getEmail(), "The returned user does not comply.");
        Assertions.assertTrue(passwordEncoder.matches(userTest.getPassword(), result.getPassword()));
        Assertions.assertEquals( userTest.getLastName(), result.getLastName(), "The returned user does not comply.");
        Assertions.assertEquals( userTest.getFirstName(), result.getFirstName(), "The returned user does not comply.");

        Assertions.assertEquals( userTest.getSellerAccount().getStoreName(), result.getSellerAccount().getStoreName(), "The returned user does not comply.");

        Assertions.assertEquals( userTest.getCustomerAccount().getAddress(), result.getCustomerAccount().getAddress(), "The returned user does not comply.");
        Assertions.assertEquals( userTest.getCustomerAccount().getPseudo(), result.getCustomerAccount().getPseudo(), "The returned user does not comply.");
    }

    @Test
    void whenSaveUser_thenUserIsSaved() throws Exception {

        // GIVEN
        userTest = new User("lastName2", "firstName2", "email2@email.email", "Passwordpassword2");
        sellerTest = new Seller("storeName2");
        customerTest = new Customer("pseudo2", "numberCityCountry2");
        userTest.setCustomerAccount(customerTest);
        userTest.setSellerAccount(sellerTest);

        jsonUserTest = new Gson().toJson(userTest);

        // WHEN
        mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON).content(jsonUserTest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));

        // THEN
        User resultUser = userService.findByEmail(userTest.getEmail());

        Assertions.assertNotNull( resultUser, "The saved user was not found.");

        Assertions.assertEquals( userTest.getEmail(), resultUser.getEmail(), "The returned user does not comply.");
        Assertions.assertTrue(passwordEncoder.matches(userTest.getPassword(), resultUser.getPassword()));
        Assertions.assertEquals( userTest.getLastName(), resultUser.getLastName(), "The returned user does not comply.");
        Assertions.assertEquals( userTest.getFirstName(), resultUser.getFirstName(), "The returned user does not comply.");

        // THEN
        List<Customer> resultCustomer = customerService.findByPseudo(userTest.getCustomerAccount().getPseudo());

        Assertions.assertEquals( 1, resultCustomer.size(), "The saved customer was not found.");

        Assertions.assertEquals( userTest.getCustomerAccount().getAddress(), resultCustomer.get(0).getAddress(), "The returned user does not comply.");
        Assertions.assertEquals( userTest.getCustomerAccount().getPseudo(), resultCustomer.get(0).getPseudo(), "The returned user does not comply.");

        // THEN
        List<Seller> resultSeller = sellerService.findByStoreName(userTest.getSellerAccount().getStoreName());

        Assertions.assertEquals( 1, resultSeller.size(), "The saved seller was not found.");

        Assertions.assertEquals( userTest.getSellerAccount().getStoreName(), resultSeller.get(0).getStoreName(), "The returned user does not comply.");
    }

    @Test
    void whenSaveUserWithoutCustomer_thenUserIsSaved() throws Exception {

        // GIVEN
        userTest = new User("lastName36", "firstName36", "email36@email.email", "Passwordpassword36");
        sellerTest = new Seller("storeName36");
        userTest.setSellerAccount(sellerTest);

        jsonUserTest = new Gson().toJson(userTest);

        // WHEN
        mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON).content(jsonUserTest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));

        // THEN
        User resultUser = userService.findByEmail(userTest.getEmail());

        Assertions.assertNotNull( resultUser, "The saved user was not found.");

        Assertions.assertEquals( userTest.getEmail(), resultUser.getEmail(), "The returned user does not comply.");
        Assertions.assertTrue(passwordEncoder.matches(userTest.getPassword(), resultUser.getPassword()));
        Assertions.assertEquals( userTest.getLastName(), resultUser.getLastName(), "The returned user does not comply.");
        Assertions.assertEquals( userTest.getFirstName(), resultUser.getFirstName(), "The returned user does not comply.");

        // THEN
        Assertions.assertNull( userTest.getCustomerAccount(), "The saved customer was found.");

        // THEN
        List<Seller> resultSeller = sellerService.findByStoreName(userTest.getSellerAccount().getStoreName());

        Assertions.assertEquals( 1, resultSeller.size(), "The saved seller was not found.");

        Assertions.assertEquals( userTest.getSellerAccount().getStoreName(), resultSeller.get(0).getStoreName(), "The returned user does not comply.");
    }


    @Test
    void whenSaveUserWithoutSeller_thenUserIsSaved() throws Exception {

        // GIVEN
        userTest = new User("lastName37", "firstName37", "email37@email.email", "Passwordpassword37");
        customerTest = new Customer("pseudo37", "numberCityCountry37");
        userTest.setCustomerAccount(customerTest);

        jsonUserTest = new Gson().toJson(userTest);

        // WHEN
        mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON).content(jsonUserTest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));

        // THEN
        User resultUser = userService.findByEmail(userTest.getEmail());

        Assertions.assertNotNull( resultUser, "The saved user was not found.");

        Assertions.assertEquals( userTest.getEmail(), resultUser.getEmail(), "The returned user does not comply.");
        Assertions.assertTrue(passwordEncoder.matches(userTest.getPassword(), resultUser.getPassword()));
        Assertions.assertEquals( userTest.getLastName(), resultUser.getLastName(), "The returned user does not comply.");
        Assertions.assertEquals( userTest.getFirstName(), resultUser.getFirstName(), "The returned user does not comply.");

        // THEN
        List<Customer> resultCustomer = customerService.findByPseudo(userTest.getCustomerAccount().getPseudo());

        Assertions.assertEquals( 1, resultCustomer.size(), "The saved customer was not found.");

        Assertions.assertEquals( userTest.getCustomerAccount().getAddress(), resultCustomer.get(0).getAddress(), "The returned user does not comply.");
        Assertions.assertEquals( userTest.getCustomerAccount().getPseudo(), resultCustomer.get(0).getPseudo(), "The returned user does not comply.");

        // THEN
        Assertions.assertNull( userTest.getSellerAccount(), "The saved seller was found.");
    }

    @ParameterizedTest
    @CsvSource(
            {",storeName3,H&pseudo3, mail",
                    "email4@email.email,,pseudo4,nom de boutique",
                    "email5@email.email,storeName5,,pseudo"}
    )
    void whenSaveUserWithoutId_thenThrowBusinessException(String email, String storeName, String pseudo, String subject) throws Exception {

        // GIVEN
        userTest = new User("lastName", "firstName", email, "Passwordpassword2");
        sellerTest = new Seller(storeName);
        customerTest = new Customer(pseudo, "numberCityCountry");
        userTest.setEmail(null);
        userTest.setCustomerAccount(customerTest);
        userTest.setSellerAccount(sellerTest);

        jsonUserTest = new Gson().toJson(userTest);

        // WHEN
        mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonUserTest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType))
                .andDo(mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                });
        BusinessMessage result = mapper.readValue(jsonResult, BusinessMessage.class);

        // THEN
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getErrorMessage().contains(subject), "Bad subject in error message : " + result.getErrorMessage());
    }

    @ParameterizedTest
    @CsvSource(
            {",storeName6,pseudo6",
            "email7@email.email,,pseudo7",
            "email8@email.email,storeName8,"}
    )
    void whenSaveUserWithoutId_thenNoSave(String email, String storeName, String pseudo) throws Exception {

        // GIVEN
        userTest = new User("lastName", "firstName", email, "Passwordpassword2");
        sellerTest = new Seller(storeName);
        customerTest = new Customer(pseudo, "numberCityCountry");
        userTest.setCustomerAccount(customerTest);
        userTest.setSellerAccount(sellerTest);

        jsonUserTest = new Gson().toJson(userTest);

        // WHEN
        mockMvc.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonUserTest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType));

        // THEN
        User resultUser = userService.findByEmail(userTest.getEmail());
        Assertions.assertNull( resultUser, "The saved user was found.");

        // THEN
        List<Customer> resultCustomer = customerService.findByPseudo(userTest.getCustomerAccount().getPseudo());
        Assertions.assertEquals( 0, resultCustomer.size(), "The saved customer was found.");

        // THEN
        List<Seller> resultSeller = sellerService.findByStoreName(userTest.getSellerAccount().getStoreName());
        Assertions.assertEquals( 0, resultSeller.size(), "The saved seller was found.");
    }
}