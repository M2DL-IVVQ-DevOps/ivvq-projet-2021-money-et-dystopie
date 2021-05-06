package org.ups.m2dl.moneyetdystopieback.services.ItemServiceIntegrationTest;

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
class ItemServiceSaveMethodIntegrationTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private SellerService sellerService;

    private Item itemTest;
    private Seller sellerTest;

    @Test
    void whenSaveItem_thenCanFindWithDatas() throws BusinessException {
        // GIVEN
        sellerTest =  new Seller("storeName51",null,null,null);
        sellerService.save(sellerTest);
        itemTest = new Item(null, "title51", "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png", "description51", 10, 5.f, null, sellerTest);

        // WHEN
        itemTest = itemService.save(itemTest);
        Item resultFindById = itemService.findById(itemTest.getId());

        //THEN
        Assertions.assertNotNull( resultFindById, "Less item than expected were obtained.");
        Assertions.assertEquals( itemTest.getTitle(), resultFindById.getTitle(), "The information retrieved does not match that expected.");
        Assertions.assertEquals( itemTest.getPicture(), resultFindById.getPicture(), "The information retrieved does not match that expected.");
        Assertions.assertEquals( itemTest.getAmount(), resultFindById.getAmount(), "The information retrieved does not match that expected.");
        Assertions.assertEquals( itemTest.getPrice(), resultFindById.getPrice(), "The information retrieved does not match that expected.");
    }

    @Test
    void whenCreateItemNull_thenThrowBusinessException() {
        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            itemService.save(null);
        });
    }
}
