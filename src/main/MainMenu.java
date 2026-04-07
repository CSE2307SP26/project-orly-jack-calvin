package main;

import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 11;
    private static final int ADMIN_EXIT_SELECTION = 7;
    private static final int MAIN_MENU_EXIT_SELECTION = 4;
    private static final int MAX_SELECTION = 11;

    private Bank bank;
	private BankAccount userAccount;
    private Scanner keyboardInput;
    private BankAdministrator admin;

    private boolean mainMenu;
    private boolean adminDisplay;
    private boolean userDisplay;

    public MainMenu() {
        this.bank = new Bank();
        this.userAccount = bank.getAccountList().get(0);
        this.keyboardInput = new Scanner(System.in);
        this.admin = new BankAdministrator(bank);
    
        this.mainMenu = true;
        this.adminDisplay = false;
        this.userDisplay = false;

    }

    public void setUserAccount(BankAccount account) {
        this.userAccount = account;
    }

    public void mainMenuDisplayOptions() {
        this.mainMenu = false;
        System.out.println();
        System.out.println("Welcome to the 237 Bank App!");
        System.out.println("1. View Account");
        System.out.println("2. Create New Account");
        System.out.println("3. Administrator Login");
        System.out.println("4. Exit the app");

    }

    public void administratorDisplayOptions() {
        this.adminDisplay = true;
        this.userDisplay = false;
        System.out.println();
        System.out.println("Administrator Portal");
        System.out.println("1. View Bank Balance");
        System.out.println("2. View Accounts");
        System.out.println("3. View Transaction History");
        System.out.println("4. Collect Fees");
        System.out.println("5. Add Interest Payment");
        System.out.println("6. Return to Main Menu");
        System.out.println("7. Exit the app");

    }

    public void processAdministratorInput(int selection) {
        switch (selection) {
            case 1:
                // view bank balance
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
                mainMenu = true;
                adminDisplay = false;
                userDisplay = false;
                break;
            case 7:
                System.out.println("Goodbye!");
                System.exit(0);
                break; 
        }
    }

    public void processMenuInput(int selection) {
        switch (selection) {
            case 1: // View Account
                this.adminDisplay = false;
                this.userDisplay = true;
                System.out.println("Which account would you like to view (select the number): ");
                viewAccounts();
                selection = getUserSelection(bank.getNumberOfAccounts()); // selection of the account
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
                selection = getUserSelection(ADMIN_EXIT_SELECTION); // need to limit this selection to the number of options in the admin menu
                processAdministratorInput(selection);
                break;
            case 4:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
        }
    }

    public void displayOptions() {
        // this.adminDisplay = false;
        // this.userDisplay = true;
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
        System.out.println("10. Return to main menu");
        System.out.println("11. Exit the app");
        

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
                newAccount();
                break;
            case 8:
                viewAccounts();
                break;
            case 9:
                performSetMinimum();
                break;
            case 10: 
                this.mainMenu = true;
                this.adminDisplay = false;
                this.userDisplay = false;
                break;
            case 11:
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
        int noteSelection = getUserSelection(2); // 1 for yes note, 2 for no note

        if (noteSelection == 1) {
            System.out.print("Add your note: ");
            String note = keyboardInput.next();
            userAccount.depositWithNote(depositAmount, note);
        }

        else {
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
            int noteSelection = getUserSelection(2); // 1 for yes note, 2 for no note

            if (noteSelection == 1) {
                System.out.print("Add your note: ");
                String note = keyboardInput.next();
                userAccount.withdrawWithNote(withdrawAmount, note);
            }
            else {
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
        System.out.println(userAccount.transactionHistory());
        System.out.println();
    }

    public void newAccount() {
        bank.addAccount();
        System.out.println("Created a new account.\n");
    }

    public void viewAccounts() {
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
                if (recipient == userAccount) {
                    System.out.println("You cannot transfer to the same account. Please select a different account.");
                    recipient = null;
                }
            }
        }
        // prompt user for amount to transfer
        double transferAmount = -1;
        while(transferAmount < 0) {
            System.out.print("How much would you like to transfer: ");
            transferAmount = keyboardInput.nextDouble();
        }

        // transfer happens in Bank class - updates both account balances
        bank.transfer(userAccount, recipient, transferAmount);
        System.out.println();
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

    public void applyInterest() {
        double rate = -1;

        while (rate < 0 || rate > 100) {
            System.out.print("Enter interest rate (%): ");
            rate = keyboardInput.nextDouble();
        }

        admin.addInterestPayment(userAccount, rate);
        System.out.println("Interest applied.");
    }

    public void run() {
        int selection = -1;
        while(selection != EXIT_SELECTION) {
            if (mainMenu) {
                mainMenuDisplayOptions();
                selection = getUserSelection(MAIN_MENU_EXIT_SELECTION);
                // processInput(selection);
                processMenuInput(selection);
            }
            else if (userDisplay) {
                displayOptions();
                selection = getUserSelection(MAX_SELECTION);
                processInput(selection);
            }
            else if (adminDisplay) {
                administratorDisplayOptions();
                selection = getUserSelection(ADMIN_EXIT_SELECTION);
                processAdministratorInput(selection);
            }
            
        }
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }

}
