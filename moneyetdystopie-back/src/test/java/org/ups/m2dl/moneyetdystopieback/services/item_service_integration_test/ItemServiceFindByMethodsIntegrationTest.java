package org.ups.m2dl.moneyetdystopieback.services.item_service_integration_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.ups.m2dl.moneyetdystopieback.domain.Item;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.ItemService;
import org.ups.m2dl.moneyetdystopieback.services.SellerService;

@SpringBootTest
@AutoConfigureMockMvc
class ItemServiceFindByMethodsIntegrationTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private SellerService sellerService;

    private Item itemTest;
    private Seller sellerTest;
    private Item resultFindBy;
    @Test
    void whenCreateSaveItem_thenCanFind() throws BusinessException {

        // GIVEN
        sellerTest =  new Seller("storeName50",null,null,null);
        sellerService.save(sellerTest);
        itemTest = new Item(null, "title50", "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png", "description50", 10, 5.f, null, sellerTest);

        // WHEN
        itemTest = itemService.save(itemTest);
        resultFindBy = itemService.findById(itemTest.getId());

        // THEN
        Assertions.assertNotNull( resultFindBy, "The saved item was not found.");
    }

    @Test
    void whenSearchItemDontExist_thenNoResult() {
        // GIVEN WHEN
        resultFindBy = itemService.findById(0L);

        Assertions.assertNull( resultFindBy, "A item was returned despite his non-existence.");
    }
}
