package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Bank;
import main.BankAccount;

public class BankTest {
  
  private Bank testBank;

   @BeforeEach
   public void setup() {
      this.testBank = new Bank();
   }

   @Test
   public void testAdditionalAccountOneAccount() {
      assertEquals(testBank.getAccountList().size(), 1); // default account
   }

   @Test
   public void testAdditionalAccountTwoAccounts() {
      testBank.addAccount();
      assertEquals(testBank.getAccountList().size(), 2);
   }

   @Test
   public void testAdditionalAccountDeposit() {
      BankAccount additionalAccount = testBank.getAccountList().get(0);
      additionalAccount.deposit(50);
      assertEquals(50, additionalAccount.getBalance(), 0.01);
   }
   
}


