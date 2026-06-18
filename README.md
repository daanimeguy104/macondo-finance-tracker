# Macondo Finance Tracker

Macondo Finance Tracker is a lightweight desktop personal finance dashboard built using **Java Swing**. It provides users with live financial tracking, automated budget metrics, dynamic category visualizations, and continuous data persistence using a localized JSON flat-file storage engine.

---

## Project Architecture & Directory Layout

This project is configured as a standard IntelliJ IDEA module. All operational codebase elements reside inside the root source directory:

MacondoFinanceTracker/<br>
│<br>
├── .idea/                  # IntelliJ project configuration metadata<br>
├── src/                    # Source folder containing Java classes<br>
│&emsp;├── DataPanel.java      # Table views, analytics calculations, and custom pie chart canvas<br>
│&emsp;├── FinancePanel.java   # Master layout wrapper organizing the global frame grids<br>
│&emsp;├── InputPanel.java     # Text consoles, filter hooks, and action listeners<br>
│&emsp;├── Main.java           # Main bootstrapper, storage scanner, and shutdown hooks<br>
│&emsp;├── RoundedPanel.java   # Core graphics helper overriding UI painting geometry<br>
│&emsp;├── TitlePanel.java     # Responsive header displaying the dynamic net balance banner<br>
│&emsp;├── Transaction.java    # Read-only model representing single ledger entries<br>
│&emsp;└── TransactionList.java# Centralized storage array and calculation matrix engine<br>
│<br>
├── MacondoFinanceTracker.iml # IntelliJ module structure descriptor<br>
└── transactions.json       # Generated local JSON data ledger database file<br>

---

## Core Technical Mechanics

### 1. The Automated Storage Hook
To guarantee data integrity without bottlenecking performance through continuous disk reads/writes during runtime, data serialization is isolated using a JVM standard shutdown hook. This ensures files are written perfectly back to the disk when the application window closes.

### 2. Multi-Tier Input Safety Checks
Every transaction undergoes input validation before being added to memory caches. This prevents exceptions from broken numeric text blocks or empty string fields.

### 3. Native Graphics Pie Chart Calculations
The category overview chart does not rely on heavy external graphics frameworks. It utilizes low-level Graphics2D draw loops mapped against the tracking model vectors to render smooth donut slices.

---

## File Storage Scheme

Historical state records are saved to a a JSON file name transactions.json located within the execution directory root. Expense entries are calculated dynamically using negative numbers:

[<br>
&emsp;{<br>
&emsp;&emsp;"name": "Bi-Weekly Paycheck",<br>
&emsp;&emsp;"amount": 2500.0,<br>
&emsp;&emsp;"date": "Jun 12, 2026",<br>
&emsp;&emsp;"category": "Income"<br>
&emsp;},<br>
&emsp;{<br>
&emsp;&emsp;"name": "Electric Bill",<br>
&emsp;&emsp;"amount": -85.5,<br>
&emsp;&emsp;"date": "Jun 03, 2026",<br>
&emsp;&emsp;"category": "Housing & Bills"<br>
&emsp;}<br>
]<br>

---

## Getting Started

### Prerequisites
* Java Development Kit (JDK 8 or higher)
* IntelliJ IDEA (Community or Ultimate Edition)

### Importing into IntelliJ IDEA
1. Open IntelliJ IDEA.
2. Select File -> Open... from the top menu toolbar.
3. Browse to and select the root MacondoFinanceTracker project folder.
4. IntelliJ will automatically detect the .iml file and configure your workspace modules instantly.

### Running the App from Command Line

To compile and run the application manually from your terminal, execute these commands from the **root directory** (the folder containing transactions.json):

javac src/*.java
java -cp src Main

### How the Command Line Paths Work:
* javac src/*.java compiles all of the Java source code files tucked inside the src folder and outputs their respective executable .class files right alongside them.
* java -cp src Main launches the program. The -cp src flag tells the Java Virtual Machine to look inside the src directory to find your compiled code classes. 
* Because you run this command directly from the root folder, Java treats the root folder as the active "Working Directory". This allows Main.java to cleanly discover and update your transactions.json file exactly where it sits in the project root, keeping your data completely separate from your source code.

*Note: On boot, the program will look for transactions.json. If it does not exist, an option prompt will alert you, and a fresh file will automatically initialize on the first safe exit sequence.*
