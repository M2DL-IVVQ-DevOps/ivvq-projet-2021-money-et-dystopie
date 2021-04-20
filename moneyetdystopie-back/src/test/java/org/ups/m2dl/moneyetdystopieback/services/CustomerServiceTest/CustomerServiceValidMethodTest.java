package org.ups.m2dl.moneyetdystopieback.services.CustomerServiceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.CustomerService;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerServiceValidMethodTest {

    @Autowired
    CustomerService customerService;

    Customer customerTest;

    @ParameterizedTest
    @ValueSource(strings = {"1234","012345678901234567890123456789012345678901234567890"})
    @NullSource
    void whenValidCustomerWithBadPseudo_thenThrowBusinessException(String pseudo)  {

        // GIVEN
        customerTest = new Customer(pseudo,"numberCityCountry18");

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            customerService.valid(customerTest);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"123456789","0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"})
    @NullSource
    void whenValidCustomerWithBadAddress_thenThrowBusinessException(String address)  {

        // GIVEN
        customerTest = new Customer("1234",address);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // HEN
            customerService.valid(customerTest);
        });
    }

    @Test
    void whenValidCustomer_thenNoThrowBusinessException() {
        // GIVEN
        customerTest = new Customer("pseudo18", "numberCityCountry18");

        //WHEN
        Assertions.assertDoesNotThrow( () -> {
            customerService.valid(customerTest);
        });
    }
}