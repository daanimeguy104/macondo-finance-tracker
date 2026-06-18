Macondo Finance Tracker
Macondo Finance Tracker is a lightweight desktop personal finance dashboard built using Java Swing. It provides users with live financial tracking, automated budget metrics, dynamic category visualizations, and continuous data persistence using a localized JSON flat-file storage engine.

Project Architecture & Directory Layout
This project is configured as a standard IntelliJ IDEA module. All operational codebase elements reside inside the root source directory:

MacondoFinanceTracker/
│
├── .idea/                  # IntelliJ project configuration metadata
├── src/                    # Source folder containing Java classes
│   ├── DataPanel.java      # Table views, analytics calculations, and custom pie chart canvas
│   ├── FinancePanel.java   # Master layout wrapper organizing the global frame grids
│   ├── InputPanel.java     # Text consoles, filter hooks, and action listeners
│   ├── Main.java           # Main bootstrapper, storage scanner, and shutdown hooks
│   ├── RoundedPanel.java   # Core graphics helper overriding UI painting geometry
│   ├── TitlePanel.java     # Responsive header displaying the dynamic net balance banner
│   ├── Transaction.java    # Read-only model representing single ledger entries
│   └── TransactionList.java# Centralized storage array and calculation matrix engine
│
├── MacondoFinanceTracker.iml # IntelliJ module structure descriptor
└── transactions.json       # Generated local JSON data ledger database file

Core Technical Mechanics
1. The Automated Storage Hook
To guarantee data integrity without bottlenecking performance through continuous disk reads/writes during runtime, data serialization is isolated using a JVM standard shutdown hook. This ensures files are written perfectly back to the disk when the application window closes.

2. Multi-Tier Input Safety Checks
Every transaction undergoes input validation before being added to memory caches. This prevents exceptions from broken numeric text blocks or empty string fields.

3. Native Graphics Pie Chart Calculations
The category overview chart does not rely on heavy external graphics frameworks. It utilizes low-level Graphics2D draw loops mapped against the tracking model vectors to render smooth donut slices.

💾 Storage File Schema
Historical state records are saved to a plain text file named transactions.json located within the execution directory root. Expense entries are calculated dynamically using negative numbers:

[
  {
    "name": "Bi-Weekly Paycheck",
    "amount": 2500.0,
    "date": "Jun 12, 2026",
    "category": "Income"
    },
    {
    "name": "Electric Bill",
    "amount": -85.5,
    "date": "Jun 03, 2026",
    "category": "Housing & Bills"
  }
]

Getting Started

Prerequisites
Java Development Kit (JDK 8 or higher)

IntelliJ IDEA (Community or Ultimate Edition)

Importing into IntelliJ IDEA
Open IntelliJ IDEA.

Select File -> Open... from the top menu toolbar.

Browse to and select the root MacondoFinanceTracker project folder.

IntelliJ will automatically detect the .iml file and configure your workspace modules instantly.

Running the App from Command Line
To compile and run the application manually from your terminal, execute these commands from the root directory (the folder containing transactions.json):

javac src/*.java
java -cp src Main
