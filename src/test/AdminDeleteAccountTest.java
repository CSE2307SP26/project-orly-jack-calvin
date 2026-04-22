package test;

import main.BankAccount;
import main.Bank;
import main.BankAdministrator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminDeleteAccountTest {

    private Bank testBank;
    private BankAdministrator admin;

    @BeforeEach
    public void setup() {
        this.testBank = new Bank();
        this.admin = new BankAdministrator();
    }

    @Test
    public void testDeleteAccountWithZeroBalance() {
        BankAccount account = testBank.getAccountList().get(0);
        testBank.deleteAccount(account);
        assertFalse(testBank.getAccountList().contains(account));
    }

    @Test
    public void testDeleteAccountWithNonZeroBalance() {
        BankAccount account = testBank.getAccountList().get(0);
        account.deposit(100);
        assertThrows(IllegalArgumentException.class, () -> testBank.deleteAccount(account));
    }

    @Test
    public void testDeleteNullAccount() {
        assertThrows(IllegalArgumentException.class, () -> testBank.deleteAccount(null));
    }

    @Test
    public void testAdminDeleteAccountClosesIt() {
        BankAccount account = testBank.getAccountList().get(0);
        admin.deleteAccount(account);
        assertEquals(false, account.getBalance() >= 0);
    }

    @Test
    public void testAccountRemovedFromList() {
        BankAccount account = testBank.getAccountList().get(0);
        int initialSize = testBank.getAccountList().size();
        testBank.deleteAccount(account);
        assertEquals(initialSize - 1, testBank.getAccountList().size());
    }
}