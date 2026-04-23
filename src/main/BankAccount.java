package main;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter; 
import java.io.IOException; 
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BankAccount {

    private double balance;
    private String name;
    private boolean isOpen;
    private List<String> transactionHistory;
    private boolean minimum;
    private double minAmount;
    private boolean isFrozen;
    

    public BankAccount() {
        this.name = "Account1";
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
        this.isOpen = true; 
        this.minimum = false; // default for minimum is false, that there is no minimum
        this.minAmount = 0;
        this.isFrozen = false;
    }
    
    public void toCSV(String fileName) {
        String directoryPath = "src/csv";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
        String filePath = directoryPath + File.separator+ fileName;
        try (FileWriter writer = new FileWriter(filePath)) {
            
            writer.append("Transaction Details\n");
            
            
            for (String transaction : transactionHistory) {
                // wrap the string in quotes in case the user's note contains commas
                writer.append("\"").append(transaction).append("\"\n");
            }
            System.out.println("Success! Transactions exported to " + fileName);
            System.out.println("File path: " + System.getProperty("user.dir") + File.separator + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while writing the CSV: " + e.getMessage());
        }
    }

    private String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "[" + now.format(formatter) + "] ";
    }

    public void deposit(double amount) {
        if (!isOpen) {
            throw new IllegalStateException("Account is closed");
        }
        if (isFrozen) {
            throw new IllegalArgumentException("Account is frozen");
        }
        if(amount > 0) {
            this.balance += amount;
            this.transactionHistory.add(getCurrentTimestamp() + " Deposited: " + amount);
        } else {
             throw new IllegalArgumentException();
        }
    }

    public void depositWithNote(double amount, String note) {
        if (!isOpen) {
            throw new IllegalStateException("Account is closed");
        }
        if (isFrozen) {
            throw new IllegalArgumentException("Account is frozen");
        }
        if(amount > 0) {
            this.balance += amount;
            this.transactionHistory.add(getCurrentTimestamp()+ " Deposited: " + amount + " [" + note + "]");
        } else {
             throw new IllegalArgumentException();
        }
    }


    public void withdraw(double amount) {
        if (!isOpen) {
            throw new IllegalStateException("Account is closed");
        }
        if (isFrozen) {
            throw new IllegalArgumentException("Account is frozen");
        }
        
        if (amount <= 0 || amount > balance) { // balance would go into negatives if you withdraw requested amount
            throw new IllegalArgumentException();
        }
        else if (minimum && this.balance - amount < this.minAmount) { // balance would go below minimum if you withdraw requested amount
            System.out.println("The amount you requested goes below your account minimum. We cannot proceed with this transaction.");
        }
        else {
            balance -= amount;
            this.transactionHistory.add(getCurrentTimestamp() + " Withdrew: " + amount);
        }
    }

    public void withdrawWithNote(double amount, String note) {
        if (!isOpen) {
            throw new IllegalStateException("Account is closed");
        }
        if (isFrozen) {
            throw new IllegalArgumentException("Account is frozen");
        }
        if (amount <= 0 || amount > balance) {
            throw new IllegalArgumentException();
        }
        balance -= amount;
        this.transactionHistory.add(getCurrentTimestamp() + " Withdrew: " + amount + " [" + note + "]");
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
        this.transactionHistory.add(getCurrentTimestamp() + " Admin withdrew: " + amount);
    }

    public void setMinimum(double minimumAmount) {
        this.minimum = true;
        this.minAmount = minimumAmount;
    }

    public void renameAccount(String newName) {
        this.name = newName;
    }

}