package org.poo.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.poo.cards.Card;
import org.poo.cards.CardFactory;
import org.poo.cards.CardFactorySelector;
import org.poo.transactions.Commerciant;
import org.poo.transactions.Transaction;
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
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();
    @JsonIgnore
    private List<Commerciant> commerciants = new ArrayList<>();

    public Account(final String currency, final String type) {
        this.currency = currency;
        this.type = type;
        this.iban = Utils.generateIBAN();
        this.balance = 0.0; // the balance starts from 0
    }

    /**
     * Adds a new card to the list of cards
     *
     * @param cardType the type of the card
     * @param cardNumber the number of the card to be created
     *
     * If the card type is invalid, no card is added.
     */
    public void addCard(final String cardType, final String cardNumber) {
        // get the correct factory based on the type of the card
        CardFactory factory = CardFactorySelector.getFactory(cardType);

        if (factory == null) {
            return;
        }

        // create the card with the card number specified
        Card card = factory.createCard(cardNumber);

        // add to the card map the new card created
        cards.add(card);
    }

    /**
     * Finds the index of a card in the list based on its card number
     *
     * @param cardNumber the card number to search for
     * @return the index of the card if found or -1 if the card is not in the list
     */
    public int findIndexCardByNumber(final String cardNumber) {
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
