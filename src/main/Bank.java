package main;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private BankAccount defaultAccount;
    private int accountNumberCounter;
    private double bankBalance;
    private List<BankAccount> accountList;
    private List<String> transactionHistory;
    private String password;

    public Bank() {
        this.defaultAccount = new BankAccount();
        this.bankBalance = 0;
        this.accountList = new ArrayList<>();   
        this.accountList.add(defaultAccount);
        this.accountNumberCounter = 1;
        this.transactionHistory = new ArrayList<>();
        this.password = "1234";

    }
    public List<BankAccount> getAccountList() {
        return accountList;
    }

    public void addTransaction(BankAccount sender, BankAccount recipient, double amount) {
        transactionHistory.add("Transfer from " + sender.getName() + " to " + recipient.getName() + ": $" + amount);
    }

    public void addTransaction(BankAccount account, double amount, String type) {
        transactionHistory.add(type + " of $" + amount + " for " + account.getName());
    }

    public void addAccount() {
        BankAccount newAccount = new BankAccount();
        accountNumberCounter++;
        newAccount.setName("Account" + accountNumberCounter);
        accountList.add(newAccount);
    }

    public void depositToBank(BankAccount account, double amount) {
        if (amount > 0) {
            this.bankBalance += amount;
            addTransaction(account, amount, "Deposit");
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void depositFees(BankAccount account, double fee) {
        if (fee > 0) {
            this.bankBalance += fee;
            addTransaction(account, fee, "Fee Collection");
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void payInterest(BankAccount account, double interest) {
        if (interest > 0) {
            this.bankBalance -= interest;
            addTransaction(account, interest, "Interest Payment");
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void transfer(BankAccount sender, BankAccount recipient, double amount) {
        if (amount > 0 && amount <= sender.getBalance()) {
            sender.withdraw(amount);
            recipient.deposit(amount);
            addTransaction(sender, recipient, amount);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getNumberOfAccounts() {
        return accountNumberCounter;
    }

    public void withdrawFromBank(BankAccount account, double amount) {
        if (amount > 0 && amount <= bankBalance) {
            this.bankBalance -= amount;
            addTransaction(account, amount, "Withdrawal");
        } else {
            throw new IllegalArgumentException();
        }
    }

     public List<String> transactionHistory() {
        return this.transactionHistory;
    }

    public double getBalance() {
        return this.bankBalance;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean checkPassword(String userPassword) {
        return userPassword.equals(this.password);
    }

    
}
