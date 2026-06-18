/**
 * Represents a single financial transaction in the tracker.
 * Stores the transaction's name, amount, date, category, and whether it is an expense.
 *
 * @author    Vishruth (daanimeguy104)
 * @since     June 10th, 2026
 */
public class Transaction {
    
    /**
     * The description or label for the transaction.
     */
    private String name;
    
    /**
     * The transaction amount. Positive values represent income and negative
     * values represent expenses.
     */
    private double amount;
    
    /**
     * The date the transaction was recorded.
     */
    private String date;
    
    /**
     * The category assigned to the transaction.
     */
    private String category;
    
    /**
     * Whether this transaction is an expense.
     */
    private boolean isExpense;
    
    /**
     * Constructs a new Transaction with the given values.
     *
     * A zero amount is not allowed because the app treats every transaction
     * as either income or expense.
     *
     * @param name          The description or label for the transaction
     * @param amount        The transaction amount; must be non-zero
     * @param date          The date string associated with the transaction
     * @param category      The category assigned to the transaction
     *
     * @throws IllegalArgumentException    If amount is zero
     */
    public Transaction(String name, double amount, String date, String category) {
        // prevent invalid transactions with no financial effect
        if(amount == 0) {
            throw new IllegalArgumentException("Amount cannot be zero");
        }
        
        // store the provided transaction values
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
        
        // mark the transaction as an expense if the amount is negative
        if(amount < 0) {
            isExpense = true;
        } else {
            isExpense = false;
        }
    }
    
    /**
     * Returns the transaction name.
     *
     * @return      The transaction description or label
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the transaction amount.
     *
     * @return The numeric amount for this transaction
     */
    public double getAmount() {
        return amount;
    }
    
    /**
     * Returns the transaction category.
     *
     * @return      The category name
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * Returns the transaction date.
     *
     * @return      The date string for this transaction
     */
    public String getDate() {
        return date;
    }
    
    /**
     * Indicates whether this transaction is an expense.
     *
     * @return      True if the transaction amount is negative, otherwise false
     */
    public boolean isExpense() {
        return isExpense;
    }
    
    /**
     * Returns a string representation of the transaction.
     *
     * @return      A readable string containing the transaction details
     */
    public String toString() {
        return name + " " + amount + " " + date + " " + category;
    }
}