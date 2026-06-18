import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Displays the application title and the current net balance. Updates the balance
 * label whenever the transaction list changes.
 *
 * @author      Vishruth (daanimeguy104)
 * @since       June 9th, 2026
 */
public class TitlePanel extends RoundedPanel {
    
    /**
     * The current list of Transactions.
     */
    private TransactionList tl;
    
    /**
     * DecimalFormat used to format the running balance as currency.
     */
    private DecimalFormat df;
    
    /**
     * The label displaying the current net balance, updated whenever transactions
     * are added or removed.
     */
    private JLabel runningAmount;
    
    public TitlePanel(TransactionList tl, DecimalFormat df) {
        // initialize the TransactionList and DecimalFormat for use in updating the balance
        this.tl = tl;
        this.df = df;
        
        // call parent constructor and set up layout, border, and size
        super(25, new Color(248, 250, 252));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(780, 50));
        
        // create title of application
        JLabel title = new JLabel("Macondo Finance Tracker");
        title.setForeground(new Color(30, 41, 59));
        title.setBackground(getBackground());
        title.setFont(new Font("Sans Serif", Font.BOLD, 24));
        
        // instantiate label that displays the running balance and set its font and color
        runningAmount = new JLabel();
        runningAmount.setForeground(new Color(30, 41, 59));
        title.setBackground(getBackground());
        runningAmount.setFont(new Font("Sans Serif", Font.BOLD, 20));
        
        // add the title and running balance label to the panel
        add(title, BorderLayout.WEST);
        add(runningAmount, BorderLayout.EAST);
        
        updateBalance(); // update the balance of the runningAmount label to reflect the current state of the TransactionList
    }
    
    /**
     * Updates the running balance label to reflect the current net balance in the TransactionList.
     * If the balance is positive, it displays "Net Balance: $X.XX".
     * If the balance is negative, it displays "Net Balance: -$X.XX".
     */
    public void updateBalance() {
        if(tl.getRunningBalance() >= 0) {
            runningAmount.setText("Net Balance: $" + df.format(tl.getRunningBalance()));
        } else {
            runningAmount.setText("Net Balance: -$" + df.format(-1 * tl.getRunningBalance()));
        }
    }
}
