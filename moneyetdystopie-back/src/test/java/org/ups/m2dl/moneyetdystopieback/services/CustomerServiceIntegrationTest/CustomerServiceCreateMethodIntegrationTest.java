package org.ups.m2dl.moneyetdystopieback.services.customerserviceintegrationtest;

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
    private CustomerService customerService;

    private Customer customerTestTwinA;
    private Customer customerTestTwinB;
    private Customer customerTest;

    @Test
    void whenCreateCustomerWithSamePseudo_thenThrowBusinessException() throws BusinessException {

        // GIVEN
        customerTestTwinA = new Customer("customerTestTwin", "numberCityCountry9", null, null, null);
        customerTestTwinB = new Customer("customerTestTwin", "numberCityCountry10", null, null, null);

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
        customerTest = new Customer(null, "numberCityCountry11", null, null, null);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            customerService.create(customerTest);
        });
    }
}
