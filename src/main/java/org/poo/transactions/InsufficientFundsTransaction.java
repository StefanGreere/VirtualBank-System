package org.poo.transactions;

public class InsufficientFundsTransaction extends Transaction {
    public InsufficientFundsTransaction(final int timestamp) {
        super(timestamp, "Insufficient funds");
    }
}
