package org.poo.cards;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OneTimeCard extends Card {
    public OneTimeCard(String cardNumber) {
        super(cardNumber, "active");
    }

    @Override
    public String getCardType() {
        return "oneTimePay";
    }
}
