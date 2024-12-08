package org.poo.transactions;

public class InterestRateTransaction extends Transaction {
    public InterestRateTransaction(int timestamp) {
        super(timestamp, "Interest Rate changed");
    }
}
