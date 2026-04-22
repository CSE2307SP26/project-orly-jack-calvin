package test;
import main.BankAccount;
import main.Bank;
import main.MainMenu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminPasswordTest {

  private Bank testBank;

   @BeforeEach
   public void setup() {
      this.testBank = new Bank();
   }

   @Test
   public void correctPassword() {
    // when they put in correct password
    String password = "1234";
    testBank.checkPassword(password);
    assertEquals(password, testBank.getPassword());

   }

   public void incorrectPassword() {
    // when they put in incorrect password, should reprompt them
    String password = "shook";
    testBank.checkPassword(password);
    assertNotEquals(password, testBank.getPassword());
   }
  
}
