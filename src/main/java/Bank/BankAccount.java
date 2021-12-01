package Bank;

import Bank.Operation.operation;

import java.util.List;

public class BankAccount {

    private int balance;
    private List<Operation> operations;

    public BankAccount(int balance, List<Operation> operations) {
        this.balance = balance;
        this.operations = operations;
    }

    /**
     * @return the balance of my account
     */
    public int getBalance() {
        return balance;
    }

    /**
     * depose money in the account
     *
     * @param amount the amount of money that I will depose in my acount
     * @param date   the date in which I will depose money in my acount
     */
    public void deposit(int amount, String date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("the amount deposed should be superior to 0");
        }
        balance += amount;
        operations.add(new Operation(operation.DEPOSIT, date, amount, balance));
    }

    /**
     * withdraw some or all the money in the account
     *
     * @param amount the amount of money that I will withdraw from acount
     * @param date   the date in which I will withdraw money from acount
     */
    public void withdraw(int amount, String date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("the amount withdrawed should be superior to 0");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("the amount cannot exceed balance");
        }
        balance -= amount;
        operations.add(new Operation(operation.WITHDRAWAL, date, amount, balance));
    }

    /**
     * withdraw all the money in the account
     *
     * @param date in which I will withdraw all the money in my acount
     */
    public void withdrawAll(String date) {
        withdraw(balance, date);
    }

    /**
     * prints the history (operation, date, amount, balance) of my operations
     */
    public void history() {
        System.out.println("operation,date,amount,balance");
        operations.forEach(operation -> System.out.println(operation));
    }
}
