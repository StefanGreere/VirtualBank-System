package org.poo.transactions;

public class InterestRateTransaction extends Transaction {
    public InterestRateTransaction(final int timestamp) {
        super(timestamp, "Interest Rate changed");
    }
}
