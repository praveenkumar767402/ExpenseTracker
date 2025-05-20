package alephys;
import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Transaction {
    String type; // INCOME or EXPENSE
    LocalDate date;
    String category;
    double amount;

    public Transaction(String type, LocalDate date, String category, double amount) {
        this.type = type;
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public Month getMonth() {
        return date.getMonth();
    }

    @Override
    public String toString() {
        return type + "," + date + "," + category + "," + amount;
    }
}

public class ExpenseTracker {
    private static final List<Transaction> transactions = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub while (true) {
        System.out.println("\n=== Expense Tracker ===");
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. View Monthly Summary");
        System.out.println("4. Load Data from File");
        System.out.println("5. Save Data to File");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");

        int option = Integer.parseInt(scanner.nextLine());

        switch (option) {
            case 1 -> addTransaction("INCOME");
            case 2 -> addTransaction("EXPENSE");
            case 3 -> viewMonthlySummary();
            case 4 -> loadFromFile();
            case 5 -> saveToFile();
            case 6 -> {
                System.out.println("Exiting... Goodbye!");
                return;
            }
            default -> System.out.println("Invalid option. Try again.");
        }
    }


private static void addTransaction(String type) {
    try {
        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        String category = "";
        if (type.equals("INCOME")) {
            System.out.print("Enter category (Salary/Business): ");
        } else {
            System.out.print("Enter category (Food/Rent/Travel): ");
        }
        category = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        transactions.add(new Transaction(type, date, category, amount));
        System.out.println(type + " added successfully.");
    } catch (Exception e) {
        System.out.println("Invalid input! Try again.");
    }
}

private static void viewMonthlySummary() {
    try {
        System.out.print("Enter month (1-12): ");
        int monthNumber = Integer.parseInt(scanner.nextLine());
        Month month = Month.of(monthNumber);

        double incomeTotal = 0, expenseTotal = 0;

        System.out.println("\n--- Summary for " + month + " ---");
        for (Transaction t : transactions) {
            if (t.getMonth() == month) {
                System.out.println(t);
                if (t.type.equals("INCOME")) {
                    incomeTotal += t.amount;
                } else {
                    expenseTotal += t.amount;
                }
            }
        }

        System.out.println("Total Income: ₹" + incomeTotal);
        System.out.println("Total Expenses: ₹" + expenseTotal);
        System.out.println("Net Savings: ₹" + (incomeTotal - expenseTotal));
    } catch (Exception e) {
        System.out.println("Invalid month input.");
    }
}

private static void loadFromFile() {
    System.out.print("Enter file name to load (with path): ");
    String filename = scanner.nextLine();

    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        int count = 0;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length != 4) continue;

            String type = parts[0].toUpperCase();
            LocalDate date = LocalDate.parse(parts[1], formatter);
            String category = parts[2];
            double amount = Double.parseDouble(parts[3]);

            transactions.add(new Transaction(type, date, category, amount));
            count++;
        }
        System.out.println("Loaded " + count + " records successfully.");
    } catch (IOException e) {
        System.out.println("Error reading file: " + e.getMessage());
    } catch (Exception ex) {
        System.out.println("Invalid file format.");
    }
}

private static void saveToFile() {
    System.out.print("Enter file name to save (with path): ");
    String filename = scanner.nextLine();

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        for (Transaction t : transactions) {
            writer.write(t.toString());
            writer.newLine();
        }
        System.out.println("Data saved to file successfully.");
    } catch (IOException e) {
        System.out.println("Error writing file: " + e.getMessage());
    }
}

}
