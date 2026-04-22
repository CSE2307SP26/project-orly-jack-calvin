package test;
import main.BankAccount;
import main.Bank;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RenameAccountTest {
  
  private Bank testBank;
  private BankAccount testAccount;
  String newName;

  @BeforeEach
  public void setup() {
    this.testBank = new Bank();
    this.testAccount = this.testBank.getAccountList().get(0);
    this.newName = "new_name";
    testAccount.renameAccount(newName);
    testBank.changeAccountName(testAccount, newName);
  }
  
  @Test
  public void renameAccountTest() {
    assertEquals(testAccount.getName(), newName);
  }

  @Test
  public void bankRecordsChange() {
    String nameInBank = testBank.getAccountList().get(0).getName();
    assertEquals(nameInBank, newName);
  }
}
