import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class InputPanel extends RoundedPanel {
    
    private TransactionList tl;
    private DecimalFormat df;
    private JTextField description;
    private JTextField amount;
    private JTextField searchBar;
    private JComboBox<String> categories;
    private JCheckBox isExpense;
    private JButton createTransaction;
    
    public InputPanel(TransactionList tl, DecimalFormat df) {
        super(12, new Color(248, 250, 252));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(250, 510));
        
        JLabel title = new JLabel("Input Console", JLabel.LEFT);
        title.setForeground(new Color(30, 41, 59));
        title.setBackground(getBackground());
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setFont(new Font("Sans Serif", Font.BOLD, 16));
        
        JPanel searchBarHolder = new JPanel(new GridBagLayout());
        searchBarHolder.setMaximumSize(new Dimension(220, 34));
        searchBarHolder.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchBarHolder.setOpaque(false);
        
        JLabel search = new JLabel("Search:", JLabel.LEFT);
        search.setAlignmentX(Component.LEFT_ALIGNMENT);
        search.setForeground(new Color(30, 41, 59));
        search.setBackground(getBackground());
        search.setFont(new Font("Sans Serif", Font.BOLD, 15));
        
        searchBar = new JTextField(10);
        searchBar.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        searchBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        searchBarHolder.add(search);
        searchBarHolder.add(searchBar);
        
        JLabel createTransactionLabel = new JLabel("Create Transaction:", JLabel.LEFT);
        createTransactionLabel.setForeground(new Color(30, 41, 59));
        createTransactionLabel.setBackground(getBackground());
        createTransactionLabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
        
        JPanel transactionInfo = new JPanel();
        transactionInfo.setLayout(new BoxLayout(transactionInfo, BoxLayout.Y_AXIS));
        transactionInfo.add(Box.createVerticalStrut(10));
        transactionInfo.setOpaque(false);
        transactionInfo.setMaximumSize(new Dimension(220, 240));
        
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));
        descriptionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionPanel.setOpaque(false);
        
        JLabel descriptionLabel = new JLabel("Description:", JLabel.LEFT);
        descriptionLabel.setForeground(new Color(30, 41, 59));
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionLabel.setBackground(getBackground());
        descriptionLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
        
        description = new JTextField(12);
        description.setFont(new Font("Sans Serif", Font.BOLD, 11));
        description.setMaximumSize(new Dimension(220, 30));
        description.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(Box.createVerticalStrut(5));
        descriptionPanel.add(description);
        
        JPanel amountPanel = new JPanel();
        amountPanel.setLayout(new BoxLayout(amountPanel, BoxLayout.Y_AXIS));
        amountPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        amountPanel.setOpaque(false);
        
        JLabel amountLabel = new JLabel("Amount ($):", JLabel.LEFT);
        amountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        amountLabel.setForeground(new Color(30, 41, 59));
        amountLabel.setBackground(getBackground());
        amountLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
        
        amount = new JTextField(12);
        amount.setFont(new Font("Sans Serif", Font.BOLD, 11));
        amount.setMaximumSize(new Dimension(220, 30));
        amount.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        amountPanel.add(amountLabel);
        amountPanel.add(Box.createVerticalStrut(5));
        amountPanel.add(amount);
        
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryPanel.setOpaque(false);
        
        JLabel categoryLabel = new JLabel("Category:", JLabel.LEFT);
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryLabel.setForeground(new Color(30, 41, 59));
        categoryLabel.setBackground(getBackground());
        categoryLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
        
        String[] cleanCategories = {"Housing & Bills", "Food & Dining", "Transportation", "Entertainment", "Shopping", "Other / Misc"};
        categories = new  JComboBox<>(cleanCategories);
        categories.setBackground(getBackground());
        categories.setFont(new Font("Sans Serif", Font.BOLD, 12));
        categories.setMaximumSize(new Dimension(220, 30));
        categories.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        categoryPanel.add(categoryLabel);
        categoryPanel.add(Box.createVerticalStrut(5));
        categoryPanel.add(categories);
        
        JPanel isExpensePanel = new JPanel(new  FlowLayout(FlowLayout.LEFT, 0, 0));
        isExpensePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        isExpensePanel.setOpaque(false);
        
        JLabel isExpenseLabel = new JLabel("Mark as Expense", JLabel.LEFT);
        isExpenseLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        isExpenseLabel.setForeground(new Color(30, 41, 59));
        isExpenseLabel.setBackground(getBackground());
        isExpenseLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
        
        isExpense = new JCheckBox("");
        isExpense.setFont(new Font("Sans Serif", Font.BOLD, 12));
        isExpense.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        isExpensePanel.add(isExpense);
        isExpensePanel.add(isExpenseLabel);
        
        transactionInfo.add(descriptionPanel);
        transactionInfo.add(Box.createVerticalStrut(12));
        transactionInfo.add(amountPanel);
        transactionInfo.add(Box.createVerticalStrut(12));
        transactionInfo.add(categoryPanel);
        transactionInfo.add(Box.createVerticalStrut(12));
        transactionInfo.add(isExpensePanel);
        
        createTransaction = new JButton("Add Transaction") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                
                super.paintComponent(g);
            }
        };

        createTransaction.setOpaque(false);
        createTransaction.setContentAreaFilled(false);
        createTransaction.setBorderPainted(false);
        createTransaction.setFocusPainted(false);
        createTransaction.setBackground(new Color(37, 99, 235));
        createTransaction.setForeground(Color.WHITE);
        createTransaction.setFont(new Font("Sans Serif", Font.BOLD, 14));
        
        add(title);
        add(Box.createVerticalStrut(16));
        add(searchBarHolder);
        add(Box.createVerticalStrut(32));
        add(createTransactionLabel);
        add(Box.createVerticalStrut(14));
        add(transactionInfo);
        add(Box.createVerticalStrut(24));
        add(createTransaction);
        add(Box.createVerticalGlue());
    }
}
