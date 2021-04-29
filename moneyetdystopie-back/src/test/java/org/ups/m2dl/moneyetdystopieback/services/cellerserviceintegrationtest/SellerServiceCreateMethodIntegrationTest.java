package org.ups.m2dl.moneyetdystopieback.services.cellerserviceintegrationtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.SellerService;

@SpringBootTest
@AutoConfigureMockMvc
class SellerServiceCreateMethodIntegrationTest {

    @Autowired
    SellerService sellerService;

    Seller sellerTestTwinA;
    Seller sellerTestTwinB;
    Seller sellerTest;

    @Test
    void whenCreateSellerWithSameStoreName_thenThrowBusinessException() throws BusinessException {

        // GIVEN
        sellerTestTwinA = new Seller("storeName19");
        sellerTestTwinB = new Seller("storeName19");

        // WHEN
        sellerService.create(sellerTestTwinA);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            sellerService.create(sellerTestTwinB);
        });
    }

    @Test
    void whenCreateSellerWithoutStoreName_thenThrowBusinessException() {
        // GIVEN
        sellerTest = new Seller(null);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            sellerService.create(sellerTest);
        });
    }
}
