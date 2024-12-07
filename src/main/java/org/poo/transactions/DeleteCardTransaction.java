package org.poo.transactions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeleteCardTransaction extends Transaction {
    private String account;
    private String card;
    private String cardHolder;

    public DeleteCardTransaction(int timestamp, String account, String card, String cardHolder) {
        super(timestamp, "The card has been destroyed");
        this.account = account;
        this.card = card;
        this.cardHolder = cardHolder;
    }
}
