package org.poo.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.poo.accounts.Account;
import org.poo.accounts.ClassicAccount;
import org.poo.accounts.SavingsAccount;
import org.poo.cards.Card;
import org.poo.transactions.Transaction;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private List<Account> accounts = new ArrayList<>();
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();

    public User(final String firstName, final String lastName, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Adds a new account to the list of accounts
     *
     * @param type the type of the account
     * @param currency the currency of the account
     * @param interestRate the interest rate for the account (used only for savings accounts)
     */
    public void addAccount(final String type, final String currency, final double interestRate) {
        if (type.equals("classic")) {
            Account newAccount = new ClassicAccount(currency, type);
            accounts.add(newAccount);
        } else {
            Account newAccount = new SavingsAccount(currency, type, interestRate);
            accounts.add(newAccount);
        }
    }

    /**
     * Finds an account by IBAN
     *
     * @param iban the IBAN of the account to search for
     * @return the account with the specified IBAN or null if no account has found
     */
    public Account findAccountByIban(final String iban) {
        for (Account account : accounts) {
            if (account.getIban().equals(iban)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Finds an account with a given card number
     *
     * @param cardNumber the card number to search for
     * @return the account that contains the card or null if no account has found
     */
    public Account findAccountByCardNumber(final String cardNumber) {
        for (Account account : accounts) {
            int index = account.findIndexCardByNumber(cardNumber);

            if (index != -1) {
                // the card exist, and belongs to an account
                return  account;
            }
        }

        return null;
    }

    /**
     * Deletes an account with the specified IBAN
     *
     * @param iban the IBAN of the account to be deleted
     */
    public void deleteAccount(final String iban) {
        for (int index = 0; index < accounts.size(); index++) {
            if (accounts.get(index).getIban().equals(iban)) {
                accounts.remove(index);
                break;
            }
        }
    }

    /**
     * Finds a card with the specified card number
     *
     * @param cardNumber the number of the card to search for
     * @return the card if found or null if no card has found
     */
    public Card findCard(final String cardNumber) {
        for (Account account : accounts) {
            int index = account.findIndexCardByNumber(cardNumber);

            if (index != -1) {
                return account.getCards().get(index);
            }
        }

        return null;
    }
}
