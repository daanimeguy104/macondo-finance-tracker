import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InputPanel extends RoundedPanel {
    
    private TransactionList tl;
    private DecimalFormat df;
    private DataPanel dp;
    private TitlePanel tp;
    
    private JTextField searchBar;
    private JTextField descriptionTF;
    private JTextField amountTF;
    private JComboBox<String> categoriesCB;
    private JCheckBox isExpenseCB;
    private JButton createTransaction;
    private JButton removeSelected;
    
    public InputPanel(TransactionList tl, DecimalFormat df, DataPanel dp, TitlePanel tp) {
        super(25, new Color(248, 250, 252));
        this.tl = tl;
        this.df = df;
        this.dp = dp;
        this.tp = tp;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
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
        searchBar.getDocument().addDocumentListener(new SearchBarListener());
        
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
        
        descriptionTF = new JTextField(12);
        descriptionTF.setFont(new Font("Sans Serif", Font.PLAIN, 11));
        descriptionTF.setMaximumSize(new Dimension(220, 30));
        descriptionTF.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(Box.createVerticalStrut(5));
        descriptionPanel.add(descriptionTF);
        
        JPanel amountPanel = new JPanel();
        amountPanel.setLayout(new BoxLayout(amountPanel, BoxLayout.Y_AXIS));
        amountPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        amountPanel.setOpaque(false);
        
        JLabel amountLabel = new JLabel("Amount ($):", JLabel.LEFT);
        amountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        amountLabel.setForeground(new Color(30, 41, 59));
        amountLabel.setBackground(getBackground());
        amountLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
        
        amountTF = new JTextField(12);
        amountTF.setFont(new Font("Sans Serif", Font.PLAIN, 11));
        amountTF.setMaximumSize(new Dimension(220, 30));
        amountTF.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        amountPanel.add(amountLabel);
        amountPanel.add(Box.createVerticalStrut(5));
        amountPanel.add(amountTF);
        
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryPanel.setOpaque(false);
        
        JLabel categoryLabel = new JLabel("Category:", JLabel.LEFT);
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryLabel.setForeground(new Color(30, 41, 59));
        categoryLabel.setBackground(getBackground());
        categoryLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
        
        String[] categories = {"Housing & Bills", "Food & Dining", "Transportation", "Entertainment", "Shopping", "Salary", "Other / Misc"};
        categoriesCB = new JComboBox<>(categories);
        categoriesCB.setBackground(getBackground());
        categoriesCB.setFont(new Font("Sans Serif", Font.PLAIN, 12));
        categoriesCB.setMaximumSize(new Dimension(220, 30));
        categoriesCB.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        categoryPanel.add(categoryLabel);
        categoryPanel.add(Box.createVerticalStrut(5));
        categoryPanel.add(categoriesCB);
        
        JPanel isExpensePanel = new JPanel(new  FlowLayout(FlowLayout.LEFT, 0, 0));
        isExpensePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        isExpensePanel.setOpaque(false);
        
        JLabel isExpenseLabel = new JLabel("Mark as Expense", JLabel.LEFT);
        isExpenseLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        isExpenseLabel.setForeground(new Color(30, 41, 59));
        isExpenseLabel.setBackground(getBackground());
        isExpenseLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
        
        isExpenseCB = new JCheckBox("");
        isExpenseCB.setFont(new Font("Sans Serif", Font.BOLD, 12));
        isExpenseCB.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        isExpensePanel.add(isExpenseCB);
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
        createTransaction.addActionListener(new AddTransaction());
        
        removeSelected = new JButton("Remove Selected Rows") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                
                super.paintComponent(g);
            }
        };
        
        removeSelected.setOpaque(false);
        removeSelected.setContentAreaFilled(false);
        removeSelected.setBorderPainted(false);
        removeSelected.setFocusPainted(false);
        removeSelected.setBackground(new Color(220, 53, 69));
        removeSelected.setForeground(Color.WHITE);
        removeSelected.setFont(new Font("Sans Serif", Font.BOLD, 12));
        removeSelected.addActionListener(new RemoveSelectedTransactions());
        
        add(title);
        add(Box.createVerticalStrut(16));
        add(searchBarHolder);
        add(Box.createVerticalStrut(32));
        add(createTransactionLabel);
        add(Box.createVerticalStrut(14));
        add(transactionInfo);
        add(Box.createVerticalStrut(24));
        add(createTransaction);
        add(Box.createVerticalStrut(32));
        add(removeSelected);
        add(Box.createVerticalGlue());
    }
    
    class AddTransaction implements ActionListener {
        
        public void actionPerformed(ActionEvent evt) {
            String description = descriptionTF.getText();
            if(description.trim().equals("")) {
                JOptionPane.showMessageDialog(InputPanel.this, "Please" +
                    " enter a description.", "Missing Input", JOptionPane.PLAIN_MESSAGE, null);
                return;
            }
            double amount = 0.0;
            
            try {
                amount = Double.parseDouble(amountTF.getText());
            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(InputPanel.this, "Please" +
                    " enter a valid non-zero number", "Incorrect Input",
                    JOptionPane.PLAIN_MESSAGE);
                return;
            }
            
            if(amount == 0) {
                JOptionPane.showMessageDialog(InputPanel.this, "Please" +
                    " enter a valid non-zero number", "Incorrect Input",
                    JOptionPane.PLAIN_MESSAGE);
                return;
            }
            
            if(isExpenseCB.isSelected()) {
                amount = amount * -1;
            }
            
            String category = categoriesCB.getSelectedItem().toString();
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM " +
                "dd, yyyy"));
            
            tl.addTransaction(description, amount, date, category);
            
            DefaultTableModel transactionsModel = dp.getTransactionsTableModel();
            String[] tableRow = new String[transactionsModel.getColumnCount()];
            
            if(amount > 0) {
                tableRow[0] = "▲";
            } else {
                tableRow[0] = "▼";
            }
            
            tableRow[1] = date;
            tableRow[2] = description;
            tableRow[3] = category;
            tableRow[4] = df.format(Math.abs(amount));
            
            transactionsModel.addRow(tableRow);
            
            tp.updateBalance();
            dp.updateDisplays();
        }
    }
    
    class SearchBarListener implements DocumentListener {
        public void insertUpdate(DocumentEvent e) {
            search();
        }
        
        public void removeUpdate(DocumentEvent e) {
            search();
        }
        
        public void changedUpdate(DocumentEvent e) {
            search();
        }
        
        public void search() {
            String query = searchBar.getText();
            dp.filterTable(query);
        }
    }
    
    class RemoveSelectedTransactions implements ActionListener {
        
        public void actionPerformed(ActionEvent evt) {
            JTable transactions = dp.getTable();
            DefaultTableModel transactionsModel = dp.getTransactionsTableModel();
            
            int[] selectedRows = transactions.getSelectedRows();
            
            if(selectedRows.length == 0) {
                JOptionPane.showMessageDialog(InputPanel.this, "Please select" +
                    "one or more rows to delete.", "Missing Input", JOptionPane.PLAIN_MESSAGE, null);
                return;
            }
            
            for(int i = selectedRows.length - 1; i >= 0; i--) {
                int modelRow = transactions.convertRowIndexToModel(selectedRows[i]);
                tl.removeTransaction(modelRow);
                transactionsModel.removeRow(modelRow);
            }
            
            dp.updateDisplays();
            tp.updateBalance();
        }
    }
}
