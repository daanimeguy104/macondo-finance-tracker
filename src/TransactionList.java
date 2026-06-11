import java.util.ArrayList;

public class TransactionList {
    
    private ArrayList<Transaction> transactions;
    private double runningBalance;
    private int transactionCount;
    
    public TransactionList() {
        transactions = new ArrayList<Transaction>();
        runningBalance = 0.0;
        transactionCount = 0;
    }
    
    public void addTransaction(String name, double amount, String date, String category) {
        Transaction transaction = new Transaction(name, amount, date, category);
        transactions.add(transaction);
        runningBalance += amount;
        transactionCount++;
    }
    
    public double getRunningBalance() {
        return runningBalance;
    }
    
    public int getTransactionCount() {
        return transactionCount;
    }
    
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}
