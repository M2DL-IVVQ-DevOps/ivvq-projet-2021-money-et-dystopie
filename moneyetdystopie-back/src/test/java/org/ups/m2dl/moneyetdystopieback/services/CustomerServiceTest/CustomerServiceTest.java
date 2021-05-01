package org.ups.m2dl.moneyetdystopieback.services.CustomerServiceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.ups.m2dl.moneyetdystopieback.domain.Customer;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.CustomerRepository;
import org.ups.m2dl.moneyetdystopieback.services.CustomerService;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @BeforeEach
    void setup() {
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void whenUseSaveMethod_thenRepositoryCustomerInvoked() throws BusinessException {
        // GIVEN
        Customer customerTest = new Customer("pseudo16", "numberCityCountry16", null, null, null);
        when(customerService.getCustomerRepository().save(customerTest)).thenReturn(customerTest);
        // WHEN: save méthode est invoqué
        customerService.save(customerTest);
        // THEN: CustomerRepository est invoqué
        verify(customerService.getCustomerRepository()).save(customerTest);
    }

    @Test
    void whenUseFindByPseudoMethod_thenRepositoryCustomerInvoked() {
        // GIVEN
        String pseudoTest = "pseudo17";
        when(customerService.getCustomerRepository().findByPseudo(pseudoTest)).thenReturn(Optional.empty());
        // WHEN: save méthode est invoqué
        customerService.findByPseudo(pseudoTest);
        // THEN: CustomerRepository est invoqué
        verify(customerService.getCustomerRepository()).findByPseudo(pseudoTest);
    }

}