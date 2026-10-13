
import java.util.concurrent.Callable;

public class Transfer implements Callable<String> {
    private final BankAccount sender;
    private final BankAccount receiver;
    private final double amount;

    public Transfer(
            BankAccount sender,
            BankAccount receiver,
            double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    @Override
    public String call() {
        if (sender == receiver) {
            return "FAILED: Sender and receiver are the same account";
        }

        if (!Double.isFinite(amount) || amount <= 0) {
            return "FAILED: Invalid transfer amount";
        }


        BankAccount first = sender.getId() < receiver.getId()
                ? sender : receiver;
        BankAccount second = first == sender ? receiver : sender;

        synchronized (first) {
            synchronized (second) {
                if (!sender.withdrawWhileLocked(amount)) {
                    return "FAILED: Account " + sender.getId()
                            + " has insufficient funds";
                }

                receiver.depositWhileLocked(amount);

                return String.format(
                        "SUCCESS: Transferred $%.2f from Account %d to Account %d",
                        amount, sender.getId(), receiver.getId());
            }
        }
    }
}
