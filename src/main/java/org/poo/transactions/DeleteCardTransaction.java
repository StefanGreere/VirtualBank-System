package org.poo.transactions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeleteCardTransaction extends Transaction {
    private String account;
    private String card;
    private String cardHolder;

    public DeleteCardTransaction(final int timestamp, final String account,
                                 final String card, final String cardHolder) {
        super(timestamp, "The card has been destroyed");
        this.account = account;
        this.card = card;
        this.cardHolder = cardHolder;
    }
}
