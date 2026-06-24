# Macondo Finance Tracker

This application is local personal finance tool that was built using Java. It helps users track their financial status by breaking down logged expenses and income. For long time storage, the program saves to a custom JSON file.

---

## Directory Layout and Project Breakdown

This project was created using IntelliJ Ultimate, and as such follows its file format:
- the .idea folder contains IntelliJ config data (can be ignored if not using IntelliJ)
- transaction.json is the custom JSON file the program reads from and writes to pull user data. 
- src is the main folder where the source code is found
  - Main.java is the entry point for the program. Controls writing to and reading from the custom JSON file.
  - FinancePanel.java is the wrapper panel for all the GUI. It creates TitlePanel, InputPanel, and DataPanel, and initializes two shared objects between its components: a       TransactionList and DecimalFormatter
  - TitlePanel.java contains the title of the project and JLabel with current user balance. Updated when a transaction is added or removed.
  - InputPanel.java contains text fields, buttons, menus, and a checkbox so the user can log a transaction. There is also a button to remove a transaction. Both buttons         update data in DataPanel accordingly. Furthermore, there is a search bar so the user can filter through table elements.
  - DataPanel.java contains the data of all the users transactions. At the top, there are rounded panels that display total income, expenses, and transaction amount. Below      it is a table containing all the transactions the user has made with the info on each. Clicking on the table header sections will sort the table by that category,           ascending and descendingly. Below that to the left, there are some financial ratios regarding the transactions. To the left of that, there is pie chart breaking down        the user's expenses by category, with a legend to the right of the pie chart.
  - RoundedPanel.java is a custom JPanel with rounded edges. It is the parent of TitlePanel, InputPanel, and DataPanel, and is used inside DataPanel.java as well.
  - Transaction.java is an object representing a single transaction. It's properties include it's description, amount, category, and date it was made. Has accessors for all     those fields.
  - TransactionList.java is an object representing a list of transactions. It has methods that calculate and return information regarding the list (ex. the current balance)

---

## Core Technical Mechanics

### 1. Shutdown Hook
As soon as the program has been exited for whatever reason, a shutdown hook is run that forces the program to the save the contents of the TransactionList object to a JSON file named "transactions.json"

### 2. Input Safety Checks
When the user adds a transactions, the input fields for the transaction are double checked to make sure no invalid inputs are present.

### 3. Pie Chart Calculations
The pie chart that breaks down user expenses is not created using external libraries, but with Java's Graphics2D to keep the program efficient and lightweight.

---

## File Storage

On shutdown, the application will save the contents of the TransactionList object in use to the custom JSON file like so (expenses are denoted as negative numbers):

[<br>
&emsp;{<br>
&emsp;&emsp;"name": "transaction name",<br>
&emsp;&emsp;"amount": transaction amount (negative if expense and vice versa),<br>
&emsp;&emsp;"date": "transaction date (will take the date when transaction is made in real time)",<br>
&emsp;&emsp;"category": "transaction category"<br>
&emsp;},<br>
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
