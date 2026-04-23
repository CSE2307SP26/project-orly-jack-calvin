package test;

import main.Bank;
import main.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
        assertEquals("Deposited: 50.0 [groceries]", history.get(0));
    }

    @Test
    public void noteWithdrawlTest() {
        testAccount.deposit(75);
        String note = "note";
        testAccount.withdrawWithNote(50, note);
        List<String> history = testAccount.transactionHistory();
        assertEquals(2, history.size());
        assertEquals("Withdrew: 50.0 [note]", history.get(1));
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
        assertEquals("Deposited: 50.0", history.get(0));
    }

    @Test
    public void testTransactionHistoryMultipleTransactions() {
        testAccount.deposit(50);
        testAccount.deposit(100);
        List<String> history = testAccount.transactionHistory();
        assertEquals(2, history.size());
        assertEquals(Arrays.asList(
        "Deposited: 50.0",
            "Deposited: 100.0"
        ), testAccount.transactionHistory());
    }
    
    @Test
        public void testTransactionHistoryWithdrawal() {
        testAccount.deposit(50);
        testAccount.withdraw(50);
        List<String> history = testAccount.transactionHistory();
        assertEquals(2, history.size());
        assertEquals(Arrays.asList(
            "Deposited: 50.0",
            "Withdrew: 50.0"
        ), testAccount.transactionHistory());
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