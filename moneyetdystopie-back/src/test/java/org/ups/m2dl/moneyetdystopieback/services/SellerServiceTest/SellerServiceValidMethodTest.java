package org.ups.m2dl.moneyetdystopieback.services.SellerServiceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.ups.m2dl.moneyetdystopieback.domain.Seller;
import org.ups.m2dl.moneyetdystopieback.exceptions.BusinessException;
import org.ups.m2dl.moneyetdystopieback.services.SellerService;

@SpringBootTest
@AutoConfigureMockMvc
class SellerServiceValidMethodTest {

    @Autowired
    SellerService sellerService;

    Seller sellerTest;

    @ParameterizedTest
    @ValueSource(strings = {"","0123456789012345678901234567890"})
    @NullSource
    void whenValidSellerWithBadStoreName_thenThrowBusinessException(String storeName)  {

        // GIVEN
        sellerTest = new Seller(storeName);

        // THEN
        Assertions.assertThrows(BusinessException.class, () -> {
            // WHEN
            sellerService.valid(sellerTest);
        });
    }

    @Test
    void whenValidSeller_thenNoThrowBusinessException() {
        // GIVEN
        sellerTest = new Seller("storeName23");

        //WHEN
        Assertions.assertDoesNotThrow( () -> {
            sellerService.valid(sellerTest);
        });
    }

}