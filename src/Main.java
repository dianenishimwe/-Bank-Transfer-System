
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {

    private static final int ACCOUNT_COUNT = 10;
    private static final int THREAD_COUNT = 50;
    private static final double INITIAL_BALANCE = 10_000.00;

    public static void main(String[] args)
            throws InterruptedException, ExecutionException {


        int transferCount = 50;

        List<BankAccount> accounts = new ArrayList<>();

        for (int i = 1; i <= ACCOUNT_COUNT; i++) {
            accounts.add(new BankAccount(i, INITIAL_BALANCE));
        }

        System.out.printf(
                "Starting bank balance: $%.2f%n",
                calculateTotal(accounts));

        ExecutorService executor =
                Executors.newFixedThreadPool(THREAD_COUNT);

        List<Future<String>> futures = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < transferCount; i++) {
            int senderIndex = random.nextInt(ACCOUNT_COUNT);
            int receiverIndex;

            do {
                receiverIndex = random.nextInt(ACCOUNT_COUNT);
            } while (senderIndex == receiverIndex);

            double amount = 1 + random.nextInt(500);

            BankAccount sender = accounts.get(senderIndex);
            BankAccount receiver = accounts.get(receiverIndex);

            Transfer task =
                    new Transfer(sender, receiver, amount);

            futures.add(executor.submit(task));
        }

        executor.shutdown();

        int successful = 0;
        int failed = 0;



    private static double calculateTotal(
            List<BankAccount> accounts) {
        double total = 0;

        for (BankAccount account : accounts) {
            total += account.getBalance();
        }

        return total;
    }
}
