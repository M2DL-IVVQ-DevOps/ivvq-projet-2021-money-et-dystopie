package org.ups.m2dl.moneyetdystopieback.services.CustomerServiceIntegrationTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.CustomerService;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerServiceCreateMethodIntegrationTest {

    @Autowired
    CustomerService customerService;

    Customer customerTestTwinA;
    Customer customerTestTwinB;
    Customer customerTest;

    @Test
    void whenCreateCustomerWithSamePseudo_thenThrowBusinessException() throws BusinessException {

        // GIVEN
        customerTestTwinA = new Customer("customerTestTwin", "numberCityCountry9");
        customerTestTwinB = new Customer("customerTestTwin", "numberCityCountry10");

        // WHEN
        customerService.create(customerTestTwinA);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            customerService.create(customerTestTwinB);
        });
    }

    @Test
    void whenCreateCustomerWithoutPseudo_thenThrowBusinessException() {
        // GIVEN
        customerTest = new Customer(null, "numberCityCountry11");

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            customerService.create(customerTest);
        });
    }
}
