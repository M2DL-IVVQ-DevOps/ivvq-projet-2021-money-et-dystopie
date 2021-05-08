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
class ItemServiceCreateMethodIntegrationTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private SellerService sellerService;

    private Item itemTestTwinA;
    private Item itemTestTwinB;
    private Item itemTest;

    private Seller sellerTest;

    private int itemsNumber;

    @Test
    void whenCreateItemWithSameDatas_thenItemCreate() throws BusinessException {
        // GIVEN
        sellerTest = new Seller("storeName49", null, null, null);
        sellerService.save(sellerTest);

        itemTestTwinA =
            new Item(
                null,
                "title49",
                "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
                "description49",
                10,
                5.f,
                null,
                sellerTest
            );
        itemTestTwinB =
            new Item(
                null,
                "title49",
                "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
                "description49",
                10,
                5.f,
                null,
                sellerTest
            );

        itemsNumber = itemService.findAll().size();

        // WHEN
        itemService.create(itemTestTwinA);
        itemService.create(itemTestTwinB);

        // THEN
        Assertions.assertEquals(
            itemsNumber + 2,
            itemService.findAll().size(),
            "Twin items are registered."
        );
    }

    @Test
    void whenCreateItemWithoutSeller_thenThrowBusinessException() {
        // GIVEN
        itemTest =
            new Item(
                null,
                "title50",
                "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
                "description50",
                10,
                5.f,
                null,
                null
            );

        // THEN
        Assertions.assertThrows(
            BusinessException.class,
            () -> {
                // WHEN
                itemService.create(itemTest);
            }
        );
    }
}
