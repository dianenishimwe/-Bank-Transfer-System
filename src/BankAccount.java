
public class BankAccount {
    private final int id;
    private double balance;

    public BankAccount(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public synchronized double getBalance() {
        return balance;
    }


    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance < amount) {
            return false;
        }

        balance -= amount;
        return true;
    }


    void depositWhileLocked(double amount) {
        balance += amount;
    }

    boolean withdrawWhileLocked(double amount) {
        if (balance < amount) {
            return false;
        }

        balance -= amount;
        return true;
    }
}
