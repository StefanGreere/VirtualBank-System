package org.poo.transactions;

public class ChangeRateTransaction extends Transaction {
    public ChangeRateTransaction(final int timestamp, final double rate) {
        super(timestamp, "Interest rate of the account changed to " + rate);
    }
}
