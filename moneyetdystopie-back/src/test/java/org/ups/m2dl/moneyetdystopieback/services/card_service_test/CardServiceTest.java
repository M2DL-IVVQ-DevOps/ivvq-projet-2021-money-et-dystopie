package org.ups.m2dl.moneyetdystopieback.services.card_service_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.ups.m2dl.moneyetdystopieback.services.CardService;

class CardServiceTest {

    @ParameterizedTest
    @ValueSource(
        strings = { "", "8532", "9999999999999999", "00000000000000000" }
    )
    void whenVerifyIncorrectNumber_thenCardNumberIsIncorrect(
        String cardNumber
    ) {
        CardService service = new CardService(cardNumber);
        Assertions.assertFalse(service.isCardNumberValid());
    }

    @Test
    void whenVerifyCorrectNumber_thenCardNumberIsCorrect() {
        CardService service = new CardService("9999999999999995");
        Assertions.assertTrue(service.isCardNumberValid());
    }
}
