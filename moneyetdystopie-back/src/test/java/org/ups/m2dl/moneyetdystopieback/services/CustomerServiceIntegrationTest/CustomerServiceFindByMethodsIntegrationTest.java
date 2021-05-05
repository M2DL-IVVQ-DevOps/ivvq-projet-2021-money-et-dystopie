package org.ups.m2dl.moneyetdystopieback.services.customerserviceintegrationtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.CustomerService;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerServiceFindByMethodsIntegrationTest {

    @Autowired
    private CustomerService customerService;

    private Customer customerTest;

    @Test
    void whenCreateSaveCustomer_thenCanFind() throws BusinessException {

        // GIVEN
        customerTest = new Customer("pseudo12", "numberCityCountry12", null, null, null);

        // WHEN
        customerService.save(customerTest);
        Customer resultFindBy = customerService.findByPseudo(customerTest.getPseudo());

        // THEN
        Assertions.assertNotNull( resultFindBy, "The saved customer was not found.");
    }

    @Test
    void whenSearchCustomerDontExist_thenNoResult() {
        // GIVEN WHEN
        Customer resultFindBy = customerService.findByPseudo("pseudo13");

        Assertions.assertNull(  resultFindBy, "A customer was returned despite his non-existence.");
    }
}
