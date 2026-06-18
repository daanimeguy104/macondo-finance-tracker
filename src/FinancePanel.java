import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Main container panel for the finance tracker interfaces. Combines the title bar,
 * transaction summary display, and input control into a single dashboard layout.
 *
 * @author      Vishruth (daanimeguy104)
 * @since       June 10th, 2026
 */
public class FinancePanel extends JPanel {
    
    /**
     * Constructs the main finance dashboard panel.
     *
     * This panel sets a two-row layout: a top area that contains the
     * TitlePanel (showing the app title and running balance) and a bottom
     * area split between the InputPanel (controls to add/search/remove
     * transactions) and the DataPanel (summary cards, transaction history,
     * charts and quick insights). The constructor also creates a shared
     * DecimalFormat used by child panels for consistent currency formatting.
     *
     * @param tl the TransactionList that provides the transactions
     *           displayed and edited by the child panels
     */
    public FinancePanel(TransactionList tl) {
        // shared DecimalFormat for currency display across child panels
        DecimalFormat df = new DecimalFormat("###,##0.00");
        setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        
        // overall layout and background for the dashboard
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(15, 23, 42));
        
        // bottom wrapper splits input controls (left) and data/visuals (right)
        JPanel bottomWrapper = new JPanel(new BorderLayout(10, 0));
        bottomWrapper.setOpaque(false);
        bottomWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // create child panels, passing shared TransactionList and DecimalFormat
        TitlePanel titlePanel = new TitlePanel(tl, df);
        DataPanel dataPanel = new DataPanel(tl, df);
        InputPanel inputPanel = new InputPanel(tl, df, dataPanel, titlePanel);
        
        // place input panel on the left and data panel on the right of the bottom wrapper
        bottomWrapper.add(inputPanel, BorderLayout.WEST);
        bottomWrapper.add(dataPanel, BorderLayout.EAST);
        
        // add wrappers to this panel to form the complete layout
        add(titlePanel, BorderLayout.NORTH);
        add(bottomWrapper, BorderLayout.CENTER);
    }
}