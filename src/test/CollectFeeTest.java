package test;

import main.Bank;
import main.BankAccount;
import main.BankAdministrator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CollectFeeTest {

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

}