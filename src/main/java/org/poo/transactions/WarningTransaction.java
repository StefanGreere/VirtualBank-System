package org.poo.transactions;

public class WarningTransaction extends Transaction {
    public WarningTransaction(final int timestamp) {
        super(timestamp, "You have reached the minimum amount of funds, the card will be frozen");
    }
}
