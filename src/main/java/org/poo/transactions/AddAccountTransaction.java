package org.poo.transactions;

public class AddAccountTransaction extends Transaction {
    public AddAccountTransaction(final int timestamp) {
        super(timestamp, "New account created");
    }
}
