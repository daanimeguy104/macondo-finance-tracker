import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Scanner;

public class Main {
    
    private TransactionList tl;
    
    public Main() {
        tl = new TransactionList();
        readJSON();
    }
    
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
    
    public void run() {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.add(new FinancePanel(tl));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new SaveOnExit());
    }
    
    public void readJSON() {
        Scanner reader = null;
        
        try {
            reader = new Scanner(new File("transactions.json"));
        } catch(FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No saved" +
                " transactions file found. Creating a new one.", "File not found",
                JOptionPane.PLAIN_MESSAGE);
            return;
        }
        
        String name = "";
        double amount = 0;
        String date = "";
        String category = "";
        
        while(reader.hasNext()) {
            String line = reader.nextLine().trim();
            
            if(line.indexOf("\"name\"") == 0) {
                name = extractStringVal(line);
            } else if(line.indexOf("\"amount\"") == 0) {
                amount = extractDoubleVal(line);
            } else if(line.indexOf("\"date\"") == 0) {
                date = extractStringVal(line);
            } else if(line.indexOf("\"category\"") == 0) {
                category = extractStringVal(line);
            }
            
            if(line.indexOf('}') != -1) {
                tl.addTransaction(name, amount, date, category);
            }
        }
    }
    
    public String extractStringVal(String line) {
        int colonIndex = line.indexOf(":");
        int quoteIndex = line.lastIndexOf("\"");
        
        return line.substring(colonIndex + 3, quoteIndex);
    }
    
    public double extractDoubleVal(String line) {
        int colonIndex = line.indexOf(":");
        int commaIndex = line.lastIndexOf(",");
        
        return Double.parseDouble(line.substring(colonIndex + 2, commaIndex));
    }
    
    class SaveOnExit extends WindowAdapter {
    
        public void windowClosing(WindowEvent evt) {
            PrintWriter out = null;
            try {
                out = new PrintWriter("transactions.json");
            } catch(IOException e) {
                System.err.println("transactions.json unable to be written to.");
                System.exit(10);
            }
            
            out.println("[");
            for(int i = 0; i < tl.getTransactionCount(); i++) {
                Transaction currTrans = tl.getTransaction(i);
                
                out.println("    {");
                out.println("        \"name\": \"" + currTrans.getName() + "\",");
                out.println("        \"amount\": " + currTrans.getAmount() + ",");
                out.println("        \"date\": \"" + currTrans.getDate() + "\",");
                out.println("        \"category\": \"" + currTrans.getCategory() + "\"");
                
                out.print("    }");
                if(i != tl.getTransactionCount() - 1) {
                    out.println(",");
                }
            }
            out.println("\n]");
            out.close();
            
            if(evt.getSource() instanceof JFrame) {
                JFrame frame = (JFrame)(evt.getSource());
                frame.dispose();
            }
            
            System.exit(0);
        }
    }
}
