import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    
    private int cornerRadius;
    
    public RoundedPanel() {
        cornerRadius = 10;
        setBackground(new Color(238, 238, 238));
    }
    
    public RoundedPanel(int cornerRadius, Color bgColor) {
        this.cornerRadius = cornerRadius;
        setBackground(bgColor);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
    }
}
