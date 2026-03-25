package test;

import main.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdditionalAccountTest {

   private BankAccount testAccount;

   public AdditionalAccountTest() {
   }

   @BeforeEach
   public void setup() {
      this.testAccount = new BankAccount();
      testAccount.addAccount();
   }

   @Test
   public void testAdditionalAccount() {
      assertEquals(testAccount.getAdditionalAccounts().size(), 1);
   }

   @Test
   public void testAdditionalAccountDeposit() {
      BankAccount additionalAccount = testAccount.getAdditionalAccounts().get(0);
      additionalAccount.deposit(50);
      assertEquals(50, additionalAccount.getBalance(), 0.01);
   }
}