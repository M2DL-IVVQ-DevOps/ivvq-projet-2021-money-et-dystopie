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
class ItemServiceUpdateMethodIntegrationTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private SellerService sellerService;

    private Seller sellerTest;

    private int itemsNumber;

    @Test
    void whenUpdateItemWithDifferentAmount_thenItemUpdated() throws BusinessException {
        // GIVEN
        sellerTest = new Seller("storeName49", null, null, null);
        sellerService.save(sellerTest);

        Item expected =
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
        itemService.create(expected);
        itemsNumber = itemService.findAll().size();

        // WHEN
        expected.setAmount(expected.getAmount() - 1);
        Item actual = itemService.update(expected);
        // THEN
        Assertions.assertEquals(
                itemsNumber,
                itemService.findAll().size(),
                "A new item has been created instead of updating the actual one."
        );
        Assertions.assertEquals(
                expected.getAmount(),
                actual.getAmount(),
                "The update has not been taken in account."
        );
    }

    @Test
    void whenUpdateItemWithSameContent_thenItemUpdated() throws BusinessException {
        // GIVEN
        sellerTest = new Seller("storeName50", null, null, null);
        sellerService.save(sellerTest);

        Item expected =
                new Item(
                        null,
                        "title1",
                        "https://www.master-developpement-logiciel.fr/assets/images/logo-master-dl.png",
                        "description50",
                        10,
                        5.f,
                        null,
                        sellerTest
                );
        itemService.create(expected);
        itemsNumber = itemService.findAll().size();

        // WHEN
        Item actual = itemService.update(expected);
        // THEN
        Assertions.assertEquals(
                itemsNumber,
                itemService.findAll().size(),
                "A new item has been created instead of updating the actual one."
        );
        Assertions.assertEquals(
                expected.getId(),
                actual.getId(),
                "The update has not been taken in account."
        );
    }

    @Test
    void whenUpdateUnknownItem_thenThrowBusinessException() throws BusinessException {
        // GIVEN
        sellerTest = new Seller("storeName50", null, null, null);
        sellerService.save(sellerTest);

        Item expected =
            new Item(
                    88L, // Invalid ID
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
                itemService.update(expected);
            }
        );
    }

    @Test
    void whenUpdateItemNull_thenThrowBusinessException() {
        // THEN
        Assertions.assertThrows(
                BusinessException.class,
                () -> {
                    // WHEN
                    itemService.update(null);
                }
        );
    }
}
