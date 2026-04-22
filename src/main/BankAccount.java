package main;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private double balance;
    private String name;
    private boolean isOpen;
    private List<String> transactionHistory;
    private boolean isFrozen = false;

    public BankAccount() {
        this.name = "Account1";
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
        this.isOpen = true; 
    }
        

    public void deposit(double amount) {
        if (!isOpen || isFrozen) {
            throw new IllegalStateException("Account is closedor frozen");
        }
        if(amount > 0) {
            this.balance += amount;
            this.transactionHistory.add("Deposited: " + amount);
        } else {
             throw new IllegalArgumentException();
        }
    }
    public void withdraw(double amount) {
        if (!isOpen || isFrozen) {
            throw new IllegalStateException("Account is closedor frozen");
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

    public void setFrozen(boolean frozen) {
        this.isFrozen = frozen;
    }
    public boolean isFrozen() {
    return this.isFrozen;
}

    public double getBalance() {
        return this.balance;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void transfer(BankAccount recipient, double amount) {
        if (isFrozen) {
            throw new IllegalStateException("Account is frozen");
        }
        if(amount > 0 && this.balance >= amount && recipient != null && recipient != this) {
            this.balance -= amount;
            recipient.deposit(amount);
        } else {
            throw new IllegalArgumentException();
        }
    }
    public List<String> transactionHistory() {
        return this.transactionHistory;
    }
    
    public void adminWithdraw(double amount) {
        if (!isOpen) {
            throw new IllegalStateException("Account is closed");
        }
        if (amount <= 0 || amount > balance) {
            throw new IllegalArgumentException();
        }
        balance -= amount;
        this.transactionHistory.add("Admin withdrew: " + amount);

    }
}

