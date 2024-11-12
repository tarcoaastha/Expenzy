# Expenzy
Expense Tracker GUI
Overview
Expense Tracker is a simple yet powerful Java Swing-based GUI application that allows users to track their daily expenses. The app enables users to add, view, and manage their expenses by categorizing them, and it also provides features like recurring expense tracking and a visual dashboard to analyze spending. The app also gives users a warning if their total expenses exceed a predefined limit.

Features
Add Expenses: Track new expenses by entering description, amount, category, location, and whether the expense is recurring.
View All Expenses: Display all recorded expenses in a user-friendly interface.
Monthly Expense View: View the expenses for the current month, categorized by expense type.
Expense Dashboard: Visualize spending distribution across categories with a pie chart.
Expense Limit Warning: The app alerts users if their total expenses exceed a predefined limit.
Recurring Expense Tracker: Easily mark expenses as recurring.
Savings Goal: Set a savings goal and keep track of your progress.
Technologies Used
Java 8+: Programming language used to build the application.
Swing: GUI framework used for building the interface.
JFreeChart: Library used for generating the expense distribution pie chart.
File Handling: The app saves expenses to a text file (expenses.txt) to persist data between app sessions.
SimpleDateFormat: For handling and formatting dates.
Installation
Prerequisites
Java 8 or higher must be installed on your machine.
You can download Java from Oracle's official website or install it using a package manager.
IDE (optional):
You can use any Java IDE like IntelliJ IDEA, Eclipse, or even simple text editors like VS Code with Java extensions.
Steps to Install
Clone the Repository:

bash
Copy code
git clone https://github.com/your-username/expense-tracker.git
Navigate to the Project Folder:

bash
Copy code
cd expense-tracker
Open the Project:

Open the project in your preferred IDE (IntelliJ, Eclipse, etc.).
Run the Application:

Run the ExpenseTrackerGUI.java class to start the application.
Usage
Launching the Application
After running the application, a GUI window will appear where you can interact with the app.
Adding Expenses: Enter details like description, amount, category, location, and whether the expense is recurring.
Viewing Expenses: You can view the list of all recorded expenses, as well as expenses for the current month.
Dashboard: Click on "Show Dashboard" to view your spending distribution by category in a pie chart.
Saving Goal: Set a savings goal to track your financial progress.
Warning: If your total expenses exceed the limit, the app will alert you with a warning message.
Expense Data Storage
Expenses are stored in a local file called expenses.txt. Each expense is recorded with its description, amount, date, category, location, and recurring status.
You can modify or delete the expenses.txt file manually, but the app will read from and write to it automatically.
Screenshots
Add Expenses, View Expenses, and Set Savings Goal options.

Pie chart showing spending distribution across categories.

Future Improvements
Cloud Sync: Sync expenses across multiple devices or platforms using a cloud storage service.
Export Data: Add functionality to export expense data to CSV or PDF.
Expense Analytics: Provide more advanced analytics such as monthly averages or spending trends over time.
Mobile Version: Build a mobile app version for Android or iOS.
Contributing
Fork the repository.
Create a new branch for your feature (git checkout -b feature-name).
Commit your changes (git commit -am 'Add feature-name').
Push to the branch (git push origin feature-name).
Open a pull request with a detailed description of your changes.
License
This project is licensed under the MIT License - see the LICENSE file for details.

Acknowledgments
JFreeChart for providing the charting library.
Oracle for the Java platform.
StackOverflow and other community forums for troubleshooting and support.
Contact
Your Name: [Your GitHub Profile Link]
Email: [Your Email Address]
Important Notes
Make sure to handle errors properly while using the app (e.g., input validation, file reading/writing).
The app is currently stored on your local machine. For others to access it, you'll need to either distribute the JAR file or deploy it online (such as via cloud platforms).
