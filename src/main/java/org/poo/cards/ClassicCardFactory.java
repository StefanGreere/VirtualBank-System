package org.poo.cards;

public class ClassicCardFactory implements CardFactory {
    @Override
    public Card createCard(String cardNumber) {
        return new ClassicCard(cardNumber);
    }
}
