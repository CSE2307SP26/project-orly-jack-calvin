package main;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private double balance;
    private String name;
    private boolean isOpen;
    private List<String> transactionHistory;
    private boolean minimum;
    private double minAmount;

    public BankAccount() {
        this.name = "Account1";
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
        this.isOpen = true; 
        this.minimum = false; // default for minimum is false, that there is no minimum
        this.minAmount = 0;
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

    public void depositWithNote(double amount, String note) {
        if (!isOpen) {
            throw new IllegalStateException("Account is closed");
        }
        if(amount > 0) {
            this.balance += amount;
            this.transactionHistory.add("Deposited: " + amount + " [" + note + "]");
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

    public void withdrawWithNote(double amount, String note) {
        if (!isOpen) {
            throw new IllegalStateException("Account is closed");
        }
        if (amount <= 0 || amount > balance) {
            throw new IllegalArgumentException();
        }
        balance -= amount;
        this.transactionHistory.add("Withdrew: " + amount + " [" + note + "]");
    }

    public void close() {
        this.isOpen = false;
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

    public void setMinimum(double minimumAmount) {
        this.minimum = true;
        this.minAmount = minimumAmount;
    }

}

