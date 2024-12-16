package org.poo.cards;

public class OneTimeCardFactory implements CardFactory {
    /**
     * Creates a new OneTimeCard object with the specified card number
     *
     * @param cardNumber the card number for the new OneTimeCard
     * @return a new instance of OneTimeCard
     */
    @Override
    public Card createCard(final String cardNumber) {
        return new OneTimeCard(cardNumber);
    }
}
