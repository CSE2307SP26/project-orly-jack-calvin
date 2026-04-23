package test;

import main.BankAccount;
import main.Bank;
import main.BankAdministrator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminFreezeAccountTest {

    private Bank testBank;
    private BankAdministrator admin;
    private BankAccount testAccount;

    @BeforeEach
    public void setup() {
        this.testBank = new Bank();
        this.admin = new BankAdministrator(testBank);
        this.testAccount = new BankAccount();
    }

    @Test
    public void testFreezeAccountPreventsDeposit() {
        testAccount.setFrozen(true);
        assertThrows(IllegalStateException.class, () -> testAccount.deposit(100));
    }

    @Test
    public void testFreezeAccountPreventsWithdraw() {
        testAccount.deposit(100);
        testAccount.setFrozen(true);
        assertThrows(IllegalStateException.class, () -> testAccount.withdraw(50));
    }

    @Test
    public void testFreezeAccountPreventsTransfer() {
        BankAccount recipient = new BankAccount();
        testAccount.deposit(100);
        testAccount.setFrozen(true);
        assertThrows(IllegalStateException.class, () -> testAccount.transfer(recipient, 50));
    }

    @Test
    public void testUnfreezeAccountAllowsDeposit() {
        testAccount.setFrozen(true);
        testAccount.setFrozen(false);
        testAccount.deposit(100);
        assertEquals(100, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testUnfreezeAccountAllowsWithdraw() {
        testAccount.deposit(100);
        testAccount.setFrozen(true);
        testAccount.setFrozen(false);
        testAccount.withdraw(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testAdminToggleFreezeFromUnfrozen() {
        assertFalse(testAccount.isFrozen());
        admin.toggleFreeze(testAccount);
        assertTrue(testAccount.isFrozen());
    }

    @Test
    public void testAdminToggleFreezeFromFrozen() {
        testAccount.setFrozen(true);
        admin.toggleFreeze(testAccount);
        assertFalse(testAccount.isFrozen());
    }

    @Test
    public void testIsFrozenInitiallyFalse() {
        assertFalse(testAccount.isFrozen());
    }
}