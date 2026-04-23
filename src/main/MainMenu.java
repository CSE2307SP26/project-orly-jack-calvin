package main;

import java.util.Scanner;
import java.util.List;

public class MainMenu {

    private static final int EXIT_SELECTION = 12;
    private static final int ADMIN_EXIT_SELECTION = 9;
    private static final int MAIN_MENU_EXIT_SELECTION = 4;
    private static final int MAX_SELECTION = 12;

    private Bank bank;
    private BankAccount userAccount;
    private Scanner keyboardInput;
    private BankAdministrator admin;

    private boolean mainMenu;
    private boolean adminDisplay;
    private boolean userDisplay;
    private boolean adminPasswordEntered;

    public MainMenu() {
        this.bank = new Bank();
        this.userAccount = bank.getAccountList().get(0);
        this.keyboardInput = new Scanner(System.in);
        this.admin = new BankAdministrator(bank);
    
        this.mainMenu = true;
        this.adminDisplay = false;
        this.userDisplay = false;
        this.adminPasswordEntered = false;
    }

    public void setUserAccount(BankAccount account) {
        this.userAccount = account;
    }

    public void mainMenuDisplayOptions() {
        this.mainMenu = false;
        System.out.println();
        System.out.println("Welcome to the 237 Bank App!");
        System.out.println("1. View account");
        System.out.println("2. Create new account");
        System.out.println("3. Administrator login");
        System.out.println("-------------------");
        System.out.println("4. Exit the app");
    }

    public void administratorDisplayOptions() {
        this.adminDisplay = true;
        this.userDisplay = false;
        
        if (adminPasswordEntered || requestPassword()) {
            System.out.println();
            System.out.println("Administrator Portal");
            System.out.println("1. View bank balance");
            System.out.println("2. View accounts");
            System.out.println("3. View transaction history");
            System.out.println("4. Collect fees");
            System.out.println("5. Add interest payment");
            System.out.println("6. Delete account");
            System.out.println("7. Freeze/unfreeze account");
            System.out.println("-------------------");
            System.out.println("8. Return to main menu");
            System.out.println("9. Exit the app");
        } else {
            System.out.println("INCORRECT PASSWORD. Sending you back to main menu.");
            mainMenuDisplayOptions();
            int selection = getUserSelection(MAIN_MENU_EXIT_SELECTION);
            processMenuInput(selection);
        }
    }

    public void processAdministratorInput(int selection) {
        switch (selection) {
            case 1:
                viewBankBalance();
                break;
            case 2:
                viewAccounts();
                break;
            case 3:
                bankTransactionHistory();
                break;
            case 4:
                applyAdminFee();
                break;
            case 5:
                applyInterest();
                break;
            case 6:
                performDeleteAccount();
                break;
            case 7:
                performToggleFreezeAccount();
                break;
            case 8:
                this.mainMenu = true;
                this.adminDisplay = false;
                this.userDisplay = false;
                break;
            case 9:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
        }
    }

    public void processMenuInput(int selection) {
        switch (selection) {
            case 1:
                this.adminDisplay = false;
                this.userDisplay = true;
                System.out.println("Which account would you like to view (select the number): ");
                viewAccounts();
                selection = getUserSelection(bank.getNumberOfAccounts());
                setUserAccount(bank.getAccountList().get(selection - 1));
                displayOptions();
                selection = getUserSelection(MAX_SELECTION);
                processInput(selection);
                break;
            case 2:
                this.mainMenu = true;
                this.adminDisplay = false;
                this.userDisplay = false;
                newAccount();
                break;
            case 3:
                this.adminDisplay = true;
                this.userDisplay = false;
                administratorDisplayOptions();
                selection = getUserSelection(ADMIN_EXIT_SELECTION);
                processAdministratorInput(selection);
                break;
            case 4:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
        }
    }

    public void displayOptions() {
        System.out.println();
        System.out.println("Welcome, " + userAccount.getName() + ".");
        System.out.println("1. Make a deposit");
        System.out.println("2. Check balance");
        System.out.println("3. Withdraw money");
        System.out.println("4. View transaction history");
        System.out.println("5. Close account");
        System.out.println("6. Transfer money");
        System.out.println("7. Add account");
        System.out.println("8. View accounts");
        System.out.println("9. Set account minimum");
        System.out.println("10. Rename account");
        System.out.println("-------------------");
        System.out.println("11. Return to main menu");
        System.out.println("12. Exit the app");
    }

    public int getUserSelection(int max) {
        int selection = -1;
        while(selection < 1 || selection > max) {
            System.out.println("Please make a selection: ");
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
                newAccount();
                System.out.println("Created a new account.\n");
                this.mainMenu = true;
                this.adminDisplay = false;
                this.userDisplay = false;
                break;
            case 8:
                viewAccounts();
                break;
            case 9:
                performSetMinimum();
                break;
            case 10: 
                performRenameAccount();
                break;
            case 11:
                this.mainMenu = true;
                this.adminDisplay = false;
                this.userDisplay = false;
                break;
            case 12:
                System.out.println("Goodbye!"); 
                System.exit(0);
                break;
        }
    }

    public void displayNoteOptions() {
        System.out.println("Would you like to add a note to your deposit? (Select a number)");
        System.out.println("1. Yes");
        System.out.println("2. No");
    }

    public void performDeposit() {
        double depositAmount = -1;
        while(depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextDouble();
        }

        displayNoteOptions();
        int noteSelection = getUserSelection(2);

        if (noteSelection == 1) {
            System.out.print("Add your note: ");
            String note = keyboardInput.next();
            userAccount.depositWithNote(depositAmount, note);
        } else {
            userAccount.deposit(depositAmount);
        }
        
        bank.depositToBank(userAccount, depositAmount);
        System.out.println("Deposit successful.");
        System.out.println();
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
            displayNoteOptions();
            int noteSelection = getUserSelection(2);

            if (noteSelection == 1) {
                System.out.print("Add your note: ");
                String note = keyboardInput.next();
                userAccount.withdrawWithNote(withdrawAmount, note);
            } else {
                userAccount.withdraw(withdrawAmount);
            }
            bank.withdrawFromBank(userAccount, withdrawAmount);
            System.out.println("Withdrawal successful.");
        }
        System.out.println();
    }

    public void checkBalance(){
        System.out.println("Current balance " + userAccount.getBalance());
        System.out.println();
    }

    public void performTransactionHistory() {
        if (userAccount.transactionHistory().isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println(userAccount.transactionHistory());
            System.out.println();
        }
    }

    public void newAccount() {
        bank.addAccount();
        System.out.println("Created a new account.\n");
    }

    public void viewAccounts() {
        System.out.println("Accounts:");
        for (int i = 0; i < bank.getAccountList().size(); i++) {
            System.out.println(i+1 + ". " + bank.getAccountList().get(i).getName() + " - Balance: " + bank.getAccountList().get(i).getBalance());
        }
        System.out.println();
    }

    public void bankTransactionHistory() {
        admin.viewAllTransactions();
        System.out.println();
    }

    public void closeAccount() {
        if (userAccount.getBalance() != 0) {
            System.out.println("Cannot close account with a non-zero balance.");
        } else {
            bank.getAccountList().remove(userAccount);
            userAccount.close();
            System.out.println("You have closed your account. Goodbye!");
        }
        System.out.println();
    }

    public void performTransfer() {
        List<BankAccount> accounts = bank.getAccountList();

        if (accounts.size() < 2) {
            System.out.println("You need at least two accounts to perform a transfer.");
            return;
        }

        BankAccount recipient = selectRecipient(accounts);
        double amount = promptForAmount();

        bank.transfer(userAccount, recipient, amount);
        System.out.println("\nTransfer complete.");
    }

    private BankAccount selectRecipient(List<BankAccount> accounts) {
        while (true) {
            System.out.println("Select a recipient:");
            for (int i = 0; i < accounts.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, accounts.get(i).getName());
            }

            int choice = keyboardInput.nextInt() - 1;

            if (choice < 0 || choice >= accounts.size()) {
                System.out.println("Invalid selection.");
            } else if (accounts.get(choice).equals(userAccount)) {
                System.out.println("You cannot transfer to the same account.");
            } else {
                return accounts.get(choice);
            }
        }
    }

    private double promptForAmount() {
        double amount = -1;
        while (amount < 0) {
            System.out.print("Enter transfer amount: ");
            amount = keyboardInput.nextDouble();
        }
        return amount;
    }

    

    public void performDeleteAccount() {
        System.out.println("Select account to delete: ");
        viewAccounts();
        
        int accountIndex = getUserSelection(bank.getAccountList().size()) - 1;
        BankAccount targetAccount = bank.getAccountList().get(accountIndex);
        
        admin.deleteAccount(targetAccount);
        bank.getAccountList().remove(targetAccount);
    }

    public void performSetMinimum() {
        double minimumAmount = -1;
        while(minimumAmount < 0) {
            System.out.print("What would you like to set your account minimum to: ");
            minimumAmount = keyboardInput.nextDouble();
        }
        userAccount.setMinimum(minimumAmount);
        System.out.println("Account minimum set to " + minimumAmount + ".");
    }

    public void applyAdminFee() {
        double fee = -1;

        while (fee <= 0) {
            System.out.print("Enter fee amount: ");
            fee = keyboardInput.nextDouble();
        }

        if (fee > userAccount.getBalance()) {
            System.out.println("Insufficient funds.");
        } else {
            admin.collectFees(userAccount, fee);
            System.out.println("Fee applied.");
        }
    }

    public void performToggleFreezeAccount() {
        System.out.println("Select account to freeze/unfreeze: ");
        viewAccounts();
    
        int accountIndex = getUserSelection(bank.getAccountList().size()) - 1;
        BankAccount targetAccount = bank.getAccountList().get(accountIndex);
    
        admin.toggleFreeze(targetAccount);
        System.out.println("Account status changed.");
    }

    public void applyInterest() {
        double rate = -1;

        while (rate < 0 || rate > 100) {
            System.out.print("Enter interest rate (%): ");
            rate = keyboardInput.nextDouble();
        }

        admin.addInterestPayment(userAccount, rate);
        System.out.println("Interest applied.");
    }

    public void viewBankBalance() {
        double totalBalance = bank.getBalance();
        System.out.println("Total Bank Balance: " + totalBalance);
    }

    public boolean requestPassword() {
        System.out.print("Enter password for admin login (1234): ");
        String password = keyboardInput.next();
        boolean correctPassword = bank.checkPassword(password);
        if (correctPassword) {
            adminPasswordEntered = true;
        }
        return correctPassword;
    }

    public void performRenameAccount() {
