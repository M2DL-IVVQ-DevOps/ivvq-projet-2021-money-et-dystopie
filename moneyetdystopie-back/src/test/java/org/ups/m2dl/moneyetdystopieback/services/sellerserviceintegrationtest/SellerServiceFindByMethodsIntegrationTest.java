package org.ups.m2dl.moneyetdystopieback.services.sellerserviceintegrationtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.SellerService;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class SellerServiceFindByMethodsIntegrationTest {

    @Autowired
    private SellerService sellerService;

    private Seller sellerTest;

    @Test
    void whenCreateSaveSeller_thenCanFind() throws BusinessException {

        // GIVEN
        sellerTest = new Seller("storeName20",null,null,null);

        // WHEN
        sellerService.save(sellerTest);
        Seller resultFindBy = sellerService.findByStoreName(sellerTest.getStoreName());

        // THEN
        Assertions.assertNotNull( resultFindBy, "The saved seller was not found.");
    }

    @Test
    void whenSearchCustomerDontExist_thenNoResult() {
        // GIVEN WHEN
        Seller resultFindBy = sellerService.findByStoreName("Bonjour");

        Assertions.assertNull( resultFindBy, "A seller was returned despite his non-existence.");
    }
}
