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
    
    public void removeTransaction(int index) {
        if(index >= 0 && index < transactions.size()) {
            Transaction transaction = transactions.remove(index);
            runningBalance -= transaction.getAmount();
            transactionCount--;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }
    
    public double getIncome() {
        double income = 0.0;
        
        for(int i = 0; i < transactions.size(); i++) {
            if(transactions.get(i).isExpense()) {
                continue;
            }
            income += transactions.get(i).getAmount();
        }
        return income;
    }
    
    public double getExpenses() {
        return getIncome() - runningBalance;
    }
    
    public Transaction getTransaction(int index) {
        return transactions.get(index);
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
    
    public String toString() {
        return transactions.toString();
    }
}
