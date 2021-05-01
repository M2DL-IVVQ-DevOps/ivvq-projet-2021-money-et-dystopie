package org.ups.m2dl.moneyetdystopieback.services.SellerServiceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.repositories.SellerRepository;
import org.ups.m2dl.moneyetdystopieback.services.SellerService;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class SellerServiceTest {

    @Autowired
    private SellerService sellerService;

    @MockBean
    private SellerRepository sellerRepository;

    @BeforeEach
    void setup() {
        sellerService = new SellerService(sellerRepository);
    }

    @Test
    void whenUseSaveMethod_thenRepositorySellerInvoked() throws BusinessException {
        // GIVEN
        Seller sellerTest = new Seller("storeName21",null,null,null);
        when(sellerService.getSellerRepository().save(sellerTest)).thenReturn(sellerTest);
        // WHEN: save méthode est invoqué
        sellerService.save(sellerTest);
        // THEN: SellerRepository est invoqué
        verify(sellerService.getSellerRepository()).save(sellerTest);
    }

    @Test
    void whenUseFindByPseudoMethod_thenRepositorySellerInvoked() {
        // GIVEN
        String storeNameTest = "storeName22";
        when(sellerService.getSellerRepository().findByStoreName(storeNameTest)).thenReturn(Optional.empty());
        // WHEN: save méthode est invoqué
        sellerService.findByStoreName(storeNameTest);
        // THEN: CustomerRepository est invoqué
        verify(sellerService.getSellerRepository()).findByStoreName(storeNameTest);
    }

}