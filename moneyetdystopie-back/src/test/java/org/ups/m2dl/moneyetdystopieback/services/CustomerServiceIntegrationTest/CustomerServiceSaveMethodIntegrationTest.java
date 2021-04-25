package org.ups.m2dl.moneyetdystopieback.services.CustomerServiceIntegrationTest;

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
class CustomerServiceSaveMethodIntegrationTest {

    @Autowired
    CustomerService customerService;

    Customer customerTest;

    @Test
    void whenSaveCustomer_thenCanFind() throws BusinessException {
        // GIVEN
        customerTest = new Customer("pseudo14", "numberCityCountry14");

        // WHEN
        customerService.save(customerTest);
        List<Customer> resultFindById = customerService.findByPseudo(customerTest.getPseudo());

        //THEN
        Assertions.assertEquals( 1, resultFindById.size(), "More or less customers than expected were obtained.");
        Assertions.assertEquals( customerTest.getPseudo(), resultFindById.get(0).getPseudo(), "The information retrieved does not match that expected.");
        Assertions.assertEquals( customerTest.getAddress(), resultFindById.get(0).getAddress(), "The information retrieved does not match that expected.");
        Assertions.assertNull( resultFindById.get(0).getCart(), "The information retrieved does not match that expected.");
        Assertions.assertEquals( 0, resultFindById.get(0).getPastCommands().size(), "The information retrieved does not match that expected.");
        Assertions.assertNull( resultFindById.get(0).getUserAccount(), "The information retrieved does not match that expected.");
    }

    @Test
    void whenCreateCustomerNull_thenThrowBusinessException() {
        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            customerService.save(null);
        });
    }

    @Test
    void whenSaveCustomerWithoutPseudo_thenThrowBusinessException() {
        // GIVEN
        customerTest = new Customer(null, "numberCityCountry15");

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            customerService.save(customerTest);
        });
    }
}
