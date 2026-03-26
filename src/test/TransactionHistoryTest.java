package test;

import main.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TransactionHistoryTest {

  private BankAccount testAccount;

  @BeforeEach
  public void setup() {
    testAccount = new BankAccount();
  }

  @Test 
  public void testTransactionHistoryNoTransactions() {
    assertEquals(0, testAccount.transactionHistory().size());
  }

  @Test
    public void testTransactionHistoryOneTransaction() {
      testAccount.deposit(50);
      List<String> history = testAccount.transactionHistory();
      assertEquals(1, history.size());
      assertEquals("Deposited: 50.0", history.get(0));
    }

  @Test
  public void testTransactionHistoryMultipleTransactions() {
      testAccount.deposit(50);
      testAccount.deposit(100);
      List<String> history = testAccount.transactionHistory();
      assertEquals(2, history.size());
      assertEquals(Arrays.asList(
     "Deposited: 50.0",
          "Deposited: 100.0"
      ), testAccount.transactionHistory());
    }
  
  @Test
    public void testTransactionHistoryWithdrawal() {
      testAccount.deposit(50);
      testAccount.withdraw(50);
      List<String> history = testAccount.transactionHistory();
      assertEquals(2, history.size());
      assertEquals(Arrays.asList(
          "Deposited: 50.0",
          "Withdrew: 50.0"
      ), testAccount.transactionHistory());
    }
  
}
