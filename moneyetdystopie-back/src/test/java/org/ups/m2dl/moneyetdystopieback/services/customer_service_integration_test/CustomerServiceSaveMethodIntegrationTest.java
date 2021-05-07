package org.ups.m2dl.moneyetdystopieback.services.customer_service_integration_test;

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
class CustomerServiceSaveMethodIntegrationTest {

    @Autowired
    private CustomerService customerService;

    private Customer customerTest;

    @Test
    void whenSaveCustomer_thenCanFindWithDatas() throws BusinessException {
        // GIVEN
        customerTest = new Customer("pseudo14", "numberCityCountry14", null, null, null);

        // WHEN
        customerService.save(customerTest);
        Customer resultFindById = customerService.findByPseudo(customerTest.getPseudo());

        //THEN
        Assertions.assertNotNull( resultFindById, "More or less customers than expected were obtained.");
        Assertions.assertEquals( customerTest.getPseudo(), resultFindById.getPseudo(), "The information retrieved does not match that expected.");
        Assertions.assertEquals( customerTest.getAddress(), resultFindById.getAddress(), "The information retrieved does not match that expected.");
        Assertions.assertNull( resultFindById.getCart(), "The information retrieved does not match that expected.");
        Assertions.assertTrue( resultFindById.getPastCommands().isEmpty(), "The information retrieved does not match that expected.");
        Assertions.assertNull( resultFindById.getUserAccount(), "The information retrieved does not match that expected.");
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
        customerTest = new Customer(null, "numberCityCountry15", null, null, null);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            customerService.save(customerTest);
        });
    }
}
