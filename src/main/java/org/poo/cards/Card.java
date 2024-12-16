package org.poo.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Card {
    private String cardNumber;
    private String status;

    public Card(final String cardNumber, final String status) {
        this.cardNumber = cardNumber;
        this.status = status;
    }

    /**
     * Returns the type of the card
     *
     * @return the card type as a {@code String}
     */
    @JsonIgnore
    public abstract String getCardType();
}
