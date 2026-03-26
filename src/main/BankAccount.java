package main;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private double balance;
    private boolean isOpen;
    private List<BankAccount> additionalAccounts;
    private List<String> transactionHistory;

    public BankAccount() {
        this.balance = 0;
        this.additionalAccounts = new ArrayList<>();
        this.transactionHistory = new ArrayList<>();
        this.isOpen = true; 
    }
        

    public void deposit(double amount) {
        if (!isOpen) {
            throw new IllegalStateException("Account is closed");
        }
        if(amount > 0) {
            this.balance += amount;
            this.transactionHistory.add("Deposited: " + amount);
        } else {
             throw new IllegalArgumentException();
        }
    }
public void withdraw(double amount) {
    if (!isOpen) {
        throw new IllegalStateException("Account is closed");
    }
    if (amount <= 0 || amount > balance) {
        throw new IllegalArgumentException();
    }
    balance -= amount;
    this.transactionHistory.add("Withdrew: " + amount);
}


    public void close() {
        this.isOpen = false;
    }

    public double getBalance() {
        return this.balance;
    }

    public void transfer(BankAccount recipient, double amount) {
        if(amount > 0 && this.balance >= amount && recipient != null && recipient != this) {
            this.balance -= amount;
            recipient.deposit(amount);
        } else {
            throw new IllegalArgumentException();
        }
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
