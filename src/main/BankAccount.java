package main;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private double balance;
    private List<BankAccount> additionalAccounts;

    public BankAccount() {
        this.balance = 0;
        this.additionalAccounts = new ArrayList<>();
    }

    public void deposit(double amount) {
        if(amount > 0) {
            this.balance += amount;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double getBalance() {
        return this.balance;
    }

    public void addAccount() {
        BankAccount additionalAccount = new BankAccount();
        this.additionalAccounts.add(additionalAccount);
        
    }

    public List<BankAccount> getAdditionalAccounts() {
        return this.additionalAccounts;
    }
}
