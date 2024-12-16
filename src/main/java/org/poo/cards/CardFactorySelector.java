package org.poo.cards;

public final class CardFactorySelector {
    /**
     * Returns the appropriate {@link CardFactory} based on the specified card type
     *
     * @param cardType The type of card for which to get the factory
     * @return an instance suitable for the specified card type
     */
    public static CardFactory getFactory(final String cardType) {
        switch (cardType) {
            case "createCard":
                return new ClassicCardFactory();

            case "createOneTimeCard":
                return new OneTimeCardFactory();

            default:
                return null;
        }
    }

    private CardFactorySelector() {
    }
}
