package org.ups.m2dl.moneyetdystopieback.services.SellerServiceIntegrationTest;

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
    private SellerService sellerService;

    private Seller sellerTest;

    @Test
    void whenSaveSeller_thenCanFindWithDatas() throws BusinessException {
        // GIVEN
        sellerTest = new Seller("storeName21",null,null,null);

        // WHEN
        sellerService.save(sellerTest);
        Seller resultFindById = sellerService.findByStoreName(sellerTest.getStoreName());

        //THEN
        Assertions.assertNotNull( resultFindById, "More or Less sellers than expected were obtained.");
        Assertions.assertEquals( sellerTest.getStoreName(), resultFindById.getStoreName(), "The information retrieved does not match that expected.");
        Assertions.assertNull( resultFindById.getUserAccount(), "The information retrieved does not match that expected.");
        Assertions.assertTrue( resultFindById.getItems().isEmpty(), "The information retrieved does not match that expected.");
        Assertions.assertTrue( resultFindById.getCommands().isEmpty(), "The information retrieved does not match that expected.");
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
        sellerTest = new Seller(null,null,null,null);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            sellerService.save(sellerTest);
        });
    }
}
