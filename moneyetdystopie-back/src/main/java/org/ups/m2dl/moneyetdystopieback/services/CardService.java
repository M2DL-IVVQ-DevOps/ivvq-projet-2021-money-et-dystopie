package org.ups.m2dl.moneyetdystopieback.services;

import java.util.Arrays;
import javax.validation.constraints.NotNull;

public class CardService {

    /** Le nombre de numéros attendus pour un numéro de CB correct */
    private static final int CARD_NUMBER_LENGTH = 16;

    private final int[] cardNumber;

    public CardService(@NotNull String cardNumberToVerify) {
        cardNumber =
            cardNumberToVerify
                .chars()
                .map(Character::getNumericValue)
                .toArray();
    }

    private void applyLuhnAlgorithm() {
        //         Doubler un chiffre sur deux en partant de l'avant-dernier jusqu'au début
        for (
            int position = cardNumber.length - 2;
            position >= 0;
            position -= 2
        ) {
            int digitDoubled = cardNumber[position] * 2;
            //        Si le double d'un chiffre est supérieur ou égal à 10, lui retirer 9.
            if (digitDoubled >= 10) {
                cardNumber[position] = digitDoubled - 9;
            } else {
                cardNumber[position] = digitDoubled;
            }
        }
    }

    private boolean verifyLuhnAlgorithmResult() {
        int sum = Arrays.stream(cardNumber).sum();
        return sum % 10 == 0;
    }

    public boolean isCardNumberValid() {
        if (cardNumber.length != CARD_NUMBER_LENGTH) {
            return false;
        }
        applyLuhnAlgorithm();
        return verifyLuhnAlgorithmResult();
    }
}
