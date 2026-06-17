import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

public class DataPanel extends RoundedPanel {
    
    private final String[] expenseCategories = {
        "Entertainment",
        "Dining & Groceries",
        "Housing & Bills",
        "Shopping",
        "Transportation",
        "Other / Misc"
    };
    
    private final Color[] sliceColors = {
        new Color(79, 70, 229),
        new Color(13, 148, 136),
        new Color(217, 119, 6),
        new Color(219, 39, 119),
        new Color(124, 58, 237),
        new Color(75, 85, 99)
    };
    
    private TransactionList tl;
    private DecimalFormat df;
    private TableRowSorter<DefaultTableModel> sorter;
    private double[] expenseAmounts;
    
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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JPanel summaryRow = new JPanel(new GridLayout(1, 3));
        summaryRow.setLayout(new GridLayout(1, 0, 15, 0));
        summaryRow.setOpaque(false);
        summaryRow.setPreferredSize(new Dimension(520, 80));
        summaryRow.setMaximumSize(new Dimension(520, 80));
        summaryRow.setMinimumSize(new Dimension(520, 80));
        
        JPanel incomeCard = new RoundedPanel(15, new Color(40, 167, 69));
        incomeCard.setLayout(new BoxLayout(incomeCard, BoxLayout.Y_AXIS));
        incomeCard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel totalIncomeLabel = new JLabel("Total Income");
        totalIncomeLabel.setForeground(Color.WHITE);
        totalIncomeLabel.setBackground(incomeCard.getBackground());
        totalIncomeLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        totalIncomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        incomeTotal = new JLabel();
        incomeTotal.setForeground(Color.WHITE);
        incomeTotal.setBackground(incomeCard.getBackground());
        incomeTotal.setFont(new Font("Sans Serif", Font.BOLD, 20));
        incomeTotal.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        incomeCard.add(totalIncomeLabel);
        incomeCard.add(Box.createVerticalStrut(10));
        incomeCard.add(incomeTotal);
        
        JPanel expenseCard = new RoundedPanel(15, new Color(220, 53, 69));
        expenseCard.setLayout(new BoxLayout(expenseCard, BoxLayout.Y_AXIS));
        expenseCard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel totalExpenseLabel = new JLabel("Total Expenses");
        totalExpenseLabel.setForeground(Color.WHITE);
        totalExpenseLabel.setBackground(expenseCard.getBackground());
        totalExpenseLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        totalExpenseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        expenseTotal = new JLabel();
        expenseTotal.setForeground(Color.WHITE);
        expenseTotal.setBackground(expenseCard.getBackground());
        expenseTotal.setFont(new Font("Sans Serif", Font.BOLD, 20));
        expenseTotal.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        expenseCard.add(totalExpenseLabel);
        expenseCard.add(Box.createVerticalStrut(10));
        expenseCard.add(expenseTotal);
        
        JPanel transactionCard = new RoundedPanel(15, new Color(108, 117, 125));
        transactionCard.setLayout(new BoxLayout(transactionCard, BoxLayout.Y_AXIS));
        transactionCard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel transactionLabel = new JLabel("Transactions");
        transactionLabel.setForeground(Color.WHITE);
        transactionLabel.setBackground(transactionCard.getBackground());
        transactionLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        transactionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        transactionAmt = new JLabel();
        transactionAmt.setForeground(Color.WHITE);
        transactionAmt.setBackground(transactionCard.getBackground());
        transactionAmt.setFont(new Font("Sans Serif", Font.BOLD, 20));
        transactionAmt.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        transactionCard.add(transactionLabel);
        transactionCard.add(Box.createVerticalStrut(10));
        transactionCard.add(transactionAmt);
        
        summaryRow.add(incomeCard);
        summaryRow.add(expenseCard);
        summaryRow.add(transactionCard);
        
        JPanel tableContainer = new JPanel(new BorderLayout(0, 10));
        tableContainer.setOpaque(false);
        tableContainer.setPreferredSize(new Dimension(490, 200));
        tableContainer.setMaximumSize(new Dimension(490, 200));
        tableContainer.setMinimumSize(new Dimension(490, 200));
        
        JLabel tableTitle = new JLabel("Transaction History", JLabel.LEFT);
        tableTitle.setForeground(new Color(30, 41, 59));
        tableTitle.setBackground(getBackground());
        tableTitle.setFont(new Font("Sans Serif", Font.BOLD, 18));
        
        String[] columnNames = {"Type", "Date", "Description", "Category", "Amount"};
        
        transactionsTableModel = new DefaultTableModel(columnNames, 0) {
            public Class<?> getColumnClass(int columnIndex) {
                if(columnIndex == 1) {
                    return LocalDate.class;
                }
                return String.class;
            }
            
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for(int i = 0; i < tl.getTransactionCount(); i++) {
            String[] row = new String[transactionsTableModel.getColumnCount()];
            Transaction currTrans = tl.getTransaction(i);
            
            if(currTrans.getAmount() > 0) {
                row[0] = "▲";
            } else {
                row[0] = "▼";
            }
            
            row[1] = currTrans.getDate();
            row[2] = currTrans.getName();
            row[3] = currTrans.getCategory();
            row[4] = "$" + df.format(Math.abs(currTrans.getAmount()));
            
            transactionsTableModel.addRow(row);
        }
        
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
        transactionsTable.getTableHeader().setResizingAllowed(false);
        transactionsTable.setAutoCreateRowSorter(true);
        transactionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        transactionsTable.getColumnModel().getColumn(0).setPreferredWidth(45);
        transactionsTable.getColumnModel().getColumn(1).setPreferredWidth(110);
        transactionsTable.getColumnModel().getColumn(2).setPreferredWidth(220);
        transactionsTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        transactionsTable.getColumnModel().getColumn(4).setPreferredWidth(95);
        
        transactionsTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            
            public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
                
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                
                setIcon(null);
                
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
                } else if(isSelected) {
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                }
                return c;
            }
        });
        
        sorter = new TableRowSorter<DefaultTableModel>(transactionsTableModel);
        sorter.setSortable(0, false);
        sorter.setComparator(4, new Comparator<String>() {
            
            public int compare(String s1, String s2) {
                try {
                    double d1 = Double.parseDouble((s1.replaceAll("[\\$,\\s]", "")));
                    double d2 = Double.parseDouble((s2.replaceAll("[\\$,\\s]", "")));
                    
                    return Double.compare(d1, d2);
                } catch(NumberFormatException | NullPointerException e) {
                    return 0;
                }
            }
        });
        transactionsTable.setRowSorter(sorter);
        
        JScrollPane scroller = new JScrollPane(transactionsTable);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.getViewport().setBackground(getBackground());
        
        tableContainer.add(tableTitle, BorderLayout.NORTH);
        tableContainer.add(scroller, BorderLayout.CENTER);
        
        JPanel bottomSplitInsights = new JPanel();
        bottomSplitInsights.setLayout(new BoxLayout(bottomSplitInsights, BoxLayout.X_AXIS));
        bottomSplitInsights.setPreferredSize(new Dimension(490, 150));
        bottomSplitInsights.setMaximumSize(new Dimension(490, Integer.MAX_VALUE));
        bottomSplitInsights.setMinimumSize(new Dimension(490, 150));
        bottomSplitInsights.setOpaque(false);
        
        JPanel ratiosPanel = new JPanel();
        ratiosPanel.setLayout(new BoxLayout(ratiosPanel, BoxLayout.Y_AXIS));
        ratiosPanel.setPreferredSize(new Dimension(190, 150));
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
        ratiosPanel.add(Box.createVerticalGlue());
        
        JPanel chartWrapper = new JPanel();
        chartWrapper.setLayout(new BoxLayout(chartWrapper, BoxLayout.Y_AXIS));
        chartWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        chartWrapper.setOpaque(false);
        
        JLabel expensesByCategory = new JLabel("Expenses By Category", JLabel.LEFT);
        expensesByCategory.setFont(new Font("Sans Serif", Font.BOLD, 18));
        expensesByCategory.setForeground(new Color(30, 41, 59));
        expensesByCategory.setBackground(getBackground());
        expensesByCategory.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel chartAndLegend = new JPanel();
        chartAndLegend.setLayout(new BoxLayout(chartAndLegend, BoxLayout.X_AXIS));
        chartAndLegend.setOpaque(false);
        chartAndLegend.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        ExpensesPieChart pieChart = new ExpensesPieChart();
        pieChart.setPreferredSize(new Dimension(100, 100));
        pieChart.setAlignmentX(Component.LEFT_ALIGNMENT);
        pieChart.setAlignmentY(Component.CENTER_ALIGNMENT);
        
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.setOpaque(false);
        legendPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        for(int i = 0; i < expenseCategories.length; i++) {
            JPanel itemRow = new JPanel();
            itemRow.setLayout(new BoxLayout(itemRow, BoxLayout.X_AXIS));
            itemRow.setOpaque(false);
            itemRow.setAlignmentX(Component.LEFT_ALIGNMENT);
            itemRow.setAlignmentY(Component.CENTER_ALIGNMENT);
            
            Color squareColor = sliceColors[i];
            JPanel colorDot = new JPanel() {
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(squareColor);
                    g.fillRect((getWidth() - 8) / 2, (getHeight() - 8) / 2, 8, 8);
                }
            };
            colorDot.setPreferredSize(new Dimension(12, 14));
            colorDot.setMaximumSize(new Dimension(12, 14));
            colorDot.setOpaque(false);
            colorDot.setAlignmentY(Component.CENTER_ALIGNMENT);
            
            double percentage = 0;
            if(tl.getExpenses() != 0) {
                percentage = expenseAmounts[i] / tl.getExpenses() * 100;
            }
            
            JLabel nameLabel = new JLabel(String.format("%s (%.1f%%)",
                expenseCategories[i], percentage), JLabel.LEFT);
            nameLabel.setForeground(new Color(30, 41, 59));
            nameLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
            
            itemRow.add(colorDot);
            itemRow.add(Box.createHorizontalStrut(5));
            itemRow.add(nameLabel);
            
            legendPanel.add(itemRow);
        }
        
        chartAndLegend.add(pieChart);
        chartAndLegend.add(Box.createHorizontalStrut(10));
        chartAndLegend.add(legendPanel);
        
        chartWrapper.add(expensesByCategory);
        chartWrapper.add(Box.createVerticalStrut(10));
        chartWrapper.add(chartAndLegend);
        
        bottomSplitInsights.add(ratiosPanel);
        bottomSplitInsights.add(Box.createHorizontalStrut(10));
        bottomSplitInsights.add(chartWrapper);
        
        add(summaryRow);
        add(Box.createVerticalStrut(10));
        add(tableContainer);
        add(Box.createVerticalStrut(15));
        add(bottomSplitInsights);
        add(Box.createVerticalGlue());
        
        updateDisplays();
    }
    
    public DefaultTableModel getTransactionsTableModel() {
        return transactionsTableModel;
    }
    
    public JTable getTable() {
        return transactionsTable;
    }
    
    public void updateDisplays() {
        incomeTotal.setText("$" + df.format(tl.getIncome()));
        expenseTotal.setText("$" + df.format(tl.getExpenses()));
        transactionAmt.setText(tl.getTransactionCount() + "");
        
        double saveRate = 0;
        double expenseToIncomeRate = 0;
        double netMarginRate = 0;
        
        if(tl.getIncome() != 0) {
            saveRate = (tl.getIncome() - tl.getExpenses()) / tl.getIncome();
            expenseToIncomeRate = tl.getExpenses() / tl.getIncome();
            netMarginRate = tl.getRunningBalance() / tl.getIncome();
        }
        
        savingsRate.setText(String.format("Savings Rate: %.1f%%", saveRate * 100));
        expenseToIncome.setText(String.format("Expense to Income: %.1f%%",
            expenseToIncomeRate * 100));
        netMargin.setText(String.format("Net Margin: %.1f%%", netMarginRate * 100));
    }
    
    
    public void filterTable(String query) {
        if(query.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 2));
        }
    }
    
    class ExpensesPieChart extends JPanel {
        
        public ExpensesPieChart() {
            setBackground(DataPanel.this.getBackground());
            
            expenseAmounts = new double[expenseCategories.length];
            for(int i = 0; i < tl.getTransactionCount(); i++) {
                Transaction currTrans = tl.getTransaction(i);
                if(currTrans.isExpense()) {
                    
                    String category = currTrans.getCategory();
                    int index = Arrays.asList(expenseCategories).indexOf(category);
                    expenseAmounts[index] -= currTrans.getAmount();
                }
            }
        }
        
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D)(g.create());
            int padding = 15;
            
            double pieChartSize = Math.max(20, Math.min(getWidth(), getHeight())
                - padding);
            
            double x = (getWidth() - pieChartSize) / 2.0;
            double y = (getHeight() - pieChartSize) / 2.0;
            
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if(tl.getExpenses() == 0) {
                g2d.setColor(new Color(226, 232, 240));
                g2d.fill(new Ellipse2D.Double(x, y, pieChartSize, pieChartSize));
                g2d.dispose();
                return;
            }
            
            double startAngle = 0;
            for(int i = 0; i < expenseAmounts.length; i++) {
                double currAmt = expenseAmounts[i];
                
                double arcAngle = 360 * (currAmt / tl.getExpenses());
                
                g2d.setColor(sliceColors[i]);
                g2d.fill(new Arc2D.Double(x, y, pieChartSize, pieChartSize,
                    startAngle, arcAngle, Arc2D.PIE));
                
                g2d.setColor(getBackground());
                g2d.setStroke(new BasicStroke(1f));
                g2d.draw(new Arc2D.Double(x, y, pieChartSize, pieChartSize,
                    startAngle, arcAngle, Arc2D.PIE));
                
                startAngle += arcAngle;
            }
            
            double donutSize = pieChartSize * 0.35;
            double donutX = (getWidth() - donutSize) / 2.0;
            double donutY = (getHeight() - donutSize) / 2.0;
            
            g2d.setColor(getBackground());
            g2d.fill(new Ellipse2D.Double(donutX, donutY, donutSize, donutSize));
            
            g2d.dispose();
        }
    }
}
