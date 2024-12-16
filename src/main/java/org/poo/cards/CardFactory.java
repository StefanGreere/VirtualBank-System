package org.poo.cards;

public interface CardFactory {
    /**
     * Creates a card instance based on the given card number.
     *
     * @param cardNumber the unique number associated with the card
     * @return a {@code Card} object
     */
    Card createCard(String cardNumber);
}
