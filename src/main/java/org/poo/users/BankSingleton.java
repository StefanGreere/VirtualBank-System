package org.poo.users;

import lombok.Getter;
import lombok.Setter;
import org.poo.accounts.Account;
import org.poo.fileio.UserInput;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter
public class BankSingleton {
    private static BankSingleton instance;
    private Map<String, User> users;    // the key is the email

    private BankSingleton() {
        this.users = new LinkedHashMap<>();
    }

    public static BankSingleton getInstance() {
        if (instance == null) {
            instance = new BankSingleton();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public boolean hasUser(String email) {
        return users.containsKey(email);
    }

    public void addAllUsers(UserInput[] usersInput) {
        for (UserInput userInput : usersInput) {
            User newUser = new User(userInput.getFirstName(), userInput.getLastName(), userInput.getEmail());
            this.users.put(userInput.getEmail(), newUser);
        }
    }

    public Account findAccountUserByIban(String iban) {
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
}
