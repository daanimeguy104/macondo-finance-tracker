import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class FinancePanel extends JPanel {
    
    public FinancePanel() {
        DecimalFormat df = new DecimalFormat("###,###.###");
        TransactionList tl = new TransactionList();
        
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(15, 23, 42));
        
        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setOpaque(false);
        topWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        
        JPanel bottomWrapper = new JPanel(new BorderLayout(10, 0));
        bottomWrapper.setOpaque(false);
        bottomWrapper.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        TitlePanel titlePanel = new TitlePanel(tl, df);
        InputPanel inputPanel = new InputPanel(tl, df);
        DataPanel dataPanel = new DataPanel(tl, df);
        
        topWrapper.add(titlePanel, BorderLayout.NORTH);
        
        bottomWrapper.add(inputPanel, BorderLayout.WEST);
        bottomWrapper.add(dataPanel, BorderLayout.EAST);
        
        add(topWrapper, BorderLayout.NORTH);
        add(bottomWrapper, BorderLayout.CENTER);
    }
}