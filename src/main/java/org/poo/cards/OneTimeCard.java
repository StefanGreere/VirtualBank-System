package org.poo.cards;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OneTimeCard extends Card {
    public OneTimeCard(final String cardNumber) {
        super(cardNumber, "active");
    }

    /**
     * Returns the type of the card
     * This implementation returns "oneTimePay" to indicate that the card is of one time pay type
     *
     * @return a string representing the type of the card
     */
    @Override
    public String getCardType() {
        return "oneTimePay";
    }
}
