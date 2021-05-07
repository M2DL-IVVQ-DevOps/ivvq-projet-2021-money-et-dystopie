package org.ups.m2dl.moneyetdystopieback.services.seller_service_integration_test;

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
    private SellerService sellerService;

    private Seller sellerTestTwinA;
    private Seller sellerTestTwinB;
    private Seller sellerTest;

    @Test
    void whenCreateSellerWithSameStoreName_thenThrowBusinessException() throws BusinessException {

        // GIVEN
        sellerTestTwinA = new Seller("storeName19",null,null,null);
        sellerTestTwinB = new Seller("storeName19",null,null,null);

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
        sellerTest = new Seller(null,null,null,null);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            sellerService.create(sellerTest);
        });
    }
}
