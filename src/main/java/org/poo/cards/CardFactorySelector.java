package org.poo.cards;

public class CardFactorySelector {
    public static CardFactory getFactory(String cardType) {
        switch (cardType) {
            case "createCard":
                return new ClassicCardFactory();

            case "createOneTimeCard":
                return new OneTimeCardFactory();

            default:
                return null;
        }
    }
}
