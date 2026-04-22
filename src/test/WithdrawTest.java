package test;

import main.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class WithdrawTest {

  private BankAccount testAccount;

  @BeforeEach
  public void setup() {
    testAccount = new BankAccount();
  }

  @Test
    public void testWithdraw() {
        testAccount.deposit(100);
        testAccount.withdraw(40);
        assertEquals(60, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidWithdrawTooMuch() {
        testAccount.deposit(50);

        try {
            testAccount.withdraw(100);
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }
    }

    @Test
    public void testInvalidWithdrawNegative() {

        try {
            testAccount.withdraw(-10);
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }
    }

    @Test
    public void testWithdrawPastMinimum() {

      testAccount.deposit(30);
      testAccount.setMinimum(25);
      testAccount.withdraw(15);
      assertEquals(30, testAccount.getBalance(), 0.01);

    }
  
}
