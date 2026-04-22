package test;

import main.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AddNoteTest {

  private BankAccount testAccount;

  @BeforeEach
  public void setup() {
    this.testAccount = new BankAccount();
  }
  
  @Test
  public void noteDepositTest() {
    String note = "groceries";
    testAccount.depositWithNote(50, note);
    List<String> history = testAccount.transactionHistory();
    assertEquals(1, history.size());
    assertEquals("Deposited: 50.0 [groceries]", history.get(0));
  }

  @Test
  public void noteWithdrawlTest() {
    testAccount.deposit(75);
    String note = "note";
    testAccount.withdrawWithNote(50, note);
    List<String> history = testAccount.transactionHistory();
    assertEquals(2, history.size());
    assertEquals("Withdrew: 50.0 [note]", history.get(1));
  }
}
