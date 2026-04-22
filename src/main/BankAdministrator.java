package main;
public class BankAdministrator {
    public void collectFees(BankAccount account, double fee){
        if (fee <= 0) {
            throw new IllegalArgumentException("Fee must be positive");
        } else{
            account.adminWithdraw(fee);
    
        }
    }
    public void addInterestPayment(BankAccount account, double interestRatePercentage) {
        if (interestRatePercentage < 0 || interestRatePercentage > 100) {
            throw new IllegalArgumentException();
        }
        double interestDecimalRate = interestRatePercentage / 100.0;
        double interest = account.getBalance() * interestDecimalRate;

        if (interest > 0) {
            account.deposit(interest);
        }
    }
    public void deleteAccount(BankAccount account) {
      account.close();
}

    public void toggleFreeze(BankAccount account) {
    account.setFrozen(!account.isFrozen());
}


}
