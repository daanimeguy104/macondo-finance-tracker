import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class TitlePanel extends RoundedPanel {
    
    private TransactionList tl;
    private DecimalFormat df;
    private JLabel title;
    private JLabel runningAmount;
    
    public TitlePanel(TransactionList tl, DecimalFormat df) {
        this.tl = tl;
        this.df = df;
        
        super(25, new Color(248, 250, 252));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(780, 50));
        
        title = new JLabel("Macondo Finance Tracker");
        title.setForeground(new Color(30, 41, 59));
        title.setBackground(getBackground());
        title.setFont(new Font("Sans Serif", Font.BOLD, 24));
        
        runningAmount = new JLabel();
        runningAmount.setForeground(new Color(30, 41, 59));
        title.setBackground(getBackground());
        runningAmount.setFont(new Font("Sans Serif", Font.BOLD, 20));
        
        add(title, BorderLayout.WEST);
        add(runningAmount, BorderLayout.EAST);
        
        updateBalance();
    }
    
    public void updateBalance() {
        if(tl.getRunningBalance() >= 0) {
            runningAmount.setText("Net Balance: $" + df.format(tl.getRunningBalance()));
        } else {
            runningAmount.setText("Net Balance: -$" + df.format(-1 * tl.getRunningBalance()));
        }
    }
}
