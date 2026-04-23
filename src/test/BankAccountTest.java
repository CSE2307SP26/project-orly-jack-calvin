package test;

import main.Bank;
import main.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankAccountTest {

    private Bank testBank;
    private BankAccount testAccount;

    @BeforeEach
    public void setup() {
        this.testAccount = new BankAccount();
        this.testBank = new Bank();
    }

    @Test
    public void testDeposit() {
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }
    }

    @Test
    public void testInitialBalance() {
        assertEquals(0, testAccount.getBalance(), 0.01);
    }

    @Test 
    public void testTransfer() {
        BankAccount sender = new BankAccount();
        BankAccount recipient = new BankAccount();
        sender.deposit(100);
        sender.transfer(recipient, 50);
        assertEquals(50, sender.getBalance(), 0.01);
        assertEquals(50, recipient.getBalance(), 0.01);
    }
  
    @Test
    public void noteDepositTest() {
        String note = "groceries";
        testAccount.depositWithNote(50, note);
        List<String> history = testAccount.transactionHistory();
        assertEquals(1, history.size());
        assertTrue(history.get(0).endsWith("Deposited: 50.0 [groceries]"));
    }

    @Test
    public void noteWithdrawlTest() {
        testAccount.deposit(75);
        String note = "note";
        testAccount.withdrawWithNote(50, note);
        List<String> history = testAccount.transactionHistory();
        assertEquals(2, history.size());
        assertTrue(history.get(1).endsWith("Withdrew: 50.0 [note]"));
    }

    // Rename tests
    @Test
    public void renameAccountTest() {
        String newName = "new_name";
        BankAccount firstAccount = this.testBank.getAccountList().get(0);
        firstAccount.renameAccount(newName);
        testBank.changeAccountName(testAccount, newName);
        assertEquals(testAccount.getName(), newName);
    }

      @Test
    public void testToCSVCreatesFileAndWritesData() throws IOException {

        BankAccount testAccount = new BankAccount();
        testAccount.depositWithNote(150.0, "Initial deposit");
        testAccount.withdrawWithNote(50.0, "Groceries");

        String testFileName = "test_export.csv";
        String testDirectory = "src/csv";
        File exportedFile = new File(testDirectory + File.separator + testFileName);

        try {
            testAccount.toCSV(testFileName);
            assertTrue(exportedFile.exists(), "The CSV file should exist in the src/csv folder.");

            Path filePath = exportedFile.toPath();
            List<String> fileLines = Files.readAllLines(filePath);

            // Check the header
            assertEquals("Transaction Details", fileLines.get(0));
            
            // Check the transactions
            assertEquals("\"Deposited: 150.0 [Initial deposit]\"", fileLines.get(1));
            assertEquals("\"Withdrew: 50.0 [Groceries]\"", fileLines.get(2));

        } finally {
            if (exportedFile.exists()) {
                exportedFile.delete();
            }
        }
    }


    @Test
    public void bankRecordsChange() {
        String newName = "new_name";
        BankAccount firstAccount = this.testBank.getAccountList().get(0);
        firstAccount.renameAccount(newName);
        testBank.changeAccountName(testAccount, newName);
        String nameInBank = testBank.getAccountList().get(0).getName();
        assertEquals(nameInBank, newName);
    }

    // Transaction history tests
    @Test 
    public void testTransactionHistoryNoTransactions() {
        assertEquals(0, testAccount.transactionHistory().size());
    }

    @Test
        public void testTransactionHistoryOneTransaction() {
        testAccount.deposit(50);
        List<String> history = testAccount.transactionHistory();
        assertEquals(1, history.size());
        assertTrue(history.get(0).endsWith("Deposited: 50.0"));
    }

    @Test
    public void testTransactionHistoryMultipleTransactions() {
        testAccount.deposit(50);
        testAccount.deposit(100);
        List<String> history = testAccount.transactionHistory();
        assertEquals(2, history.size());
        assertTrue(history.get(0).endsWith("Deposited: 50.0"));
        assertTrue(history.get(1).endsWith("Deposited: 100.0"));
    }
    
    @Test
        public void testTransactionHistoryWithdrawal() {
        testAccount.deposit(50);
        testAccount.withdraw(50);
        List<String> history = testAccount.transactionHistory();
        assertEquals(2, history.size());
        assertTrue(history.get(0).endsWith("Deposited: 50.0"));
        assertTrue(history.get(1).endsWith("Withdrew: 50.0"));
    }

    // Withdraw tests
    @Test
    public void testWithdraw() {
        testAccount.deposit(100);
        testAccount.withdraw(40);
        assertEquals(60, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidWithdrawTooMuch() {
        testAccount.deposit(50);

        try {
            testAccount.withdraw(100);
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }
    }

    @Test
    public void testInvalidWithdrawNegative() {

        try {
            testAccount.withdraw(-10);
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }
    }

    @Test
    public void testWithdrawPastMinimum() {

      testAccount.deposit(30);
      testAccount.setMinimum(25);
      testAccount.withdraw(15);
      assertEquals(30, testAccount.getBalance(), 0.01);

    }

}