package test;

import main.BankAccount;
import main.BankAdministrator;
import main.Bank;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class BankAdministratorTest {

    private Bank bank;
    private BankAdministrator admin;

    @BeforeEach
    public void setup() {
        this.bank = new Bank();
        this.admin = new BankAdministrator(bank);  // Fixed: Pass testBank as parameter
    }

    @Test
    void testCollectFees_validFee() {
        BankAccount account = bank.getAccountList().get(0);
        account.deposit(100.0); // starting balance
        admin.collectFees(account, 20.0);
        assertEquals(80.0, account.getBalance(), 0.001);
    }


    @Test
    void testCollectFees_negativeFee() {
        BankAccount account = bank.getAccountList().get(0);

        assertThrows(IllegalArgumentException.class, () -> {
            admin.collectFees(account, -10.0);
        });
    }

    @Test
    void testTransactionHistory() {
        bank.addAccount();
        BankAccount account1 = bank.getAccountList().get(0);
        BankAccount account2 = bank.getAccountList().get(1); 

        account1.deposit(100.0);
        bank.depositToBank(account1, 100.0); // Log deposit to bank
        account1.withdraw(30.0);
        bank.withdrawFromBank(account1, 30.0); // Log withdrawal from bank
        bank.transfer(account1, account2, 20.0);

        assertEquals(3, bank.transactionHistory().size());

        assertEquals("Deposit of $100.0 for " + account1.getName(), bank.transactionHistory().get(0));
        assertEquals("Withdrawal of $30.0 for " + account1.getName(), bank.transactionHistory().get(1));
        assertEquals("Transfer from " + account1.getName() + " to " + account2.getName() + ": $20.0", bank.transactionHistory().get(2));
        }

    @Test
    void testCheckBankBalance() {
        bank.addAccount();
        BankAccount account1 = bank.getAccountList().get(0);
        BankAccount account2 = bank.getAccountList().get(1); 

        account1.deposit(100.0);
        bank.depositToBank(account1, 100.0); // Log deposit to bank
        account2.deposit(200.0);
        bank.depositToBank(account2, 200.0); // Log deposit to bank

        assertEquals(300.0, bank.getBalance(), 0.001);
    }

    // private Bank testBank;
    // private BankAdministrator admin;

    // @BeforeEach
    // public void setup() {
    //     this.testBank = new Bank();
    //     this.admin = new BankAdministrator(testBank);  // Fixed: Pass testBank as parameter
    // }

    @Test
    public void testDeleteAccountWithZeroBalance() {
        BankAccount account = bank.getAccountList().get(0);
        bank.deleteAccount(account);
        assertFalse(bank.getAccountList().contains(account));
    }

    @Test
    public void testDeleteAccountWithNonZeroBalance() {
        BankAccount account = bank.getAccountList().get(0);
        account.deposit(100);
        assertThrows(IllegalArgumentException.class, () -> bank.deleteAccount(account));
    }

    @Test
    public void testDeleteNullAccount() {
        assertThrows(IllegalArgumentException.class, () -> bank.deleteAccount(null));
    }

    @Test
    public void testAdminDeleteAccountClosesIt() {
        BankAccount account = bank.getAccountList().get(0);
        admin.deleteAccount(account);
        // Verify the account is closed (removed from the bank's account list)
        assertFalse(bank.getAccountList().contains(account));
    }

    @Test
    public void testAccountRemovedFromList() {
        BankAccount account = bank.getAccountList().get(0);
        int initialSize = bank.getAccountList().size();
        bank.deleteAccount(account);
        assertEquals(initialSize - 1, bank.getAccountList().size());
    }

    @Test
    public void testFreezeAccount() {
        BankAccount account = bank.getAccountList().get(0);
        admin.freezeAccount(account);
        assertTrue(account.isFrozen());
    }

    @Test
    public void testUnfreezeAccount() {
        BankAccount account = bank.getAccountList().get(0);
        admin.freezeAccount(account);
        assertTrue(account.isFrozen());
        admin.unfreezeAccount(account);
        assertFalse(account.isFrozen());
    }

    @Test
    public void testFrozenAccountCannotDeposit() {
        BankAccount account = bank.getAccountList().get(0);
        admin.freezeAccount(account);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(100));
    }

    @Test
    public void testFrozenAccountCannotWithdraw() {
        BankAccount account = bank.getAccountList().get(0);
        admin.freezeAccount(account);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(100));
    }

    @Test
    public void testUnfrozenAccountCanDeposit() {
        BankAccount account = bank.getAccountList().get(0);
        admin.freezeAccount(account);
        admin.unfreezeAccount(account);
        double initialBalance = account.getBalance();
        account.deposit(50);
        assertEquals(initialBalance + 50, account.getBalance());
    }

    @Test
    public void testUnfrozenAccountCanWithdraw() {
        BankAccount account = bank.getAccountList().get(0);
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
        BankAccount account = bank.getAccountList().get(0);
        admin.freezeAccount(account);
        assertTrue(account.isFrozen());
        admin.unfreezeAccount(account);
        assertFalse(account.isFrozen());
        admin.freezeAccount(account);
        assertTrue(account.isFrozen());
    }

    // Password tests
    @Test
    public void testCorrectPassword() {
        String password = "1234";
        bank.checkPassword(password);
        assertEquals(password, bank.getPassword());

    }

    @Test
    public void testIncorrectPassword() {
        String password = "shook";
        assertFalse(bank.checkPassword(password));
        assertNotEquals(password, bank.getPassword());
    }

    // Interest tests
    @Test
    void testAddInterest_validRate() {
        BankAccount account = bank.getAccountList().get(0);
        BankAdministrator admin = new BankAdministrator(bank);
        account.deposit(100.0);
        admin.addInterestPayment(account, 10.0);
        assertEquals(110.0, account.getBalance(), 0.001);
    }

    @Test
    void testAddInterest_zeroRate() {
        BankAccount account = bank.getAccountList().get(0);
        account.deposit(100.0);
        admin.addInterestPayment(account, 0.0);
        assertEquals(100.0, account.getBalance(), 0.001);
    }

    @Test
    void testAddInterest_highRate() {
        BankAccount account = bank.getAccountList().get(0);
        account.deposit(200.0);
        admin.addInterestPayment(account, 50.0);
        assertEquals(300.0, account.getBalance(), 0.001);
    }

    @Test
    void testAddInterest_negativeRate() {
        BankAccount account = bank.getAccountList().get(0);
        assertThrows(IllegalArgumentException.class, () -> {
            admin.addInterestPayment(account, -5.0);
        });
    }

    @Test
    void testAddInterest_over100Rate() {
        BankAccount account = bank.getAccountList().get(0);
        assertThrows(IllegalArgumentException.class, () -> {
            admin.addInterestPayment(account, 150.0);
        });
    }


}