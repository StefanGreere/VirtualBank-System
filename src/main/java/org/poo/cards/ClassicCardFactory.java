package org.poo.cards;

public class ClassicCardFactory implements CardFactory {
    /**
     * Creates a new ClassicCard object with the specified card number
     *
     * @param cardNumber the card number for the new ClassicCard
     * @return a new instance of ClassicCard
     */
    @Override
    public Card createCard(final String cardNumber) {
        return new ClassicCard(cardNumber);
    }
}
