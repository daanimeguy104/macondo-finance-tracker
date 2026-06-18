import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Scanner;

/**
 * Entry point for the Macondo Finance Tracker application. Loads saved transactions
 * from disk, opens the main window, and saves transaction data back to a JSON file
 * when the program closes.
 *
 * @author      Vishruth (daanimeguy104)
 * @since       June 9th, 2026
 */
public class Main {
    
    /**
     * The list of transactions the user has logged.
     */
    private TransactionList tl;
    
    /**
     * Constructor initializes the transaction list and reads in any saved transactions
     * from a JSON file.
     */
    public Main() {
        tl = new TransactionList();
        readJSON();
    }
    
    /**
     * The main method creates a new instance of the Main class and runs the
     * application. It also adds a shutdown hook to save the transaction data to
     * a JSON file when the application is closed.
     *
     * @param args      Command line arguments (not used)
     */
    public static void main(String[] args) {
        Main main = new Main(); // run the application
        main.run();
        
        // create a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    main.writeJSON(); // try to save the current transaction data to a JSON file
                } catch(Exception e) {
                    // if an Exception is caught show an error message dialog
                    JOptionPane.showMessageDialog(null, "Error saving" +
                        "data during shutdown: " + e.getMessage(), "Error",
                        JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
    }
    
    /**
     * Creates the main JFrame of the application, adds a FinancePanel, and makes it
     * visible.
     */
    public void run() {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new FinancePanel(tl));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    /**
     * Reads in the saved transactions from a JSON file and adds them to the transaction
     * list. If the file is not found, a message dialog is shown to inform the
     * user the application will start fresh.
     */
    public void readJSON() {
        Scanner reader = null; // to read the JSON file
        
        // try to open the transactions.json file for reading
        try {
            reader = new Scanner(new File("transactions.json"));
        } catch(FileNotFoundException e) {
            // if the file is not found, show a message dialog and return
            JOptionPane.showMessageDialog(null, "No saved" +
                " transactions file found. Starting fresh.", "File Not Found",
                JOptionPane.PLAIN_MESSAGE);
            return;
        }
        
        // data for a single transaction
        String name = "";
        double amount = 0;
        String date = "";
        String category = "";
        
        // loop through every line of the JSON file and extract the transaction data
        while(reader.hasNext()) {
            String line = reader.nextLine().trim(); // get the current line without leading/trailing whitespace
            
            // else if blocks to extract the transaction data from the JSON file
            if(line.indexOf("\"name\"") == 0) {
                name = extractStringVal(line);
            } else if(line.indexOf("\"amount\"") == 0) {
                amount = extractDoubleVal(line);
            } else if(line.indexOf("\"date\"") == 0) {
                date = extractStringVal(line);
            } else if(line.indexOf("\"category\"") == 0) {
                category = extractStringVal(line);
            }
            
            // if the end of JSON object is reached, add the transaction to the transaction list
            if(line.indexOf('}') != -1) {
                tl.addTransaction(name, amount, date, category);
            }
        }
    }
    
    /**
     * Extracts the string value from a line of JSON data. The method assumes
     * that the line is in the format: "key": "value".
     *
     * @param line      The line of JSON data to extract the string value from
     * @return          The extracted string value
     */
    public String extractStringVal(String line) {
        // mark indices of interest
        int colonIndex = line.indexOf(":");
        int quoteIndex = line.lastIndexOf("\"");
        
        // cut the string to get the value between the indices of interest
        return line.substring(colonIndex + 3, quoteIndex);
    }
    
    /**
     * Extracts the double value from a line of JSON data. The method assumes
     * that the line is in the format: "key": value
     *
     * @param line      The line of JSON data to extract the double value from
     * @return          The extracted double value
     */
    public double extractDoubleVal(String line) {
        // mark indices of interest
        int colonIndex = line.indexOf(":");
        int commaIndex = line.lastIndexOf(",");
        
        // cut the string to get the value between the indices and parse it as a double
        return Double.parseDouble(line.substring(colonIndex + 2, commaIndex));
    }
    
    /**
     * Writes the current transaction data to a JSON file. The method creates a new
     * file called "transactions.json" and writes the transaction data in JSON format.
     * If the file cannot be created or written to, an error message is printed to the
     * console and the program exits with an error code.
     */
    public void writeJSON() {
        PrintWriter out = null; // to write to the output JSON file
        
        // try to create the PrintWriter for the output file, and handle any IOException that may occur
        try {
            out = new PrintWriter("transactions.json");
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "transactions.json" +
                " unable to be written to.", "File Unavailable", JOptionPane.PLAIN_MESSAGE);
            System.err.println("");
            System.exit(10);
        }
        
        out.println("["); // print JSON file starting mark
        
        // loop through each transaction in the transaction list and write its data to the JSON file
        for(int i = 0; i < tl.getTransactionCount(); i++) {
            Transaction currTrans = tl.getTransaction(i); // get current transaction
            
            // write its corresponding data to the file
            out.println("    {");
            out.println("        \"name\": \"" + currTrans.getName() + "\",");
            out.println("        \"amount\": " + currTrans.getAmount() + ",");
            out.println("        \"date\": \"" + currTrans.getDate() + "\",");
            out.println("        \"category\": \"" + currTrans.getCategory() + "\"");
            
            out.print("    }");
            
            // if current transaction isn't the last one, print a comma to separate it from the next transaction
            if(i != tl.getTransactionCount() - 1) {
                out.println(",");
            }
        }
        out.println("\n]"); // print JSON file ending mark and close PrintWriter
        out.close();
    }
}
