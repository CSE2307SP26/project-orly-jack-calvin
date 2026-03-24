package main;

public class BankAccount {

    private double balance;

    public BankAccount() {
        this.balance = 0;
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

    public void transfer(BankAccount recipient, double amount) {
        if(amount > 0 && this.balance >= amount && recipient != null && recipient != this) {
            this.balance -= amount;
            recipient.deposit(amount);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
