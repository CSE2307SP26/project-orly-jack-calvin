package test;

import main.BankAccount;
import main.Bank;
import main.BankAdministrator;
import main.Bank;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankAdministratorInterestTest {

    @Test
    void testAddInterest_validRate() {
        Bank bank = new Bank();
<<<<<<< HEAD
        BankAccount account = new BankAccount();
=======
        BankAccount account = bank.getAccountList().get(0);
>>>>>>> 59c4c9e3bd18edb4e3c300747583273f7fd74cc7
        BankAdministrator admin = new BankAdministrator(bank);

        account.deposit(100.0);

        admin.addInterestPayment(account, 10.0); // 10% interest

        assertEquals(110.0, account.getBalance(), 0.001);
    }

    @Test
    void testAddInterest_zeroRate() {
        Bank bank = new Bank();
<<<<<<< HEAD
        BankAccount account = new BankAccount();
=======
        BankAccount account = bank.getAccountList().get(0);
>>>>>>> 59c4c9e3bd18edb4e3c300747583273f7fd74cc7
        BankAdministrator admin = new BankAdministrator(bank);

        account.deposit(100.0);

        admin.addInterestPayment(account, 0.0);

        assertEquals(100.0, account.getBalance(), 0.001);
    }

    @Test
    void testAddInterest_highRate() {
        Bank bank = new Bank();
<<<<<<< HEAD
        BankAccount account = new BankAccount();
=======
        BankAccount account = bank.getAccountList().get(0);
>>>>>>> 59c4c9e3bd18edb4e3c300747583273f7fd74cc7
        BankAdministrator admin = new BankAdministrator(bank);

        account.deposit(200.0);

        admin.addInterestPayment(account, 50.0); // 50% interest

        assertEquals(300.0, account.getBalance(), 0.001);
    }

    @Test
    void testAddInterest_negativeRate() {
        Bank bank = new Bank();
<<<<<<< HEAD
        BankAccount account = new BankAccount();
=======
        BankAccount account = bank.getAccountList().get(0);
>>>>>>> 59c4c9e3bd18edb4e3c300747583273f7fd74cc7
        BankAdministrator admin = new BankAdministrator(bank);

        assertThrows(IllegalArgumentException.class, () -> {
            admin.addInterestPayment(account, -5.0);
        });
    }

    @Test
    void testAddInterest_over100Rate() {
        Bank bank = new Bank();
<<<<<<< HEAD
        BankAccount account = new BankAccount();
=======
        BankAccount account = bank.getAccountList().get(0);
>>>>>>> 59c4c9e3bd18edb4e3c300747583273f7fd74cc7
        BankAdministrator admin = new BankAdministrator(bank);

        assertThrows(IllegalArgumentException.class, () -> {
            admin.addInterestPayment(account, 150.0);
        });
    }
}