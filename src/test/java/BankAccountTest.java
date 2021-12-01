import Bank.BankAccount;
import Bank.Operation;
import Bank.Operation.operation;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountTest {

    private List<Operation> operations = new ArrayList<>();
    private BankAccount ba = new BankAccount(0, operations);

    @Test
    public void deposit_50() {
        ba.deposit(50, "25/11/21");
        assertEquals(50, ba.getBalance());
        assertEquals(operations.size(), 1);
        assertEquals(operations.get(0).toString(), new Operation(operation.DEPOSIT, "25/11/21", 50, 50).toString());
    }

    @Test
    public void deposit_0() {
        assertThrows(IllegalArgumentException.class, () -> ba.deposit(0, "25/11/21"));
    }

    @Test
    public void withdraw_first_50() {
        ba.deposit(100, "25/11/21");
        ba.withdraw(50, "25/11/21");
        assertEquals(50, ba.getBalance());
        assertEquals(operations.size(), 2);
        assertEquals(operations.get(0).toString(), new Operation(operation.DEPOSIT, "25/11/21", 100, 100).toString());
        assertEquals(operations.get(1).toString(), new Operation(operation.WITHDRAWAL, "25/11/21", 50, 50).toString());
    }

    @Test
    public void withdraw_second_50() {
        ba.deposit(50, "30/11/21");
        ba.withdraw(50, "30/11/21");
        assertEquals(0, ba.getBalance());
        assertEquals(operations.size(), 2);
        assertEquals(operations.get(0).toString(), new Operation(operation.DEPOSIT, "30/11/21", 50, 50).toString());
        assertEquals(operations.get(1).toString(), new Operation(operation.WITHDRAWAL, "30/11/21", 50, 0).toString());
    }

    @Test
    public void withdraw_0() {
        assertThrows(IllegalArgumentException.class, () -> ba.withdraw(0, "30/11/21"));
    }

    @Test
    public void withdraw_more_than_balance() {
        ba.deposit(50, "30/11/21");
        assertThrows(IllegalArgumentException.class, () -> ba.withdraw(60, "30/11/21"));
    }

    @Test
    public void withdrawAll() {
        ba.deposit(50, "02/12/21");
        ba.withdrawAll("02/12/21");
        assertEquals(0, ba.getBalance());
        assertEquals(operations.size(), 2);
        assertEquals(operations.get(0).toString(), new Operation(operation.DEPOSIT, "02/12/21", 50, 50).toString());
        assertEquals(operations.get(1).toString(), new Operation(operation.WITHDRAWAL, "02/12/21", operations.get(0).getBalance(), 0).toString());
    }

    @Test
    public void history() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String output = "operation,date,amount,balance\r\n";
        output += "DEPOSIT,today,10,10\r\n";
        output += "DEPOSIT,today,10,20\r\n";
        output += "DEPOSIT,today,10,30\r\n";
        output += "DEPOSIT,today,10,40\r\n";
        output += "WITHDRAWAL,today,10,30\r\n";
        output += "WITHDRAWAL,today,30,0\r\n";

        ba.deposit(10, "today");
        ba.deposit(10, "today");
        ba.deposit(10, "today");
        ba.deposit(10, "today");
        ba.withdraw(10, "today");
        ba.withdrawAll("today");
        ba.history();
        assertEquals(output, outContent.toString());
    }

}