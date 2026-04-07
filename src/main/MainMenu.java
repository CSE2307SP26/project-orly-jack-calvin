package main;

import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 9;
	private static final int MAX_SELECTION = 9;

    private Bank bank;
	private BankAccount userAccount;
    private Scanner keyboardInput;

    private boolean mainMenu;

    public MainMenu() {
        this.bank = new Bank();
        this.userAccount = bank.getAccountList().get(0);
        this.keyboardInput = new Scanner(System.in);
        this.mainMenu = true;
    }

    public void setUserAccount(BankAccount account) {
        this.userAccount = account;
    }

    public void mainMenuDisplayOptions() {
        this.mainMenu = false;
        System.out.println("Welcome to the 237 Bank App!");
        System.out.println("1. View Account");
        System.out.println("2. Create New Account");
        System.out.println("3. Administrator Login");

    }

    public void administratorDisplayOptions() {
        System.out.println("Administrator Portal");
        System.out.println("1. View Bank Balance");
        System.out.println("2. View Accounts");
        System.out.println("3. View Transaction History");
    }

    public void processAdministratorInput(int selection) {
        // calvin
        switch (selection) {
            case 1:
                // view bank balance
                break;
            case 2:
                viewAccounts();
                break;
            case 3:
                // view transaction history
                break;
        }
    }

    public void processMenuInput(int selection) {
        switch (selection) {
            case 1: // View Account
                System.out.println("Which account: ");
                viewAccounts();
                selection = getUserSelection(MAX_SELECTION); // selection of the account
                setUserAccount(bank.getAccountList().get(selection - 1));
                displayOptions();
                selection = getUserSelection(MAX_SELECTION);
                // processInput(selection);
                
                System.out.println();
                processInput(selection);
                break;
            case 2:
                this.mainMenu = true;
                performAdditionalAccount();
                break;
            case 3:
                administratorDisplayOptions();
                selection = getUserSelection(MAX_SELECTION);
                processAdministratorInput(selection);
                
        }
    }

    public void displayOptions() {
        System.out.println();
        System.out.println("User Portal");
        System.out.println("1. Make a deposit");
        System.out.println("2. Check Balance");
        System.out.println("3. Withdraw money");
        System.out.println("4. View transaction history");
        System.out.println("5. Close account");
        System.out.println("6. Transfer money");
        System.out.println("7. Add account");
        System.out.println("8. View accounts");
        System.out.println("9. Exit the app");

    }

    public int getUserSelection(int max) {
        int selection = -1;
        while(selection < 1 || selection > max) {
            System.out.print("Please make a selection: ");
            selection = keyboardInput.nextInt();
        }
        return selection;
    }

    public void processInput(int selection) {
        switch (selection) {
            case 1:
                performDeposit();
                break;
            case 2:
                checkBalance();
                break;
            case 3:
                performWithdraw();
                break;
            case 4:
                performTransactionHistory();
                break;
            case 5:
                closeAccount();
                break;
            case 6:
                performTransfer();
                break;
            case 7:
                performAdditionalAccount();
                break;
            case 8:
                viewAccounts();
                break;
            case 9:
                System.out.println("Goodbye!");
                break;
        }
    }

    public void performDeposit() {
        double depositAmount = -1;
        while(depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextDouble();
        }
        userAccount.deposit(depositAmount);
        bank.depositToBank(userAccount, depositAmount);
        System.out.println("Deposit successful.");
    }
    public void performWithdraw() {
        double withdrawAmount = -1;

        while (withdrawAmount <= 0) {
            System.out.print("How much would you like to withdraw: ");
            withdrawAmount = keyboardInput.nextDouble();
    }

        if (withdrawAmount > userAccount.getBalance()) {
            System.out.println("Insufficient funds.");
        } else {
            bank.withdrawFromBank(userAccount, withdrawAmount);
            userAccount.withdraw(withdrawAmount);
            System.out.println("Withdrawal successful.");
        }
    }

    public void checkBalance(){
        System.out.println("Current balance " + userAccount.getBalance());
    }

    public void performTransactionHistory() {
        System.out.println(userAccount.transactionHistory());
    }

    public void performAdditionalAccount() {
        // userAccount.addAccount();
        bank.addAccount();
        System.out.println("Added an additional account.\n");

    }

    public void viewAccounts() {
        for (BankAccount account : bank.getAccountList()) {
            System.out.println(account.getName() + " - Balance: " + account.getBalance());
        }
    }


    public void closeAccount() {
        if (userAccount.getBalance() != 0) {
            System.out.println("Cannot close account with a non-zero balance.");
        } else {
            bank.getAccountList().remove(userAccount);
            userAccount.close();
            System.out.println("You have closed your account. Goodbye!");
        }
    }

    public void performTransfer() {
        if (bank.getAccountList().size() < 2) {
            System.out.println("You need at least two accounts to perform a transfer.");
            return;
        }
        // prompt user for recipient account
        BankAccount recipient = null;
        while(recipient == null) {
            System.out.println("Enter the number of the account you would like to transfer to: ");
            for (int i = 0; i < bank.getAccountList().size(); i++) {
                System.out.println(i+1 + ". " + bank.getAccountList().get(i).getName());
            }
            int recipientIndex = keyboardInput.nextInt() - 1;
            if (recipientIndex >= 0 && recipientIndex < bank.getAccountList().size()) {
                recipient = bank.getAccountList().get(recipientIndex);
            }
        }
        // prompt user for amount to transfer
        double transferAmount = -1;
        while(transferAmount < 0) {
            System.out.print("How much would you like to transfer: ");
            transferAmount = keyboardInput.nextInt();
        }

        // transfer happens in Bank class - update both account balances
        bank.transfer(userAccount, recipient, transferAmount);
    }

    public void run() {
        int selection = -1;
        while(selection != EXIT_SELECTION) {
            // displayOptions();
            while (mainMenu) {
                mainMenuDisplayOptions();
                selection = getUserSelection(MAX_SELECTION);
                // processInput(selection);
                processMenuInput(selection);
            }
            // else {
                displayOptions();
                selection = getUserSelection(MAX_SELECTION);
                processInput(selection);
            // }
            
        }
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }

}
