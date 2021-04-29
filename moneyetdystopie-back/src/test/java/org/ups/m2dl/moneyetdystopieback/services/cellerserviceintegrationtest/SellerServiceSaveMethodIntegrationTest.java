package org.ups.m2dl.moneyetdystopieback.services.cellerserviceintegrationtest;

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
class SellerServiceSaveMethodIntegrationTest {

    @Autowired
    SellerService sellerService;

    Seller sellerTest;

    @Test
    void whenSaveSeller_thenCanFind() throws BusinessException {
        // GIVEN
        sellerTest = new Seller("storeName21");

        // WHEN
        sellerService.save(sellerTest);
        List<Seller> resultFindById = sellerService.findByStoreName(sellerTest.getStoreName());

        //THEN
        Assertions.assertEquals( 1, resultFindById.size(), "More or Less sellers than expected were obtained.");
        Assertions.assertEquals( sellerTest.getStoreName(), resultFindById.get(0).getStoreName(), "The information retrieved does not match that expected.");
        Assertions.assertNull( resultFindById.get(0).getUserAccount(), "The information retrieved does not match that expected.");
        Assertions.assertEquals( 0,  resultFindById.get(0).getItems().size(), "The information retrieved does not match that expected.");
        Assertions.assertEquals( 0, resultFindById.get(0).getCommands().size(), "The information retrieved does not match that expected.");
    }

    @Test
    void whenCreateSellerNull_thenThrowBusinessException() {
        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            sellerService.save(null);
        });
    }

    @Test
    void whenSaveSellerWithoutStoreName_thenThrowBusinessException() {
        // GIVEN
        sellerTest = new Seller(null);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            sellerService.save(sellerTest);
        });
    }
}
