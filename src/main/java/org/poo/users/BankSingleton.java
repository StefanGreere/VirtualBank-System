package org.poo.users;

import lombok.Getter;
import lombok.Setter;
import org.poo.accounts.Account;
import org.poo.cards.Card;
import org.poo.fileio.UserInput;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter
public final class BankSingleton {
    private static BankSingleton instance;
    private Map<String, User> users;    // the key is the email

    private BankSingleton() {
        this.users = new LinkedHashMap<>();
    }

    /**
     * Returns the singleton instance of the BankSingleton
     * If the instance does not already exist, it is created
     *
     * @return the singleton instance of BankSingleton
     */
    public static BankSingleton getInstance() {
        if (instance == null) {
            instance = new BankSingleton();
        }
        return instance;
    }

    /**
     * Resets the singleton instance
     */
    public static void resetInstance() {
        instance = null;
    }

    /**
     * Adds all users from the UserInput to the bank's user hashmap
     *
     * @param usersInput an array of UserInput objects
     */
    public void addAllUsers(final UserInput[] usersInput) {
        for (UserInput userInput : usersInput) {
            User newUser = new User(userInput.getFirstName(),
                            userInput.getLastName(), userInput.getEmail());
            this.users.put(userInput.getEmail(), newUser);
        }
    }

    /**
     * Returns the account with the given IBAN
     *
     * @param iban the IBAN of the account
     * @return the Account object if found or null if no account has found
     */
    public Account findAccountUserByIban(final String iban) {
        for (User user : users.values()) {
            Account account = user.findAccountByIban(iban);

            if (account != null) {
                if (account.getIban().equals(iban)) {
                    return account;
                }
            }
        }
        return null;
    }

    /**
     * Returns the card with the given card number
     *
     * @param cardNumber the card number to search for
     * @return the Card object if found or null if no card has found
     */
    public Card findCardByCardNumber(final String cardNumber) {
        for (User user : users.values()) {
            Card card = user.findCard(cardNumber);

            if (card != null) {
                return card;
            }
        }

        return null;
    }

    /**
     * Returns the account with the given card number
     *
     * @param cardNumber the card number to search for
     * @return the Account object if found or null if no account has found
     */
    public Account findAccountByCardNumber(final String cardNumber) {
        for (User user : users.values()) {
            Account account = user.findAccountByCardNumber(cardNumber);

            if (account != null) {
                return account;
            }
        }

        return null;
    }

    /**
     * Returns the user with the given IBAN
     *
     * @param iban the IBAN to search for
     * @return the User object if found or null if no user has found
     */
    public User findUserByIban(final String iban) {
        for (User user : users.values()) {
            Account account = user.findAccountByIban(iban);

            if (account != null) {
                return user;
            }
        }

        return null;
    }
}
