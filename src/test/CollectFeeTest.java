package test;

import main.BankAccount;
import main.BankAdministrator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankAdministratorTest {

    @Test
    void testCollectFees_validFee() {
        BankAccount account = new BankAccount();
        BankAdministrator admin = new BankAdministrator();

        account.deposit(100.0); // starting balance

        admin.collectFees(account, 20.0);

        assertEquals(80.0, account.getBalance(), 0.001);
    }


@Test
void testCollectFees_negativeFee() {
    BankAccount account = new BankAccount();
    BankAdministrator admin = new BankAdministrator();

    assertThrows(IllegalArgumentException.class, () -> {
        admin.collectFees(account, -10.0);
    });
}




}
