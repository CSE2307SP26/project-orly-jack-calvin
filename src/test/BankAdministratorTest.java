package test;

import main.BankAccount;
import main.BankAdministrator;
import main.Bank;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankAdministratorTest {

    @Test
    void testCollectFees_validFee() {
        Bank bank = new Bank();
        BankAccount account = bank.getAccountList().get(0);
        BankAdministrator admin = new BankAdministrator(bank);

        account.deposit(100.0); // starting balance

        admin.collectFees(account, 20.0);

        assertEquals(80.0, account.getBalance(), 0.001);
    }


@Test
void testCollectFees_negativeFee() {
    Bank bank = new Bank();
    BankAccount account = bank.getAccountList().get(0);
    BankAdministrator admin = new BankAdministrator(bank);

    assertThrows(IllegalArgumentException.class, () -> {
        admin.collectFees(account, -10.0);
    });
}



@Test
void testTransactionHistory() {
    Bank bank = new Bank();
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
    Bank bank = new Bank();
    bank.addAccount();
    BankAccount account1 = bank.getAccountList().get(0);
    BankAccount account2 = bank.getAccountList().get(1); 

    account1.deposit(100.0);
    bank.depositToBank(account1, 100.0); // Log deposit to bank
    account2.deposit(200.0);
    bank.depositToBank(account2, 200.0); // Log deposit to bank

    assertEquals(300.0, bank.getBalance(), 0.001);
}

}