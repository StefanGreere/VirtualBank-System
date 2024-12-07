package org.poo.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SendMoneyTransaction extends Transaction {
    @JsonProperty("receiverIBAN")
    private String receiverIban;
    @JsonProperty("senderIBAN")
    private String senderIban;
    private String amount;
    private String transferType;

    public SendMoneyTransaction(int timestamp, String description, String receiverIban,
                                String senderIban, String amount, String transferType) {
        super(timestamp, description);
        this.receiverIban = receiverIban;
        this.senderIban = senderIban;
        this.amount = amount;
        this.transferType = transferType;
    }
}
