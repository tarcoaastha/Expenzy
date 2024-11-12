package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

class Expense {
    private String description;
    private double amount;
    private String date;
    private String category;
    private String location;
    private boolean isRecurring;

    public Expense(String description, double amount, String date, String category, String location, boolean isRecurring) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.location = location;
        this.isRecurring = isRecurring;
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }
    public boolean isRecurring() { return isRecurring; }

    @Override
    public String toString() {
        return "Description: " + description + ", Amount: Rs. " + amount + ", Date: " + date +
                ", Category: " + category + ", Location: " + location +
                ", Recurring: " + (isRecurring ? "Yes" : "No");
    }
}

public class ExpenseTrackerGUI extends JFrame {
    private ArrayList<Expense> expenses;
    private JTextArea expenseDisplayArea;
    private JTextField descriptionField, amountField, locationField;
    private JComboBox<String> categoryComboBox;
    private JCheckBox recurringCheckBox;
    private static final String FILE_NAME = "expenses.txt";
    private static final double EXPENSE_LIMIT = 4000;  // Limit for warning
    private double savingsGoal = 0;

    public ExpenseTrackerGUI() {
        expenses = new ArrayList<>();
        loadExpenses();
        createUI();
    }

    private void createUI() {
        setTitle("Expense Tracker");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));

        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        inputPanel.add(new JLabel("Category:"));
        String[] categories = {"Food", "Travel", "Entertainment", "Health", "Others"};
        categoryComboBox = new JComboBox<>(categories);
        inputPanel.add(categoryComboBox);

        inputPanel.add(new JLabel("Location:"));
        locationField = new JTextField();
        inputPanel.add(locationField);

        recurringCheckBox = new JCheckBox("Recurring Expense");
        inputPanel.add(recurringCheckBox);

        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(new AddExpenseListener());
        inputPanel.add(addButton);

        JButton deleteButton = new JButton("Delete Last Expense");
        deleteButton.addActionListener(new DeleteExpenseListener());
        inputPanel.add(deleteButton);

        JButton viewButton = new JButton("View All Expenses");
        viewButton.addActionListener(new ViewExpensesListener());
        inputPanel.add(viewButton);

        JButton viewMonthlyButton = new JButton("View Monthly Expenses");
        viewMonthlyButton.addActionListener(new ViewMonthlyExpensesListener());
        inputPanel.add(viewMonthlyButton);

        JButton setGoalButton = new JButton("Set Savings Goal");
        setGoalButton.addActionListener(e -> {
            String goalInput = JOptionPane.showInputDialog("Enter your savings goal:");
            try {
                savingsGoal = Double.parseDouble(goalInput);
                JOptionPane.showMessageDialog(null, "Savings goal set successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid goal amount. Please enter a number.");
            }
        });
        inputPanel.add(setGoalButton);

        JButton dashboardButton = new JButton("Show Dashboard");
        dashboardButton.addActionListener(e -> showDashboard());
        inputPanel.add(dashboardButton);

        add(inputPanel, BorderLayout.NORTH);

        // Display Area
        expenseDisplayArea = new JTextArea();
        expenseDisplayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(expenseDisplayArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String description = parts[0];
                double amount = Double.parseDouble(parts[1]);
                String date = parts[2];
                String category = parts[3];
                String location = parts[4];
                boolean isRecurring = Boolean.parseBoolean(parts[5]);
                expenses.add(new Expense(description, amount, date, category, location, isRecurring));
            }
        } catch (IOException e) {
            System.out.println("No previous expenses found. Starting fresh.");
        }
    }

    private void saveExpenses() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Expense expense : expenses) {
                writer.write(expense.getDescription() + "," + expense.getAmount() + "," + expense.getDate() + ","
                        + expense.getCategory() + "," + expense.getLocation() + "," + expense.isRecurring());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses.");
        }
    }

    private void clearFields() {
        descriptionField.setText("");
        amountField.setText("");
        locationField.setText("");
        recurringCheckBox.setSelected(false);
    }

    private double calculateTotalExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    private class AddExpenseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String description = descriptionField.getText();
            double amount;
            try {
                amount = Double.parseDouble(amountField.getText());
                if (amount < 0) {
                    JOptionPane.showMessageDialog(null, "Amount cannot be negative.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a numeric value.");
                return;
            }

            String selectedCategory = categoryComboBox.getSelectedItem().toString();
            String location = locationField.getText();
            boolean isRecurring = recurringCheckBox.isSelected();

            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            expenses.add(new Expense(description, amount, currentDate, selectedCategory, location, isRecurring));
            saveExpenses();
            clearFields();
            JOptionPane.showMessageDialog(null, "Expense added successfully!");

            double totalExpenses = calculateTotalExpenses();
            if (totalExpenses > EXPENSE_LIMIT) {
                JOptionPane.showMessageDialog(null, "Warning: Your expenses have exceeded Rs. " + EXPENSE_LIMIT,
                        "Expense Limit Exceeded", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private class DeleteExpenseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!expenses.isEmpty()) {
                expenses.remove(expenses.size() - 1); // Remove the last expense
                saveExpenses();
                JOptionPane.showMessageDialog(null, "Last expense deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No expenses to delete.");
            }
        }
    }

    private class ViewExpensesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            expenseDisplayArea.setText(""); // Clear the display area
            if (expenses.isEmpty()) {
                expenseDisplayArea.setText("No expenses recorded.");
            } else {
                double total = 0;
                for (Expense expense : expenses) {
                    expenseDisplayArea.append(expense + "\n");
                    total += expense.getAmount();
                }
                expenseDisplayArea.append(String.format("\nTotal Expenses: Rs. %.2f", total));
            }
        }
    }

    private class ViewMonthlyExpensesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            expenseDisplayArea.setText(""); // Clear the display area
            double totalMonthlyExpense = 0;
            String currentMonth = new SimpleDateFormat("yyyy-MM").format(new Date());
            for (Expense expense : expenses) {
                if (expense.getDate().startsWith(currentMonth)) {
                    expenseDisplayArea.append(expense + "\n");
                    totalMonthlyExpense += expense.getAmount();
                }
            }

            if (totalMonthlyExpense == 0) {
                expenseDisplayArea.setText("No expenses recorded for this month.");
            } else {
                expenseDisplayArea.append(String.format("\nTotal Monthly Expenses: Rs. %.2f", totalMonthlyExpense));
            }
        }
    }

    private void showDashboard() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Expense expense : expenses) {
            categoryTotals.put(expense.getCategory(),
                    categoryTotals.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount());
        }

        for (String category : categoryTotals.keySet()) {
            dataset.setValue(category, categoryTotals.get(category));
        }

        JFreeChart chart = ChartFactory.createPieChart("Spending by Category", dataset, true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 400));
        JFrame dashboardFrame = new JFrame("Expense Dashboard");
        dashboardFrame.add(chartPanel);
        dashboardFrame.pack();
        dashboardFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExpenseTrackerGUI tracker = new ExpenseTrackerGUI();
            tracker.setVisible(true);
        });
    }
}