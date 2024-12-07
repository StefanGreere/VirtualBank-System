package org.poo.cards;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClassicCard extends Card {
    public ClassicCard(String cardNumber) {
        super(cardNumber, "active");
    }

    @Override
    public String getCardType() {
        return "classic";
    }
}
