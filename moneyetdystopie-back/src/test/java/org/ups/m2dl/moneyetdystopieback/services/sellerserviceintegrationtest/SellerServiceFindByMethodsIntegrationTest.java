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
    SellerService sellerService;

    Seller sellerTest;

    @Test
    void whenCreateSaveSeller_thenCanFind() throws BusinessException {

        // GIVEN
        sellerTest = new Seller("storeName20");

        // WHEN
        sellerService.save(sellerTest);
        List<Seller> resultFindBy = sellerService.findByStoreName(sellerTest.getStoreName());

        // THEN
        Assertions.assertEquals( 1, resultFindBy.size(), "The saved seller was not found.");
    }

    @Test
    void whenSearchCustomerDontExist_thenNoResult() {
        // GIVEN WHEN
        List<Seller> resultFindBy = sellerService.findByStoreName("Bonjour");

        Assertions.assertEquals( 0, resultFindBy.size(), "A seller was returned despite his non-existence.");
    }
}
