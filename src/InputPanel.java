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

/**
 * Handles user input for searching, adding, and deleting transactions.
 * Provides form controls for entering transaction details and keeps the
 * table and summary displays in sync with the transaction list.
 *
 * @author    Vishruth (daanimeguy104)
 * @since     June 10th, 2026
 */
public class InputPanel extends RoundedPanel {
    
    /**
     * Reference to the transaction list used to add and remove transactions.
     */
    private TransactionList tl;
    
    /**
     *  Shared DecimalFormat used for consistent money formatting.
     */
    private DecimalFormat df;
    
    /**
     * Reference to the DataPanel so the table and summary values can be updated.
     */
    private DataPanel dp;
    
    /**
     * Reference to the TitlePanel so the running balance label can be updated.
     */
    private TitlePanel tp;
    
    /**
     * Text field used to search transaction descriptions.
     */
    private JTextField searchBar;
    
    /**
     * Text field used to enter the transaction description.
     */
    private JTextField descriptionTF;
    
    /**
     *  Text field used to enter the transaction amount.
     */
    private JTextField amountTF;
    
    /**
     * Dropdown menu used to choose a transaction category.
     */
    private JComboBox<String> categoriesCB;
    
    /**
     *  Checkbox used to mark the transaction as an expense instead of income.
     */
    private JCheckBox isExpenseCB;
    
    /**
     *  Button used to add the transaction to the list.
     */
    private JButton createTransaction;
    
    /**
     * Button used to remove selected rows from the transaction table.
     */
    private JButton removeSelected;
    
    /**
     * Constructs the input panel and builds all transaction entry controls.
     * Initializes the search bar, transaction form, buttons, and listeners,
     * then lays everything out vertically.
     *
     * @param tl    The TransactionList to update
     * @param df    The DecimalFormat used for currency formatting
     * @param dp    The DataPanel used to refresh the table and summary display
     * @param tp    The TitlePanel used to refresh the running balance display
     */
    public InputPanel(TransactionList tl, DecimalFormat df, DataPanel dp, TitlePanel tp) {
        // call the parent rounded panel constructor and store shared references
        super(25, new Color(248, 250, 252));
        this.tl = tl;
        this.df = df;
        this.dp = dp;
        this.tp = tp;
        
        // set up the overall vertical layout and panel spacing
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setPreferredSize(new Dimension(250, 510));
        
        // title at the top of the panel
        JLabel title = new JLabel("Input Console", JLabel.LEFT);
        title.setForeground(new Color(30, 41, 59));
        title.setBackground(getBackground());
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setFont(new Font("Sans Serif", Font.BOLD, 16));
        
        // create the search bar area
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
        
        // create the "create transaction" section label
        JLabel createTransactionLabel = new JLabel("Create Transaction:", JLabel.LEFT);
        createTransactionLabel.setForeground(new Color(30, 41, 59));
        createTransactionLabel.setBackground(getBackground());
        createTransactionLabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
        
        // container that holds the transaction input fields
        JPanel transactionInfo = new JPanel();
        transactionInfo.setLayout(new BoxLayout(transactionInfo, BoxLayout.Y_AXIS));
        transactionInfo.add(Box.createVerticalStrut(10));
        transactionInfo.setOpaque(false);
        transactionInfo.setMaximumSize(new Dimension(220, 240));
        
        // description input section
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
        
        // amount input section
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
        
        // category dropdown section
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryPanel.setOpaque(false);
        
        JLabel categoryLabel = new JLabel("Category:", JLabel.LEFT);
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryLabel.setForeground(new Color(30, 41, 59));
        categoryLabel.setBackground(getBackground());
        categoryLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
        
        String[] categories = {"Entertainment", "Dining & Groceries", "Housing & Bills",
            "Shopping", "Transportation", "Other / Misc"};
        categoriesCB = new JComboBox<>(categories);
        categoriesCB.setBackground(getBackground());
        categoriesCB.setFont(new Font("Sans Serif", Font.PLAIN, 12));
        categoriesCB.setMaximumSize(new Dimension(220, 30));
        categoriesCB.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        categoryPanel.add(categoryLabel);
        categoryPanel.add(Box.createVerticalStrut(5));
        categoryPanel.add(categoriesCB);
        
        // expense/income toggle section
        JPanel isExpensePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
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
        isExpenseCB.setSelected(true);
        isExpenseCB.addActionListener(new IfExpense());
        
        isExpensePanel.add(isExpenseCB);
        isExpensePanel.add(isExpenseLabel);
        
        // add all form sections into the container
        transactionInfo.add(descriptionPanel);
        transactionInfo.add(Box.createVerticalStrut(12));
        transactionInfo.add(amountPanel);
        transactionInfo.add(Box.createVerticalStrut(12));
        transactionInfo.add(categoryPanel);
        transactionInfo.add(Box.createVerticalStrut(12));
        transactionInfo.add(isExpensePanel);
        
        // create the add transaction button with rounded custom painting
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
        
        // create the remove selected rows button with rounded custom painting
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
        
        // place all components on the panel
        add(title);
        add(Box.createVerticalStrut(16));
        add(searchBarHolder);
        add(Box.createVerticalStrut(32));
        add(createTransactionLabel);
        add(Box.createVerticalStrut(14));
        add(transactionInfo);
        add(Box.createVerticalStrut(24));
        add(createTransaction);
        add(Box.createVerticalStrut(25));
        add(removeSelected);
        add(Box.createVerticalGlue());
    }
    
    /**
     * Handles the add transaction button click.
     * Validates the input fields, creates a new transaction, adds it to the list,
     * updates the UI, and clears the form.
     */
    class AddTransaction implements ActionListener {
        
        /**
         * Processes the add transaction action.
         *
         * @param evt       The action event generated by the button
         */
        public void actionPerformed(ActionEvent evt) {
            // read the description from the text field
            String description = descriptionTF.getText();
            
            // reject empty descriptions
            if(description.trim().equals("")) {
                JOptionPane.showMessageDialog(InputPanel.this, "Please enter a description.",
                    "Missing Input", JOptionPane.PLAIN_MESSAGE, null);
                return;
            }
            
            // parse the entered amount
            double amount = 0.0;
            try {
                amount = Double.parseDouble(amountTF.getText());
            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(InputPanel.this,
                    "Please enter a valid non-zero number", "Incorrect Input",
                    JOptionPane.PLAIN_MESSAGE);
                return;
            }
            
            // reject zero amounts because Transaction does not allow them
            if(amount == 0) {
                JOptionPane.showMessageDialog(InputPanel.this,
                    "Please enter a valid non-zero number for the amount",
                    "Incorrect Input", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            
            // expenses are stored as negative values
            if(isExpenseCB.isSelected()) {
                amount = amount * -1;
            }
            
            // get the category and today's date
            String category = categoriesCB.getSelectedItem().toString();
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
            
            // add the transaction to the list
            tl.addTransaction(description, amount, date, category);
            
            // build a row for the transaction table
            DefaultTableModel transactionsModel = dp.getTransactionsTableModel();
            String[] tableRow = new String[transactionsModel.getColumnCount()];
            
            // first column shows whether this is income or expense
            if(amount > 0) {
                tableRow[0] = "▲";
            } else {
                tableRow[0] = "▼";
            }
            
            tableRow[1] = date;
            tableRow[2] = description;
            tableRow[3] = category;
            tableRow[4] = df.format(Math.abs(amount));
            
            // add the row to the table model
            transactionsModel.addRow(tableRow);
            
            // refresh the other panels so totals update immediately
            tp.updateBalance();
            dp.updateDisplays();
            
            // reset the form for the next transaction
            descriptionTF.setText("");
            amountTF.setText("");
            categoriesCB.setSelectedIndex(0);
            isExpenseCB.setSelected(true);
        }
    }
    
    /**
     * Listens for changes in the search box and filters the table.
     */
    class SearchBarListener implements DocumentListener {
        
        /**
         * Called when text is inserted into the document.
         *
         * @param e     The document event
         */
        public void insertUpdate(DocumentEvent e) {
            search();
        }
        
        /**
         * Called when text is removed from the document.
         *
         * @param e     The document event
         */
        public void removeUpdate(DocumentEvent e) {
            search();
        }
        
        /**
         * Called when the document style/content changes.
         *
         * @param e     The document event
         */
        public void changedUpdate(DocumentEvent e) {
            search();
        }
        
        /**
         * Filters the table by the current search text.
         */
        public void search() {
            String query = searchBar.getText();
            dp.filterTable(query);
        }
    }
    
    /**
     * Handles changes to the expense checkbox.
     * Enables or disables category selection depending on whether the item
     * is being treated as an expense.
     */
    class IfExpense implements ActionListener {
        
        /**
         * Updates the category dropdown based on checkbox state.
         *
         * @param evt       The action event generated by the checkbox
         */
        public void actionPerformed(ActionEvent evt) {
            // category selection only applies when the item is marked as an expense
            categoriesCB.setEnabled(isExpenseCB.isSelected());
            
            // repaint so the UI reflects the change immediately
            revalidate();
            repaint();
        }
    }
    
    /**
     * Handles the remove selected rows button.
     * Deletes selected transactions from the table and transaction list.
     */
    class RemoveSelectedTransactions implements ActionListener {
        
        /**
         * Removes the selected rows from the table and list.
         *
         * @param evt       The action event generated by the button
         */
        public void actionPerformed(ActionEvent evt) {
            JTable transactions = dp.getTable();
            DefaultTableModel transactionsModel = dp.getTransactionsTableModel();
            
            // get the selected row indices from the visible table
            int[] selectedRows = transactions.getSelectedRows();
            
            // prevent deletion when nothing is selected
            if(selectedRows.length == 0) {
                JOptionPane.showMessageDialog(InputPanel.this,
                    "Please select one or more rows to delete.", "Missing Input",
                    JOptionPane.PLAIN_MESSAGE, null);
                return;
            }
            
            // remove selected rows from bottom to top so row indices do not shift
            for(int i = selectedRows.length - 1; i >= 0; i--) {
                // convert the visible row index to the underlying model index
                int modelRow = transactions.convertRowIndexToModel(selectedRows[i]);
                
                // remove from the transaction list and table model
                tl.removeTransaction(modelRow);
                transactionsModel.removeRow(modelRow);
            }
            
            // update the other panels after deletion
            dp.updateDisplays();
            tp.updateBalance();
        }
    }
}