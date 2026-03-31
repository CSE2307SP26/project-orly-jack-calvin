package main;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private BankAccount defaultAccount;
    private int accountNumberCounter;
    private double bankBalance;
    private List<BankAccount> accountList;

    public Bank() {
        this.defaultAccount = new BankAccount();
        this.bankBalance = 0;
        this.accountList = new ArrayList<>();   
        this.accountList.add(defaultAccount);
        this.accountNumberCounter = 1;

    }
    public List<BankAccount> getAccountList() {
        return accountList;
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
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void transfer(BankAccount sender, BankAccount recipient, double amount) {
        if (amount > 0 && amount <= sender.getBalance()) {
            sender.withdraw(amount);
            recipient.deposit(amount);
        } else {
            throw new IllegalArgumentException();
   