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

    @BeforeEach
    public void setup() {
        this.testBank = new Bank();
        this.admin = new BankAdministrator(testBank);  // Fixed: Pass testBank as parameter
    }

    @Test
    public void testFreezeAccount() {
        BankAccount account = testBank.getAccountList().get(0);
        admin.freezeAccount(account);
        assertTrue(account.isFrozen());
    }

    @Test
    public void testUnfreezeAccount() {
        BankAccount account = testBank.getAccountList().get(0);
        admin.freezeAccount(account);
        assertTrue(account.isFrozen());
        admin.unfreezeAccount(account);
        assertFalse(account.isFrozen());
    }

    @Test
    public void testFrozenAccountCannotDeposit() {
        BankAccount account = testBank.getAccountList().get(0);
        admin.freezeAccount(account);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(100));
    }

    @Test
    public void testFrozenAccountCannotWithdraw() {
        BankAccount account = testBank.getAccountList().get(0);
        admin.freezeAccount(account);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(100));
    }

    @Test
    public void testUnfrozenAccountCanDeposit() {
        BankAccount account = testBank.getAccountList().get(0);
        admin.freezeAccount(account);
        admin.unfreezeAccount(account);
        double initialBalance = account.getBalance();
        account.deposit(50);
        assertEquals(initialBalance + 50, account.getBalance());
    }

    @Test
    public void testUnfrozenAccountCanWithdraw() {
        BankAccount account = testBank.getAccountList().get(0);
        account.deposit(100);  // Ensure sufficient balance
        admin.freezeAccount(account);
        admin.unfreezeAccount(account);
        double initialBalance = account.getBalance();
        account.withdraw(50);
        assertEquals(initialBalance - 50, account.getBalance());
    }

    @Test
    public void testFreezeNullAccount() {
        assertThrows(IllegalArgumentException.class, () -> admin.freezeAccount(null));
    }

    @Test
    public void testUnfreezeNullAccount() {
        assertThrows(IllegalArgumentException.class, () -> admin.unfreezeAccount(null));
    }

    @Test
    public void testMultipleFreezeUnfreezeToggle() {
        BankAccount account = testBank.getAccountList().get(0);
        admin.freezeAccount(account);
        assertTrue(account.isFrozen());
        admin.unfreezeAccount(account);
        assertFalse(account.isFrozen());
        admin.freezeAccount(account);
        assertTrue(account.isFrozen());
    }
}