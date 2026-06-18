import java.util.ArrayList;

/**
 * Stores and manages all transactions entered in the finance tracker.
 * Keeps the transaction list, running balance, and transaction count in sync
 * whenever transactions are added or removed.
 *
 * @author    Vishruth (daanimeguy104)
 * @since     June 10th, 2026
 */
public class TransactionList {
    
    /**
     * Dynamic list containing every transaction currently in the tracker.
     */
    private ArrayList<Transaction> transactions;
    
    /**
     *  Running total of all transaction amounts (income positive, expenses
     *  negative).
     */
    private double runningBalance;
    
    /**
     *  Number of transactions currently stored in the list
     */
    private int transactionCount;
    
    /**
     * Constructs an empty TransactionList. Initializes the internal list and
     * resets all totals to zero.
     */
    public TransactionList() {
        // create the backing list used to store Transaction objects
        transactions = new ArrayList<Transaction>();
        
        // start the balance at zero because there are no transactions yet
        runningBalance = 0.0;
        
        // start the transaction count at zero because the list is empty
        transactionCount = 0;
    }
    
    /**
     * Creates a new Transaction and adds it to the list.
     * Also updates the running balance and transaction count so they stay accurate.
     *
     * @param name      The description or label for the transaction
     * @param amount    The transaction amount; positive for income, negative for expense
     * @param date      The date string associated with the transaction
     * @param category  The category assigned to the transaction
     */
    public void addTransaction(String name, double amount, String date, String category) {
        // build a Transaction object from the provided input values
        Transaction transaction = new Transaction(name, amount, date, category);
        
        // store the new transaction at the end of the list
        transactions.add(transaction);
        
        // update the overall balance using the transaction amount
        runningBalance += amount;
        
        // increase the total number of transactions by one
        transactionCount++;
    }
    
    /**
     * Removes the transaction at the given index. Updates the running balance and
     * transaction count to reflect the removal.
     *
     * @param index     The position of the transaction to remove
     *
     * @throws IndexOutOfBoundsException    If the index is not within the valid range
     */
    public void removeTransaction(int index) {
        if(index >= 0 && index < transactions.size()) {
            // remove the selected transaction from the list and keep a reference to it
            Transaction transaction = transactions.remove(index);
            
            // subtract its amount from the running balance to undo its effect
            runningBalance -= transaction.getAmount();
            
            // reduce the transaction count because one item was removed
            transactionCount--;
        } else {
            // signal invalid access when the index does not exist in the list
            throw new IndexOutOfBoundsException();
        }
    }
    
    /**
     * Calculates the total income from all non-expense transactions.
     * Expense transactions are skipped because only positive amounts count as income.
     *
     * @return      The sum of all income transactions
     */
    public double getIncome() {
        double income = 0.0;
        
        // inspect every transaction in the list
        for(int i = 0; i < transactions.size(); i++) {
            // skip transactions that are marked as expenses
            if(transactions.get(i).isExpense()) {
                continue;
            }
            
            // add positive transaction amounts to the income total
            income += transactions.get(i).getAmount();
        }
        
        return income;
    }
    
    /**
     * Calculates total expenses using income and running balance.
     * Since expenses are stored as negative amounts, this formula derives the
     * total amount spent.
     *
     * @return      The total expenses
     */
    public double getExpenses() {
        // expenses are the difference between income and the current net balance
        return getIncome() - runningBalance;
    }
    
    /**
     * Returns the transaction stored at the specified index.
     *
     * @param index     The list position of the transaction to retrieve
     * @return          The Transaction at the given index
     */
    public Transaction getTransaction(int index) {
        return transactions.get(index);
    }
    
    /**
     * Returns the current running balance of all transactions.
     *
     * @return      The net balance across income and expenses
     */
    public double getRunningBalance() {
        return runningBalance;
    }
    
    /**
     * Returns the number of transactions currently stored.
     *
     * @return      The current transaction count
     */
    public int getTransactionCount() {
        return transactionCount;
    }
    
    /**
     * Returns a string representation of the transaction list.
     *
     * @return      The list of transactions as a string
     */
    public String toString() {
        return transactions.toString();
    }
}