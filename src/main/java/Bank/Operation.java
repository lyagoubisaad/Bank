package Bank;

public class Operation {

    private String date;
    private int amount;
    private int balance;
    private operation operation;

    public Operation(operation operation, String date, int amount, int balance) {
        this.operation = operation;
        this.date = date;
        this.amount = amount;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public String toString() {
        return operation + "," + date + "," + amount + "," + balance;
    }

    public enum operation {
        DEPOSIT,
        WITHDRAWAL
    }
}
