package main;

public class BankAdministrator {
    private Bank bank;

    public BankAdministrator(Bank bank) {
        this.bank = bank;
    }

    public void collectFees(BankAccount account, double fee) {
        if (fee <= 0) {
            throw new IllegalArgumentException("Fee must be positive");
        } else {
            account.adminWithdraw(fee);
            bank.depositFees(account, fee);
        }
    }

    public void addInterestPayment(BankAccount account, double interestRatePercentage) {
        if (interestRatePercentage < 0 || interestRatePercentage > 100) {
            throw new IllegalArgumentException();
        }
        double interestDecimalRate = interestRatePercentage / 100.0;
        double interest = account.getBalance() * interestDecimalRate;
        if (interest > 0) {
            if (account.getBalance() + interest < 0) {
                throw new IllegalArgumentException("Bank cannot pay interest at this time");
            }
            account.deposit(interest);
            bank.payInterest(account, interest);
        }
    }

    public void deleteAccount(BankAccount account) {
        account.close();
    }

    public void toggleFreeze(BankAccount account) {
        account.setFrozen(!account.isFrozen());
    }

    public void viewAllTransactions() {
        if (bank.transactionHistory().isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
