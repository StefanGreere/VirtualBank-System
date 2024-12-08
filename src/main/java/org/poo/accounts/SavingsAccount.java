package org.poo.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SavingsAccount extends Account {
    @JsonIgnore
    private double interestRate;

    public SavingsAccount(String currency, String type, double interestRate) {
        super(currency, type);
        this.interestRate = interestRate;
    }
}
