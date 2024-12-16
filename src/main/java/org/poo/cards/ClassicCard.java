package org.poo.cards;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClassicCard extends Card {
    public ClassicCard(final String cardNumber) {
        super(cardNumber, "active"); // the card is active at first
    }

    /**
     * Returns the type of the card
     * This implementation returns "classic" to indicate that the card is of classic type
     *
     * @return a string representing the type of the card
     */
    @Override
    public String getCardType() {
        return "classic";
    }
}
