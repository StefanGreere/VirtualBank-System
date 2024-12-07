package org.poo.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Card {
    private String cardNumber;
    private String status;

    public Card(String cardNumber, String status) {
        this.cardNumber = cardNumber;
        this.status = status;
    }

    @JsonIgnore
    public abstract String getCardType();
}
