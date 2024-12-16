package org.poo.transactions;

public class FrozenCardTransaction extends Transaction {
    public FrozenCardTransaction(final int timestamp) {
        super(timestamp, "The card is frozen");
    }
}
