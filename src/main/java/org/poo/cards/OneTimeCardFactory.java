package org.poo.cards;

public class OneTimeCardFactory implements CardFactory {
    @Override
    public Card createCard(String cardNumber) {
        return new OneTimeCard(cardNumber);
    }
}
