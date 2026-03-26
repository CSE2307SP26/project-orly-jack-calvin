package test;

import main.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        BankAccount testAccount = new BankAccount();
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }
    }

    @Test
    public void testInitialBalance() {
        BankAccount testAccount = new BankAccount();
        assertEquals(0, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testWithdraw() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        testAccount.withdraw(40);
        assertEquals(60, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidWithdrawTooMuch() {
        BankAccount testAccount = new BankAccount();
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
        BankAccount testAccount = new BankAccount();

        try {
            testAccount.withdraw(-10);
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }
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
}