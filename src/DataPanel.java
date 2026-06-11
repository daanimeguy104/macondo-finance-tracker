import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class DataPanel extends RoundedPanel {
    
    private TransactionList tl;
    private DecimalFormat df;
    
    public DataPanel(TransactionList tl, DecimalFormat df) {
        super(12, new Color(248, 250, 252));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(520, 510));
    }
}
