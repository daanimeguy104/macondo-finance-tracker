import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.DecimalFormat;

public class DataPanel extends RoundedPanel {
    
    private TransactionList tl;
    private DecimalFormat df;
    private TableRowSorter<DefaultTableModel> sorter;
    
    private JLabel expenseTotal;
    private JLabel incomeTotal;
    private JLabel transactionAmt;
    private JTable transactionsTable;
    private DefaultTableModel transactionsTableModel;
    private JLabel savingsRate;
    private JLabel expenseToIncome;
    private JLabel netMargin;
    
    public DataPanel(TransactionList tl, DecimalFormat df) {
        super(25, new Color(248, 250, 252));
        this.tl = tl;
        this.df = df;
        
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setPreferredSize(new Dimension(520, 510));
        setLayout(new BorderLayout(0, 20));
        
        JPanel summaryRow = new JPanel(new GridLayout(1, 3));
        summaryRow.setLayout(new GridLayout(1, 0, 16, 0));
        summaryRow.setOpaque(false);
        summaryRow.setPreferredSize(new Dimension(520, 80));
        
        JPanel incomeCard = new RoundedPanel(15, new Color(40, 167, 69));
        incomeCard.setLayout(new BoxLayout(incomeCard, BoxLayout.Y_AXIS));
        
        JLabel totalIncomeLabel = new JLabel("Total Income");
        totalIncomeLabel.setForeground(Color.WHITE);
        totalIncomeLabel.setBackground(incomeCard.getBackground());
        totalIncomeLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        totalIncomeLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        totalIncomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        incomeTotal = new JLabel();
        incomeTotal.setForeground(Color.WHITE);
        incomeTotal.setBackground(incomeCard.getBackground());
        incomeTotal.setFont(new Font("Sans Serif", Font.BOLD, 18));
        incomeTotal.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        incomeCard.add(totalIncomeLabel);
        incomeCard.add(incomeTotal);
        
        JPanel expenseCard = new RoundedPanel(15, new Color(220, 53, 69));
        expenseCard.setLayout(new BoxLayout(expenseCard, BoxLayout.Y_AXIS));
        
        JLabel totalExpenseLabel = new JLabel("Total Expenses");
        totalExpenseLabel.setForeground(Color.WHITE);
        totalExpenseLabel.setBackground(expenseCard.getBackground());
        totalExpenseLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        totalExpenseLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        totalExpenseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        expenseTotal = new JLabel();
        expenseTotal.setForeground(Color.WHITE);
        expenseTotal.setBackground(expenseCard.getBackground());
        expenseTotal.setFont(new Font("Sans Serif", Font.BOLD, 18));
        expenseTotal.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        expenseCard.add(totalExpenseLabel);
        expenseCard.add(expenseTotal);
        
        JPanel transactionCard = new RoundedPanel(15, new Color(108, 117, 125));
        transactionCard.setLayout(new BoxLayout(transactionCard, BoxLayout.Y_AXIS));
        
        JLabel transactionLabel = new JLabel("Transactions");
        transactionLabel.setForeground(Color.WHITE);
        transactionLabel.setBackground(transactionCard.getBackground());
        transactionLabel.setBorder(BorderFactory.createEmptyBorder(8, 8 , 8, 8));
        transactionLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        transactionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        transactionAmt = new JLabel();
        transactionAmt.setForeground(Color.WHITE);
        transactionAmt.setBackground(transactionCard.getBackground());
        transactionAmt.setFont(new Font("Sans Serif", Font.BOLD, 18));
        transactionAmt.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        transactionCard.add(transactionLabel);
        transactionCard.add(transactionAmt);
        
        summaryRow.add(incomeCard);
        summaryRow.add(expenseCard);
        summaryRow.add(transactionCard);
        
        JPanel centerWorkspace = new JPanel();
        centerWorkspace.setLayout(new BoxLayout(centerWorkspace, BoxLayout.Y_AXIS));
        centerWorkspace.setOpaque(false);
        
        JPanel tableContainer = new JPanel(new BorderLayout(0, 10));
        tableContainer.setOpaque(false);
        tableContainer.setPreferredSize(new Dimension(490, 240));
        
        JLabel tableTitle = new JLabel("Transaction History", JLabel.LEFT);
        tableTitle.setForeground(new Color(30, 41, 59));
        tableTitle.setBackground(getBackground());
        tableTitle.setFont(new Font("Sans Serif", Font.BOLD, 18));
        
        String[] columnNames = {"Type", "Date", "Description", "Category", "Amount"};
        
        transactionsTableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        transactionsTable = new JTable(transactionsTableModel);
        transactionsTable.setRowHeight(34);
        transactionsTable.setFont(new Font("Sans Serif", Font.PLAIN, 12));
        transactionsTable.setShowVerticalLines(false);
        transactionsTable.setShowHorizontalLines(true);
        transactionsTable.setGridColor(new Color(241, 245, 249));
        transactionsTable.setBackground(getBackground());
        transactionsTable.getTableHeader().setFont(new Font("Sans Serif", Font.BOLD, 12));
        transactionsTable.getTableHeader().setBackground(getBackground());
        transactionsTable.getTableHeader().setForeground(new Color(30, 41, 59));
        transactionsTable.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        transactionsTable.getTableHeader().setReorderingAllowed(false);
        transactionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        transactionsTable.getColumnModel().getColumn(0).setPreferredWidth(45);
        transactionsTable.getColumnModel().getColumn(1).setPreferredWidth(110);
        transactionsTable.getColumnModel().getColumn(2).setPreferredWidth(220);
        transactionsTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        transactionsTable.getColumnModel().getColumn(4).setPreferredWidth(95);
        
        transactionsTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                
                if(value != null && !isSelected) {
                    if(value.toString().contains("▲")) {
                        c.setForeground(new Color(21, 128, 61));
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else if(value.toString().contains("▼")){
                        c.setForeground(new Color(185, 28, 28));
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else {
                        c.setForeground(new Color(30, 41, 59));
                        c.setFont(c.getFont().deriveFont(Font.PLAIN));
                    }
                } else if (isSelected) {
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                }
                return c;
            }
        });
        
        sorter = new TableRowSorter<DefaultTableModel>(transactionsTableModel);
        transactionsTable.setRowSorter(sorter);
        
        JScrollPane scroller = new JScrollPane(transactionsTable);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.getViewport().setBackground(getBackground());
        
        tableContainer.add(tableTitle, BorderLayout.NORTH);
        tableContainer.add(scroller, BorderLayout.CENTER);
        
        JPanel bottomSplitInsights = new JPanel(new GridLayout(1, 2));
        bottomSplitInsights.setOpaque(false);
        
        JPanel ratiosPanel = new JPanel();
        ratiosPanel.setLayout(new BoxLayout(ratiosPanel, BoxLayout.Y_AXIS));
        ratiosPanel.setOpaque(false);
        
        JLabel ratiosTitle = new JLabel("Quick Insights", JLabel.LEFT);
        ratiosTitle.setFont(new Font("Sans Serif", Font.BOLD, 18));
        ratiosTitle.setForeground(new Color(30, 41, 59));
        ratiosTitle.setBackground(getBackground());
        
        savingsRate = new JLabel();
        savingsRate.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        savingsRate.setForeground(new Color(30, 41, 59));
        savingsRate.setBackground(getBackground());
        
        expenseToIncome = new JLabel();
        expenseToIncome.setFont(new Font("Sans Serif",  Font.PLAIN, 14));
        expenseToIncome.setForeground(new Color(30, 41, 59));
        expenseToIncome.setBackground(getBackground());
        
        netMargin = new JLabel();
        netMargin.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        netMargin.setForeground(new Color(30, 41, 59));
        netMargin.setBackground(getBackground());
        
        ratiosPanel.add(ratiosTitle);
        ratiosPanel.add(Box.createVerticalStrut(15));
        ratiosPanel.add(savingsRate);
        ratiosPanel.add(Box.createVerticalStrut(5));
        ratiosPanel.add(expenseToIncome);
        ratiosPanel.add(Box.createVerticalStrut(5));
        ratiosPanel.add(netMargin);
        
        bottomSplitInsights.add(ratiosPanel);
        bottomSplitInsights.add(Box.createVerticalGlue());
        
        centerWorkspace.add(tableContainer, BorderLayout.NORTH);
        centerWorkspace.add(Box.createVerticalStrut(20));
        centerWorkspace.add(bottomSplitInsights, BorderLayout.SOUTH);
        
        add(summaryRow, BorderLayout.NORTH);
        add(centerWorkspace, BorderLayout.CENTER);
        
        updateRatios();
    }
    
    public DefaultTableModel getTransactionsTableModel() {
        return transactionsTableModel;
    }
    
    public void updateRatios() {
        incomeTotal.setText("$" + df.format(tl.getIncome()));
        expenseTotal.setText("$" + df.format(tl.getExpense()));
        transactionAmt.setText(tl.getTransactionCount() + "");
        
        double saveRate = 0;
        double expenseToIncomeRate = 0;
        double netMarginRate = 0;
        
        if(tl.getIncome() != 0) {
            saveRate = (tl.getIncome() - tl.getExpense()) / tl.getIncome();
            expenseToIncomeRate = tl.getExpense() / tl.getIncome();
            netMarginRate = tl.getRunningBalance() / tl.getIncome();
        }
        
        savingsRate.setText(String.format("Savings Rate: %.2f%%", saveRate * 100));
        expenseToIncome.setText(String.format("Expense to Income: %.2f%%",
            expenseToIncomeRate * 100));
        netMargin.setText(String.format("Net Margin: %.2f%%", netMarginRate * 100));
    }
    
    public void filterTable(String query) {
        if(query.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 2));
        }
    }
}
