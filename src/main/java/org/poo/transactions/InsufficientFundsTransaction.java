package org.poo.transactions;

public class InsufficientFundsTransaction extends Transaction {
    public InsufficientFundsTransaction(int timestamp) {
        super(timestamp, "Insufficient funds");
    }
}
