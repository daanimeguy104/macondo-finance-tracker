public class Transaction {
    
    private String name;
    private double amount;
    private String date;
    private String category;
    private boolean isExpense;
    
    public Transaction(String name, double amount, String date, String category) {
        if(amount == 0) {
            throw new IllegalArgumentException("Amount cannot be zero");
        }
        
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
        
        if(amount < 0) {
            isExpense = true;
        } else {
            isExpense = false;
        }
    }
    
    public String getName() {
        return name;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getCategory() {
        return category;
    }
    
    public String getDate() {
        return date;
    }
    
    public boolean isExpense() {
        return isExpense;
    }
    
    public String toString() {
        return name + " " + amount + " " + date + " " + category;
    }
}
