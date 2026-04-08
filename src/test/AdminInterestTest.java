package test;

import main.BankAccount;
import main.BankAdministrator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankAdministratorInterestTest {

    @Test
    void testAddInterest_validRate() {
        BankAccount account = new BankAccount();
        BankAdministrator admin = new BankAdministrator();

        account.deposit(100.0);

        admin.addInterestPayment(account, 10.0); // 10% interest

        assertEquals(110.0, account.getBalance(), 0.001);
    }

    @Test
    void testAddInterest_zeroRate() {
        BankAccount account = new BankAccount();
        BankAdministrator admin = new BankAdministrator();

        account.deposit(100.0);

        admin.addInterestPayment(account, 0.0);

        assertEquals(100.0, account.getBalance(), 0.001);
    }

    @Test
    void testAddInterest_highRate() {
        BankAccount account = new BankAccount();
        BankAdministrator admin = new BankAdministrator();

        account.deposit(200.0);

        admin.addInterestPayment(account, 50.0); // 50% interest

        assertEquals(300.0, account.getBalance(), 0.001);
    }

    @Test
    void testAddInterest_negativeRate() {
        BankAccount account = new BankAccount();
        BankAdministrator admin = new BankAdministrator();

        assertThrows(IllegalArgumentException.class, () -> {
            admin.addInterestPayment(account, -5.0);
        });
    }

    @Test
    void testAddInterest_over100Rate() {
        BankAccount account = new BankAccount();
        BankAdministrator admin = new BankAdministrator();

        assertThrows(IllegalArgumentException.class, () -> {
            admin.addInterestPayment(account, 150.0);
        });
    }
}