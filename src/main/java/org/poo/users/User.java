package org.poo.users;

import lombok.Getter;
import lombok.Setter;
import org.poo.accounts.Account;
import org.poo.accounts.ClassicAccount;
import org.poo.accounts.SavingsAccount;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private List<Account> accounts = new ArrayList<>();

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // metoda care sa adauge un cont
    public void addAccount(String type, String currency, double interestRate) {
        if (type.equals("classic")) {
            Account newAccount = new ClassicAccount(currency, type);
            accounts.add(newAccount);
        } else {
            Account newAccount = new SavingsAccount(currency, type, interestRate);
            accounts.add(newAccount);
        }
    }

    public Account findAccountByIban(String iban) {
        for (Account account : accounts) {
            if (account.getIban().equals(iban)) {
                return account;
            }
        }
        return null;
    }

    public Account findAccountByCardNumber(String cardNumber) {
        for (Account account : accounts) {
            int index = account.findIndexCardByNumber(cardNumber);

            if (index != -1) {
                // the card exist, and belongs to an account
                return  account;
            }
        }

        return null;
    }

    public void deleteAccount(String iban) {
        for (int index = 0; index < accounts.size(); index++) {
            if (accounts.get(index).getIban().equals(iban)) {
                accounts.remove(index);
                break;
            }
        }
    }
}
