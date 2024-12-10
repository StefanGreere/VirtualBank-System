package org.poo.transactions;

public class ExistFundsTransaction extends Transaction {
    public ExistFundsTransaction(int timestamp) {
        super(timestamp, "Account couldn't be deleted - there are funds remaining");
    }
}
