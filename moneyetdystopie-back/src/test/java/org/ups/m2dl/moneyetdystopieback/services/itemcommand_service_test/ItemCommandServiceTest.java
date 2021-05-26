package org.ups.m2dl.moneyetdystopieback.services.itemcommand_service_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.ups.m2dl.moneyetdystopieback.domain.Item;
import org.ups.m2dl.moneyetdystopieback.domain.ItemCommand;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.ItemCommandService;

@SpringBootTest
@AutoConfigureMockMvc
class ItemCommandServiceTest {

    @Autowired
    private ItemCommandService itemCommandService;

    @ParameterizedTest
    @CsvSource({ "5,5,0", "4,3,1", "5,0,5" })
    void decreaseStock_stockDecreased(
        Integer initialAmount,
        Integer quantityToDecrease,
        Integer expectedAmount
    ) throws BusinessException {
        Item item = new Item(
            null,
            "titre",
            "photo.mail.net",
            "description",
            initialAmount,
            0f,
            null,
            null
        );
        // item = itemService.create(item);
        ItemCommand itemCommand = new ItemCommand(
            null,
            quantityToDecrease,
            item
        );

        // When
        itemCommandService.decreaseStock(itemCommand);

        // Then
        Assertions.assertEquals(expectedAmount, item.getAmount());
    }
}
