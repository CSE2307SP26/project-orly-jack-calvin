package main;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private double balance;
    private List<BankAccount> additionalAccounts;
    private List<String> transactionHistory;

    public BankAccount() {
        this.balance = 0;
        this.additionalAccounts = new ArrayList<>();
        this.transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        if(amount > 0) {
            this.balance += amount;
            this.transactionHistory.add("Deposited: " + amount);
        } else {
            throw new IllegalArgumentException();
        }
    }
    public void withdraw(double amount) {
        balance -= amount;
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
    public List<String> transactionHistory() {
        return this.transactionHistory;
    }

}
