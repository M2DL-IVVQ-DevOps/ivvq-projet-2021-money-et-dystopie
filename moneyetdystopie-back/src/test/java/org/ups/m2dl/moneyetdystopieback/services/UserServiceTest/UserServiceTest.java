package org.ups.m2dl.moneyetdystopieback.services.UserServiceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.ups.m2dl.moneyetdystopieback.domain.User;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.UserRepository;
import org.ups.m2dl.moneyetdystopieback.services.CustomerService;
import org.ups.m2dl.moneyetdystopieback.services.SellerService;
import org.ups.m2dl.moneyetdystopieback.services.UserService;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SellerService sellerService;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setup() {
        userService = new UserService(userRepository, sellerService, customerService);
    }

    @Test
    void whenUseSaveMethod_thenRepositoryUserInvoked() throws BusinessException {
        // GIVEN
        User userTest = new User("lastName33", "firstName33", "email33@gmail", "password33", null, null);
        when(userService.getUserRepository().save(userTest)).thenReturn(userTest);
        // WHEN: save méthode est invoqué
        userService.save(userTest);
        // THEN: UserRepository est invoqué
        verify(userService.getUserRepository()).save(userTest);
    }

    @Test
    void whenUseFindByEmailMethod_thenRepositoryUserInvoked() {
        // GIVEN
        String emailTest = "email34@email.com";
        when(userService.getUserRepository().findByEmail(emailTest)).thenReturn(Optional.empty());
        // WHEN: save méthode est invoqué
        userService.findByEmail(emailTest);
        // THEN: UserRepository est invoqué
        verify(userService.getUserRepository()).findByEmail(emailTest);
    }
}