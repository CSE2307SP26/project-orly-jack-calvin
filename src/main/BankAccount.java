package main;

public class BankAccount {

    private double balance;
    private boolean isOpen;

    public BankAccount() {
        this.balance = 0;
        this.isOpen = true; 
    }

    public void deposit(double amount) {
        if (!isOpen) {
            throw new IllegalStateException("Account is closed");
        } else {
            if(amount > 0) {
                this.balance += amount;
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    public void close() {
        this.isOpen = false;
    }

    public double getBalance() {
        return this.balance;
    }
}
