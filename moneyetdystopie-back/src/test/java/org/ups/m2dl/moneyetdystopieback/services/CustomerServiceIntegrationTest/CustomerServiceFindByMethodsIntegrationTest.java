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
    CustomerService customerService;

    Customer customerTest;

    @Test
    void whenCreateSaveCustomer_thenCanFind() throws BusinessException {

        // GIVEN
        customerTest = new Customer("pseudo12", "numberCityCountry12");

        // WHEN
        customerService.save(customerTest);
        List<Customer> resultFindBy = customerService.findByPseudo(customerTest.getPseudo());

        // THEN
        Assertions.assertEquals( 1, resultFindBy.size(), "The saved customer was not found.");
    }

    @Test
    void whenSearchCustomerDontExist_thenNoResult() {
        // GIVEN WHEN
        List<Customer> resultFindBy = customerService.findByPseudo("pseudo13");

        Assertions.assertEquals( 0, resultFindBy.size(), "A customer was returned despite his non-existence.");
    }
}
