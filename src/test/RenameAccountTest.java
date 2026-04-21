package test;
import main.BankAccount;
import main.Bank;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RenameAccountTest {
  
  private BankAccount testAccount;

  @BeforeEach
  public void setup() {
    this.testAccount = new BankAccount();
  }
  
  @Test
  public void renameAccountTest() {

    String newName = "new_name";
    testAccount.renameAccount(newName);
    assertEquals(testAccount.getName(), newName);

  }
}
