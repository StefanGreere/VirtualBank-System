package org.poo.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.poo.cards.Card;
import org.poo.cards.CardFactory;
import org.poo.cards.CardFactorySelector;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public abstract class Account {
    @JsonProperty("IBAN")
    private String iban;
    private double balance;
    private String currency;
    private String type;
    private List<Card> cards = new ArrayList<>();
    @JsonIgnore
    private double minBalance;
    @JsonIgnore
    private String alias;

    public Account(String currency, String type) {
        this.currency = currency;
        this.type = type;
        this.iban = Utils.generateIBAN();
        this.balance = 0.0; // the balance starts from 0
    }

    public void addCard(String cardType, String cardNumber) {
        // get the correct factory based on the type of the card
        CardFactory factory = CardFactorySelector.getFactory(cardType);

        if (factory == null)
            return;

        // create the card with the card number specified
        Card card = factory.createCard(cardNumber);

        // add to the card map the new card created
        cards.add(card);
    }

    public int findIndexCardByNumber(String cardNumber) {
        int index = 0;
        for (Card card : cards) {
            if (card.getCardNumber().equals(cardNumber)) {
                return index;
            }
            index++;
        }

        return -1;
    }
}
