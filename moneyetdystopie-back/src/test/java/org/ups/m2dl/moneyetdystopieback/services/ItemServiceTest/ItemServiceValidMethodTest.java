package org.ups.m2dl.moneyetdystopieback.services.ItemServiceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
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
class ItemServiceValidMethodTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private SellerService sellerService;

    private Seller sellerTest;
    
    private Item itemTest;

    @BeforeEach
    void setup() throws BusinessException {
        sellerTest =  new Seller("storeName54",null,null,null);
        sellerService.save(sellerTest);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1","01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"})
    @NullSource
    void whenValidItemWithBadTitle_thenThrowBusinessException(String title)  {

        // GIVEN
        itemTest = new Item( null, title, "https://moodle.univ-tlse3.fr/course/view.php?id=1653", "description55", 10, 5.f, null, sellerTest);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            itemService.valid(itemTest);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"1","012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"})
    @NullSource
    void whenValidItemWithBadDescription_thenThrowBusinessException(String description)  {

        // GIVEN
        itemTest = new Item( null, "title56", "https://moodle.univ-tlse3.fr/course/view.php?id=1653", description, 10, 5.f, null, sellerTest);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            itemService.valid(itemTest);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"uneUrlFausse","httpsmoodle.univ-tlse3.fr/course/view.php?id=1653"})
    void whenValidItemWithBadPicture_thenThrowBusinessException(String picture)  {

        // GIVEN
        itemTest = new Item( null, "title53", picture, "description53", 10, 5.f, null, sellerTest);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // THEN
            itemService.valid(itemTest);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {-1})
    @NullSource
    void whenValidItemWithBadAmount_thenThrowBusinessException(Integer amount)  {

        // GIVEN
        itemTest = new Item( null, "title53", "https://moodle.univ-tlse3.fr/course/view.php?id=1653", "description53", amount, 5.f, null, sellerTest);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // THEN
            itemService.valid(itemTest);
        });
    }

    @ParameterizedTest
    @ValueSource(floats = {-1.f})
    @NullSource
    void whenValidItemWithBadAmount_thenThrowBusinessException(Float price)  {

        // GIVEN
        itemTest = new Item( null, "title53", "https://moodle.univ-tlse3.fr/course/view.php?id=1653", "description53", 10, price, null, sellerTest);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // THEN
            itemService.valid(itemTest);
        });
    }


    @Test
    void whenValidCustomer_thenNoThrowBusinessException() {
        // GIVEN
        itemTest = new Item( null, "title53", "https://moodle.univ-tlse3.fr/course/view.php?id=1653", "description53", 10, 5.f, null, sellerTest);

        // THEN
        Assertions.assertDoesNotThrow( () -> {
            // WHEN
            itemService.valid(itemTest);
        });
    }
}